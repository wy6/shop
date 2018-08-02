package com.zcib.util;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

/**
 * Created by WY on 2018/1/9.
 */

public class EmailUtils {

    //发送激活邮件
    public boolean sendActivateMail(String to, String code) {


        InputStream in = EmailUtils.class.getClassLoader().getResourceAsStream("activateEmail.properties");
        Properties prop = new Properties();
        try {
            prop.load(in);
            String name = prop.getProperty("name");
            String password = prop.getProperty("password");
            String host = prop.getProperty("host");
            //String to = "827895233@qq.com";
            String from = prop.getProperty("from");
            String subject = prop.getProperty("subject");
            String encode = prop.getProperty("encode");
            String content = prop.getProperty("content");

            content = MessageFormat.format(content,code);
            System.out.println("11111111"+content);
            System.out.println("11111111"+to);
            System.out.println("11111111"+code);

            EmailUtils emailUtils = new EmailUtils();
            emailUtils.sendMail(name, password, host, to, from, subject, encode, content);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
           // throw new RuntimeException(e);
        }
        return false;
    }

    //发送邮件方法
    public boolean sendMail(String name, String password, String host, String to, String From, String subject, String encode, String content) {

        try {
            HtmlEmail email = new HtmlEmail();
            //设置发邮件的用户名和密码（授权密码）
            email.setAuthentication(name, password);
            //设置发送邮件服务器（SMTP服务器）域名
            email.setHostName(host);
            //收件人的邮箱
            email.addTo(to);
            //设置发件人的邮箱
            email.setFrom(From);
            //设置邮件的主题
            email.setSubject(subject);
            //设置编码方式为了防止中文乱码
            email.setCharset(encode);
            //邮件的内容
            email.setHtmlMsg(content);
            //发送邮件
            email.send();
            return true;

        } catch (EmailException e) {
            e.printStackTrace();
            return false;
        }


    }
}
