package com.zcib.test;

import com.zcib.util.EmailUtils;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

/**
 * Created by WY on 2018/1/9.
 */

public class TestEmail {
    public static void main(String[] args){
//        EmailUtils emailUtils = new EmailUtils();
//        emailUtils.sendActivateMail("827895233@qq.com","123");

        try {
            HtmlEmail email = new HtmlEmail();
            //设置发邮件的用户名和密码（授权密码）
            email.setAuthentication("yx_827895233","xinxin123456");
            //设置发送邮件服务器（SMTP服务器）域名
            email.setHostName("smtp.163.com");
            //收件人的邮箱
            email.addTo("913494146@qq.com");
            //设置发件人的邮箱
            email.setFrom("yx_827895233@163.com");
            //设置邮件的主题
            email.setSubject("这是一个用于测试的邮件！");
            //设置编码方式为了防止中文乱码
            email.setCharset("GB2312");
            //邮件的内容
            email.setHtmlMsg("测试邮件内容！");
            //发送邮件
            email.send();

        } catch (EmailException e) {
            throw new RuntimeException(e);
        }
    }
}
