package com.example.demo.http;

import org.apache.http.impl.client.BasicCookieStore;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zph  Date：2017/11/8.
 */
public class DemoHttpService extends AbstractHttpService {

    private String cookie = "cookie2=128323ae28ee435994659d2d7ed59b97; v=0; _tb_token_=ed7e3e1b8ee6e; cna=s8qaEokL2BkCAXTnN6t2zTIy; isg=Ap-foudCOUUPjz1Z_apST_M_JfEjBOJTYpv3mzHsOc6VwL1CNtWn93yS8mpB; ockeqeudmj=l9m5L%2BM%3D; munb=542322863; WAPFDFDTGFG=%2B4cMKKP%2B8PI%2BtTbL9prafy%2By7g%3D%3D; _w_app_lg=19; unb=542322863; sg=138; t=e95c69deea2b48ac5ca71795d19f11c1; _l_g_=Ug%3D%3D; skt=92918025bdfccaa2; uc1=cookie21=UIHiLt3xTIkz&cookie15=URm48syIIVrSKA%3D%3D&cookie14=UoTdev4o4DKwhA%3D%3D; cookie1=AC1w54fLBoXi1gz7KEWpSfEjboiOfa9bzX81l2zI4%2Fw%3D; uc3=vt3=F8dBzLOZjlr%2FG05q1qI%3D&id2=VvqkQTPtKJel&nk2=oggwC2P1&lg2=W5iHLLyFOGW7aA%3D%3D&sg2=VAXZ2zH%2FUhOBF38mpuKcmYXed1fL3tcMdoPkg7Ql4ls%3D; tracknick=%5Cu9F99%5Cu9CDE01; uss=AHt4fLb708IoHMcNuG5K499fuuZVWbyzJ0UkhQAtz2xpyOJa0m%2FatvM6Nw%3D%3D; lgc=%5Cu9F99%5Cu9CDE01; _cc_=URm48syIZQ%3D%3D; _nk_=%5Cu9F99%5Cu9CDE01; cookie17=VvqkQTPtKJel; ntm=0";

    public String getImportNew(){
        LoginContext loginContext = createLoginContext(new BasicCookieStore(), null);
        Map<String,String> headers = loginContext.getRequestHeaders();
        headers.put("Host","h5.m.taobao.com");
        headers.put("Cookie",cookie);
        String url = "http://h5.m.taobao.com/mlapp/olist.html?spm=a2141.7756461.2.6";
        loginContext.setEncoding("utf-8");
        loginContext.setUri(url);
        loginContext.setParams(null);
        String result = doGet(loginContext);

        return result;
    }

    public static void main(String[] args) {
        DemoHttpService demoHttpService = new DemoHttpService();
        String result = demoHttpService.getImportNew();
        System.out.println(result);
    }
}
