package com.example.demo.http;

import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.message.BasicNameValuePair;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zph  Date：2017/11/8.
 */
public class DemoHttpService extends AbstractHttpService {

    public String getImportNew() {

        for(int i =0;i<20000;i++){
            LoginContext loginContext = createLoginContext(new BasicCookieStore(), null);
            Map<String, String> headers = loginContext.getRequestHeaders();
            headers.put("Host", "ebanks.bankofshanghai.com");
            String url = "https://ebanks.bankofshanghai.com/pweb/GenTokenImg.do?random=0.6111087410526812";
            loginContext.setEncoding("utf-8");
            loginContext.setUri(url);
            loginContext.setParams(null);
            BasicCookieStore cookieStore = loginContext.getCookieStore();
            String result = doGet(loginContext);
        }
        LoginContext loginContext = createLoginContext(new BasicCookieStore(), null);
        Map<String, String> headers = loginContext.getRequestHeaders();
        headers.put("Host", "ah.ac.10086.cn");
        String url = "https://ah.ac.10086.cn/SSOArtifact?artifact=-1&backUrl=https%3A%2F%2Fah.ac.10086.cn%2FSSOArtifact%3Fspid%3DF62DDE4206F3A39EFDDA75F8F4443409991A43143E01117245219D275996F432DD901D9B7459D8E2%26RelayState%3Dtype%3DB%3Bbackurl%3Dhttp%253A%252F%252Fservice.ah.10086.cn%252FLoginSso%3Bnl%3D3%3BloginFrom%3Dhttp%3A%2F%2Fservice.ah.10086.cn%2FLoginSso";
        loginContext.setEncoding("utf-8");
        loginContext.setUri(url);
        loginContext.setParams(null);
        BasicCookieStore cookieStore = loginContext.getCookieStore();
        String result = doGet(loginContext);

        cookieStore = loginContext.getCookieStore();
        List<Cookie> list = cookieStore.getCookies();
        for(int i =0; i<list.size();i++){
            Cookie cookie = list.get(i);
            System.out.println("getName "+cookie.getName());
            System.out.println("getComment "+cookie.getComment());
            System.out.println("getCommentURL "+cookie.getCommentURL());
            System.out.println("getDomain "+cookie.getDomain());
            System.out.println("getPath "+cookie.getPath());
            System.out.println("getValue "+cookie.getValue());
            System.out.println("getExpiryDate "+cookie.getExpiryDate());
            System.out.println("getPorts "+cookie.getPorts());
            System.out.println("getVersion "+cookie.getVersion());
            System.out.println("isExpired "+cookie.isExpired(new Date()));
            System.out.println("isPersistent "+cookie.isPersistent());
            System.out.println("isSecure "+cookie.isSecure());


        }
        System.out.println(loginContext.getCookieStr());

        return result;
    }

    public static void main(String[] args) {
        DemoHttpService demoHttpService = new DemoHttpService();
        String result = demoHttpService.getImportNew();
        System.out.println(result);
    }
}
