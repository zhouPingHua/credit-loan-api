package com.example.demo.http;

import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.*;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zph  Date：2017/11/8.
 */
public abstract class AbstractHttpService {

    public Logger logger = LoggerFactory.getLogger(getClass());
    private static PoolingHttpClientConnectionManager connManager = null;
    private static HttpRequestRetryHandler retryHandler = null;

    public AbstractHttpService() {
        synchronized (AbstractHttpService.class) {
            if (connManager == null) {
                connManager = initConnManager();
            }
            if(retryHandler == null){
                retryHandler = initHttpRequestRetryHandler();
            }
        }
    }


    /**
     * 讲httpResponse转成String,可能是json,xml, html
     *
     * @param response
     * @param encode
     * @return
     * @throws Exception
     */
    protected String transformToString(CloseableHttpResponse response, String encode) throws Exception {
        try {
            return EntityUtils.toString(response.getEntity(), encode);
        } finally {
            closeAndReturnHttpConnection(response);
        }
    }

    protected String transformToString(CloseableHttpResponse response) throws Exception {
        try {
            return EntityUtils.toString(response.getEntity());
        } finally {
            closeAndReturnHttpConnection(response);
        }
    }

    private void closeAndReturnHttpConnection(CloseableHttpResponse response) {
        if (response != null) {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * httpClient get
     *
     * @param context 请求上下文
     * @return 请求结果：
     * 没有请求地址uri，返回 null
     * 状态码为 200， 返回请求结果
     * 返回结果不为200， 返回状态码
     */
    public String doGet(LoginContext context) {
        context.clear();
        String uri = context.getUri();
//        if (StringUtils.isBlank(uri)) return null;
        CloseableHttpClient httpClient = context.getHttpClient();
        CloseableHttpResponse response = null;
        String result = "";
        try {
            String encode = context.getEncoding();
            Map<String, String> params = context.getParams();
            if (params != null && !params.isEmpty()) {
                // 设置请求参数
                List<NameValuePair> getParams = new ArrayList<>();
                params.forEach((k, v) -> getParams.add(new BasicNameValuePair(k, v)));
                String paramsStr = EntityUtils.toString(new UrlEncodedFormEntity(getParams, encode));
                uri += "?" + paramsStr;
            }
            HttpGet httpGet = new HttpGet(uri);

            Map<String, String> headers = context.getRequestHeaders();
            // 设置请求头
            if (headers != null) {
                setCustomHeader(httpGet, headers);
            }

            RequestConfig requestConfig = context.getRequestConfig();
            // 其他配置
            if (requestConfig != null) {
                httpGet.setConfig(requestConfig);
            }

            response = httpClient.execute(httpGet, context.getHttpContext());

            context.setResponseInfo(response);
            // 获取信息
            HttpEntity entity = response.getEntity();
            String statusCode = String.valueOf(response.getStatusLine().getStatusCode());
            if ("200".equals(statusCode)) {
                result = readHttpContent(entity, encode);
            } else {
                result = statusCode;
            }
        } catch (Exception e) {
            logger.error("httpClient get " + uri + " 异常：", e);
        } finally {
            closeAndReturnHttpConnection(response);
        }
        return result;
    }

    private String readHttpContent(HttpEntity entity, String encode) throws Exception {
        StringBuilder buffer = new StringBuilder();
        InputStream in = null;
        try {
            in = entity.getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(in, encode));
            String temp;
            while ((temp = br.readLine()) != null) {
                buffer.append(temp);
                buffer.append("\n");
            }
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return buffer.toString();
    }

    public String getCookieParam(String cookies, String param) {
        int begin = cookies.lastIndexOf(param);
        String beginStr = cookies.substring(begin);
        int end = beginStr.indexOf(";");
        return beginStr.substring(0, end);
    }

    /**
     * httpClient post
     *
     * @param context 请求上下文
     * @return 请求结果：
     * 没有请求地址uri，返回 null
     * 状态码为 200， 返回请求结果
     * 状态码为 302， 返回跳转地址
     * 其他情况， 返回状态码
     */
    public String doPost(LoginContext context) {
        context.clear();
        String uri = context.getUri();
//        if (StringUtils.isBlank(uri)) return null;
        CloseableHttpClient httpClient = context.getHttpClient();
        HttpPost httpPost = new HttpPost(uri);
        CloseableHttpResponse response = null;
        String result = "";
        try {
            Map<String, String> headers = context.getRequestHeaders();
            // 设置请求头
            if (headers != null) {
                setCustomHeader(httpPost, headers);
            }

            String encode = context.getEncoding();
            Map<String, String> params = context.getParams();
            if (params != null && !params.isEmpty()) {
                List<NameValuePair> postParam = new ArrayList<>();
                params.forEach((k, v) -> postParam.add(new BasicNameValuePair(k, v)));
                UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(postParam, encode);
                httpPost.setEntity(postEntity);
            }

            RequestConfig requestConfig = context.getRequestConfig();
            // 其他配置
            if (requestConfig != null) {
                httpPost.setConfig(requestConfig);
            }

            response = httpClient.execute(httpPost, context.getHttpContext());
            context.setResponseInfo(response);


            // 获取信息
            HttpEntity entity = response.getEntity();

            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == 200) {
                result = readHttpContent(entity, encode);
            } else {
                result = String.valueOf(response.getStatusLine().getStatusCode());
                if ("302".equals(result) || "301".equals(result)) {
                    result += response.getFirstHeader("Location").getValue();
                }
            }
        } catch (Exception e) {
            logger.error("post " + uri + "异常", e);
        } finally {
            closeAndReturnHttpConnection(response);
        }
        return result;
    }


    /**
     * post请求不是form表单类型 @StringEntity
     *
     * @param loginContext
     * @param payload
     * @return
     */
    protected String httpPostPayload(LoginContext loginContext, String payload) {
        loginContext.clear();
        String url = loginContext.getUri();

        String encode = loginContext.getEncoding();
        CloseableHttpClient httpClient = loginContext.getHttpClient();
        BasicCookieStore cookieStore = loginContext.getCookieStore();
        String context = "";
        HttpPost post = new HttpPost(url);
        CloseableHttpResponse response = null;
        try {
            //提交的json数据
            StringEntity entity = new StringEntity(payload);
            entity.setContentEncoding(encode);
            entity.setContentType("application/json");
            post.setEntity(entity);

            Map<String, String> map = getCustomHeader();
            setCustomHeader(post, map);

            response = httpClient.execute(post, loginContext.getHttpContext());
            loginContext.setResponseInfo(response);
            String statusCode = response.getStatusLine().toString();

            if ("HTTP/1.1 200 OK".equals(statusCode)) {
                context = readHttpContent(response.getEntity(), encode);
            } else {
                context = String.valueOf(response.getStatusLine().getStatusCode());
                if ("302".equals(context) || "301".equals(context)) {
                    context += response.getFirstHeader("Location").getValue();
                }
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        } finally {
            closeAndReturnHttpConnection(response);
        }
        return context;
    }


    public CloseableHttpClient getHttpClient(BasicCookieStore cookieStore) {
        return getHttpClientFromConnManager(cookieStore, null);
    }

    /**
     * @param cookieStore
     * @return
     */
    public CloseableHttpClient getHttpClientFromConnManager(BasicCookieStore cookieStore, HttpHost proxy) {
        return HttpClientBuilder
                .create()
                .setProxy(proxy)
                .setDefaultCookieStore(cookieStore)
                .setConnectionManager(connManager)
                .setRetryHandler(retryHandler).build();
    }


    protected Map<String, String> getCustomHeader() {
        return new HashMap<>();
    }

    protected void setCustomHeader(HttpRequestBase httpRequest, Map<String, String> headers) {
        headers.forEach((k, v) -> httpRequest.setHeader(k, v));
    }


    private PoolingHttpClientConnectionManager initConnManager() {
        RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.create();
        ConnectionSocketFactory plainSF = new PlainConnectionSocketFactory();
        registryBuilder.register("http", plainSF);
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            //信任任何链接
            TrustStrategy anyTrustStrategy = (x509Certificates, s) -> true;
            SSLContext sslContext = SSLContexts
                    .custom()
//                    .useTLS()
                    .useProtocol("TLS")
                    .loadTrustMaterial(trustStore, anyTrustStrategy)
                    .build();
            NoopHostnameVerifier noopHostnameVerifier = NoopHostnameVerifier.INSTANCE;
            LayeredConnectionSocketFactory sslSF = new SSLConnectionSocketFactory(sslContext,
//                    SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER
                    noopHostnameVerifier
            );
            registryBuilder.register("https", sslSF);
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        } catch (KeyManagementException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        Registry<ConnectionSocketFactory> registry = registryBuilder.build();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        connectionManager.setMaxTotal(2000);
        connectionManager.setDefaultMaxPerRoute(50);
        return connectionManager;
    }

    //initHttpRequestRetryHandler
    private HttpRequestRetryHandler initHttpRequestRetryHandler(){
        return new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(IOException exception,
                                        int executionCount, HttpContext context) {
                if (executionCount >= 5) {// 如果已经重试了5次，就放弃
                    return false;
                }
                if(exception instanceof HttpHostConnectException){ //连接服务器拒接 Connection refused
                    return false;
                }
                if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
                    return true;
                }
                if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
                    return false;
                }
                if (exception instanceof InterruptedIOException) {// 超时
                    return false;
                }
                if (exception instanceof UnknownHostException) {// 目标服务器不可达
                    return false;
                }
                if (exception instanceof ConnectTimeoutException) {// 连接被拒绝
                    return false;
                }
                if (exception instanceof SSLException) {// SSL握手异常
                    return false;
                }

                HttpClientContext clientContext = HttpClientContext
                        .adapt(context);
                HttpRequest request = clientContext.getRequest();
                // 如果请求是幂等的，就再次尝试
                if (!(request instanceof HttpEntityEnclosingRequest)) {
                    return true;
                }
                return false;
            }
        };
    }


    protected LoginContext createLoginContext(BasicCookieStore cookieStore) {
        return createLoginContext(cookieStore, null);
    }

    /**
     * 创建loginContext
     *
     * @param cookieStore 缓存对象
     * @param proxy       代理
     * @return loginContext 上下文对象
     */
    protected LoginContext createLoginContext(BasicCookieStore cookieStore, HttpHost proxy) {
        HttpContext localContext = new BasicHttpContext();
        localContext.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(30000)
                .setSocketTimeout(30000)
                .setConnectionRequestTimeout(30000)
                .setProxy(proxy)
                .build();
        CloseableHttpClient httpClient = getHttpClient(cookieStore);
        return new LoginContext(cookieStore, httpClient, getBasicHeader(), localContext, requestConfig);
    }

    protected Map<String, String> getBasicHeader() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        headers.put("Accept-Encoding", "gzip, deflate, sdch");
        headers.put("Accept-Language", "zh-CN,zh;q=0.8");
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("Connection", "keep-alive");
        headers.put("Upgrade-Insecure-Requests", "1");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) " +
                "Chrome/47.0.2526.106 Safari/537.36");
        return headers;
    }

}
