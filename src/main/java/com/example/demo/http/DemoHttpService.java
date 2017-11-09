package com.example.demo.http;

import org.apache.http.impl.client.BasicCookieStore;

import java.util.Map;

/**
 * Created by zph  Date：2017/11/8.
 */
public class DemoHttpService extends AbstractHttpService {

    public String getImportNew(){
        LoginContext loginContext = createLoginContext(new BasicCookieStore(), null);
        Map<String,String> headers = loginContext.getRequestHeaders();
        headers.put("Host","www.importnew.com");
        String url = "http://www.importnew.com/22060.html";
        loginContext.setEncoding("utf-8");
        loginContext.setUri(url);
        loginContext.setParams(null);
        String result = doPost(loginContext);
        return result;
    }







    public static void main(String[] args) {
        DemoHttpService demoHttpService = new DemoHttpService();
        String result = demoHttpService.getImportNew();
        System.out.println(result);
    }
}
