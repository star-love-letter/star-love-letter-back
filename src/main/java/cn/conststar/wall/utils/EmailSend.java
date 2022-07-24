package cn.conststar.wall.utils;

import cn.conststar.wall.config.GlobalConfig;
import cn.conststar.wall.pojo.model.CommentDomain;
import cn.conststar.wall.pojo.model.TableDomain;
import cn.conststar.wall.pojo.model.UserDomain;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class EmailSend {

    private static String template;

    private static Map<String, Long> userNotifyTime = new HashMap<>();


    public static void loadEmail() {
        try {
            InputStream path = EmailSend.class.getResourceAsStream("/email-template.html");

            int len = path.available();
            byte[] bytes = new byte[len];
            path.read(bytes);
            path.close();
            template = new String(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static JavaMailSenderImpl getMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(GlobalConfig.smtpHost);
        mailSender.setPort(GlobalConfig.smtpPort);
        mailSender.setUsername(GlobalConfig.smtpUsername);
        mailSender.setPassword(GlobalConfig.smtpPassword);

        Properties properties = new Properties();
        properties.setProperty("mail.smtp.ssl.enable", String.valueOf(GlobalConfig.smtpSSL));
        mailSender.setJavaMailProperties(properties);
        return mailSender;
    }

    /**
     * @param subject     主题
     * @param body        替换的内容
     * @param receiveMail 收件人
     */
    public static void send(String subject, String body, String receiveMail) throws Exception {
        JavaMailSenderImpl mailSender = getMailSender();

        String content = null;
        if (template != null) {
            content = template.replaceAll("\\{CODE\\}", body);
        } else {
            content = body;
        }

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");

        helper.setSubject(subject);
        helper.setText(content, true);

        helper.setTo(receiveMail);
        helper.setFrom(GlobalConfig.smtpUsername);

        mailSender.send(mimeMessage);
    }

    //发送邮件
    public static void sendAsync(String subject, String body, String receiveMail) throws Exception {
        new Thread(() -> {
            try {
                send(subject, body, receiveMail);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    /**
     * 发送评论通知
     *
     * @param table   帖子
     * @param comment 评论
     * @param user    评论者
     */
    public static void sendNotifyComment(TableDomain table, CommentDomain comment, UserDomain user) throws Exception {

        //检查是否频繁发送
        Long last = userNotifyTime.get(user.getEmail());
        if (last != null && System.currentTimeMillis() - last < GlobalConfig.userNotifyEmailPeriod * 1000) {
            return;
        }


        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("<div class=\"content_head\"><span>刚刚有人评论了你的表白</span></div><div class=\"content_body\"><div>");
        messageBuilder.append("<p>评论者: " + user.getName() + "</p>");
        messageBuilder.append("<p>评论内容: " + comment.getContent());
        messageBuilder.append(table.getImages() != null ? "<p>图片: " + table.getImages() + "</p>" : "");
        messageBuilder.append("</p>");

        int state = comment.getState();
        if (state == 1)
            messageBuilder.append("<p>评论状态: 待审核</p>");
        else if (state == -1)
            messageBuilder.append("<p>评论状态: 已封禁</p>");

        messageBuilder.append("<p>评论时间: " + comment.getCreateTime() + "</p>");
        messageBuilder.append("<p>帖子地址: <a href=\"https://wall.conststar.cn/#/TableDetail/" + table.getId() + "\">打开帖子</a></p>");
        messageBuilder.append("</div></div>");

        sendAsync("刚刚有人评论了你的表白", messageBuilder.toString(), user.getEmail());

        userNotifyTime.put(user.getEmail(), System.currentTimeMillis());
    }

}