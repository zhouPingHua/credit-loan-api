package com.example.demo.credit;

import com.example.demo.http.AbstractHttpService;
import com.example.demo.http.LoginContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.client.BasicCookieStore;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zph  on 2017/12/8
 */
public class VoteCredit extends AbstractHttpService {


    public void run() throws InterruptedException {


        BasicCookieStore cookieStore = new BasicCookieStore();
        LoginContext loginContext = createLoginContext(cookieStore);
        loginContext.setEncoding("utf-8");
        Map<String, String> headers = loginContext.getRequestHeaders();
        headers.put("Host", "p4e6gu.v.vote8.cn");

        //get first page
        String url = "http://p4e6gu.v.vote8.cn/?Topic_2832884_Page=4";
//        loginContext.setUri("UTF-8");
        loginContext.setUri(url);
        String resHtml = doGet(loginContext);

        Map<String,String> datas = new HashMap<>();
        Document document = Jsoup.parse(resHtml);
        Elements elements = document.getElementsByClass("aspNetHidden");
        if(elements != null && elements.size()>0){
            for(Element element:elements){
                setFormParams(element,datas);
            }
        }

        elements = document.getElementsByClass("input-group");
        setFormParams(elements.first(),datas);
        Element element = document.getElementById("cphMainContent_rptTopicList_rptOptions_0_labelOptionTitle_0");
        setFormParams(element,datas);

        datas.put("ctl00$cphMainContent$rptTopicList$ctl00$hiddenTopicID","2832884");

        element = document.getElementById("cphMainContent_pnlVerifyCode");
        setFormParams(element,datas);

        element = document.getElementById("hiddenRefererUrl");
        setFormParams(element,datas);
        element = document.getElementById("hiddenTimeStampEncodeString");
        setFormParams(element,datas);
        element = document.getElementById("hiddenLatitude");
        setFormParams(element,datas);
        element = document.getElementById("hiddenLongitude");
        setFormParams(element,datas);
        element = document.getElementById("hiddenGeoLocationEncode");
        setFormParams(element,datas);

        url = "http://p4e6gu.v.vote8.cn/Front/VerifyCodeImage/Vote8Click.ashx";
        loginContext.setUri(url);
        loginContext.setParams(null);
        resHtml = doGet(loginContext);
        datas.put("hiddenVote8ClickValidateCode",resHtml.substring(0,resHtml.length()-1));
        datas.put("__EVENTTARGET","ctl00$cphMainContent$lbtnVote");
        Thread.sleep(10000l);

        url = "http://p4e6gu.v.vote8.cn/?Topic_2832884_Page=4";
        loginContext.setParams(datas);
        loginContext.setUri(url);
        resHtml = doPost(loginContext);

        System.out.println(resHtml);


    }


    public static void main(String[] args) throws InterruptedException {
        VoteCredit voteCredit = new VoteCredit();
        voteCredit.run();

        System.out.println();

    }

    private static void setFormParams(Element form, Map<String, String> data) {
        Elements inputs = form.getElementsByTag("input");
        for (Element input : inputs) {
            String name = input.attr("name");
            if (!StringUtils.isEmpty(name)){
                String value = input.attr("value");
                data.put(name, value);
            }
        }
    }
}
