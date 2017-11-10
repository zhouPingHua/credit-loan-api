package com.example.demo.http;

import org.apache.http.impl.client.BasicCookieStore;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zph  Date：2017/11/8.
 */
public class DemoHttpService extends AbstractHttpService {

    public String getImportNew(){
        LoginContext loginContext = createLoginContext(new BasicCookieStore(), null);
        Map<String,String> headers = loginContext.getRequestHeaders();
        headers.put("Host","192.168.1.57");
        String url = "http://192.168.1.57:8011/import";
        loginContext.setEncoding("utf-8");
        loginContext.setUri(url);
        Map<String,String> datas = new HashMap<>();
        datas.put("phoneNo","15079019862");
        datas.put("servicePwd","zphqq123456");
        datas.put("opCode","ALIPAY");
        datas.put("userId","zph");
        datas.put("method","login");
        loginContext.setParams(datas);
        String result = doPost(loginContext);
        return result;
    }







    public static void main(String[] args) {
        DemoHttpService demoHttpService = new DemoHttpService();
        String result = demoHttpService.getImportNew();
        System.out.println(result);
    }
}
