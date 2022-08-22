package cn.conststar.wall.config;

public class GlobalConfig {

    // SMTP设置
    public static String smtpHost;
    public static Integer smtpPort = 25;
    public static String smtpUsername;
    public static String smtpPassword;
    public static Boolean smtpSSL = false;


    // 用户设置
    public static Integer userImageMaxNumberDay = 27; // 允许用户一天上传的图片数量
    public static Long userNotifyEmailPeriod = 7200L; // 最低多长时间通知一次 单位为秒

    // 帖子设置
    public static Integer tableImageMaxNumber = 9; // 帖子图片最多数量

    // 评论设置
    public static Integer commentImageMaxNumber = 3; // 评论图片最多数量

    // 页面显示设置
    public static String viewStatsCode; // 统计代码
    public static String viewHeader;    // 页面头部
    public static String viewFooter;        // 页面底部
    public static Boolean viewStop = false;         // 关闭网站
    public static String viewStopReason;    // 关闭网站的原因


    // 日志设置
    public static Integer logMaxNumber = 500; // 日志最大保存数量 最低200起步

    // 跨域设置
    public static String corsOrigin;

}
