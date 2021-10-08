package cn.conststar.wall.utils;

import lombok.SneakyThrows;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.Date;
import java.util.Properties;

@Component
@PropertySource("classpath:/email.properties")
public class UtilsEmail {

    static Logger logger = Logger.getLogger(UtilsEmail.class);

    static private String emailAccount;

    static private String emailPassword;

    static private String emailSMTPHost;

    static private String port;

    static private Boolean ssl;

    static private String sendName;

    static private String template;

    static {
        try {
            InputStream path = UtilsEmail.class.getResourceAsStream("/email-template.html");
            assert path != null : "email-template.html文件不存在";

            int len = path.available();
            byte[] bytes = new byte[len];
            path.read(bytes);
            path.close();
            template = new String(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

    @Value("${emailAccount}")
    public void setEmailAccount(String emailAccount) {
        UtilsEmail.emailAccount = emailAccount;
    }

    @Value("${emailPassword}")
    public void setEmailPassword(String emailPassword) {
        UtilsEmail.emailPassword = emailPassword;
    }

    @Value("${emailSMTPHost}")
    public void setEmailSMTPHost(String emailSMTPHost) {
        UtilsEmail.emailSMTPHost = emailSMTPHost;
    }

    @Value("${port}")
    public void setPort(String port) {
        UtilsEmail.port = port;
    }

    @Value("${ssl}")
    public void setSsl(Boolean ssl) {
        UtilsEmail.ssl = ssl;
    }

    @Value("${sendName}")
    public void setSendName(String sendName) {
        UtilsEmail.sendName = sendName;
    }

    //异步发送邮箱
    public static void sendAsync(String subject, String content, String receiveMail, String receiveName) {
        try {
            Thread t = new Thread() {
                @Override
                public void run() {
                    try {
                        UtilsEmail.send(subject, content, receiveMail, receiveName);
                    } catch (Exception ex) {
                        logger.error(ex.toString());
                    }
                }
            };
            t.start();
        } catch (Exception ex) {
            logger.error(ex.toString());
        }
    }

    /**
     * @param subject     主题
     * @param body        替换的内容
     * @param receiveMail 收件人
     * @param receiveName 收件人名称
     */
    public static void send(String subject, String body, String receiveMail, String receiveName) throws Exception {

        String content = template.replaceAll("\\{CODE\\}", body);

        // 配置并创建会话对象
        Session session = createSession(emailSMTPHost, ssl, port);

        // 创建一封邮件
        MimeMessage message = createMimeMessage(session, emailAccount,
                receiveMail, sendName, receiveName, subject, content);

        // 获取邮件传输对象
        Transport transport = session.getTransport();
        transport.connect(emailAccount, emailPassword);

        // 发送邮件
        transport.sendMessage(message, message.getAllRecipients());

        // 关闭连接
        transport.close();
    }

    // 配置并创建会话对象
    private static Session createSession(String emailSMTPHost, boolean ssl, String port) {
        // 1. 创建参数配置, 用于连接邮件服务器的参数配置
        Properties props = new Properties();                    // 参数配置
        props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", emailSMTPHost);     // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");            // 需要请求认证


        // SSL
        props.setProperty("mail.smtp.port", port);

        if (ssl) {
            props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.setProperty("mail.smtp.socketFactory.fallback", "false");
            props.setProperty("mail.smtp.socketFactory.port", port);
        }


        // 2. 根据配置创建会话对象, 用于和邮件服务器交互
        Session session = Session.getInstance(props);
        // 设置为debug模式, 可以查看详细的发送 log
//        session.setDebug(true);
        return session;
    }

    private static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail,
                                                 String sendName, String receiveName, String subject, String content) throws Exception {
        // 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);

        // 2. From: 发件人
        message.setFrom(new InternetAddress(sendMail, sendName, "UTF-8"));

        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, receiveName, "UTF-8"));

        // 4. Subject: 邮件主题
        message.setSubject(subject, "UTF-8");

        // 5. Content: 邮件正文（可以使用html标签）
        message.setContent(content, "text/html;charset=UTF-8");
        // 6. 设置发件时间
        message.setSentDate(new Date());

        // 7. 保存设置
        message.saveChanges();

        return message;
    }
}