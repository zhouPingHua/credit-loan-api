package com.example.demo.mail;



/**
 * Created by terry on 2016/11/21.
 */
public class EmailUtil {
    private static String smtpUid;
    private static String smtpPwd;
    private static String smtpServer;
    private static String senderMail;
    private static String senderNick;
    private static String inceptAddress="540742575@qq.com";

    static {
        smtpUid = "service@m3.9188.com";
        smtpPwd = "service9188cp123";
        smtpServer = "m3.9188.com";
        senderMail = "service@m3.9188.com";
        senderNick = "测试email";

        System.out.println("---------------------------");
        System.out.println(smtpUid);
        System.out.println(smtpPwd);
        System.out.println(smtpServer);
        System.out.println(senderMail);
        System.out.println(senderNick);
        System.out.println("---------------------------");


    }

    public static boolean sendEmail(String title,String content){
        boolean issuccess=false;
        for(int i = 0;i<3;i++){
            Mail mail = new Mail();
            mail.setSmtpUid(smtpUid);
            mail.setSmtpPwd(smtpPwd);
            mail.setSmtpServer(smtpServer);
            mail.setSenderMail(senderMail);
            mail.setSenderNick(senderNick);
            //发送邮件
            System.out.println("-----发送邮件开始------");
            boolean flag = mail.sendMsg(inceptAddress,title,content);
            System.out.println(inceptAddress);
            System.out.println(title);
            System.out.println(content);
            System.out.println("-----发送邮件结束------");
            if(!flag){
                System.out.println("发送邮件失败");
            }else{
                issuccess=true;
                break;
            }
            try {
                Thread.sleep(1000*i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return issuccess;
    }

    public static void main(String[] args) throws Exception {
        boolean result= EmailUtil.sendEmail("ttt","ttttt1");
        System.out.println(result);
    }
}