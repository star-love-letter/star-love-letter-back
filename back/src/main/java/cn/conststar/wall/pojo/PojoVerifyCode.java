package cn.conststar.wall.pojo;

import lombok.*;

@AllArgsConstructor
public class PojoVerifyCode {

    //创建时间
    private Long createTime = 0L;

    //图片验证码
    private String imageCode = "";
    //图片验证码获取时间
    private Long imageCodeTime = 0L;
    //图片验证码验证验证次数
    private Integer imageCodeVerifyAns = 0;

    //邮箱验证码
    private String emailCode = "";
    //验证的邮箱
    private String emailVerify = "";
    //邮箱验证码获取时间
    private Long emailCodeTime = 0L;
    //邮箱验证码验证验证次数
    private Integer emailCodeVerifyAns = 0;

    //邮箱验证码获取次数
    private Integer emailCodeAns = 0;


    //设置
    public static final int OBJECT_OVERDUE = 60 * 12; //12小时
    public static final int IMAGE_CODE_OVERDUE = 5;
    public static final int EMAIL_CODE_OVERDUE = 60;
    public static final int EMAIL_SLEEP = 1;
    public static final int EMAIL_EXCEED = 5;
    public static final int IMAGE_VERIFY_EXCEED = 5;
    public static final int EMAIL_VERIFY_EXCEED = 5;


    //构造函数
    public PojoVerifyCode() {
        createTime = System.currentTimeMillis();
    }


    //是否获取 图形验证码
    public boolean isImageCodeEmpty() {
        return imageCode.isEmpty();
    }

    //是否获取 邮箱验证码
    public boolean isEmailCodeEmpty() {
        return emailCode.isEmpty();
    }

    //验证 图片验证码
    public boolean isVerifyImageCode(String code) {
        return imageCode.equalsIgnoreCase(code);
    }

    //验证 邮箱验证码
    public boolean isVerifyEmailCode(String code) {
        return emailCode.equalsIgnoreCase(code);
    }


    //对象是否需要删除
    public boolean isObjectOverdue() {
        //超过一天就过期
        return System.currentTimeMillis() - createTime > OBJECT_OVERDUE * 1000 * 60;
    }

    //图形验证码是否失效
    public boolean isImageCodeOverdue() {
        //五分钟后 图形验证码失效
        return System.currentTimeMillis() - imageCodeTime > IMAGE_CODE_OVERDUE * 1000 * 60;
    }

    //邮箱验证码是否失效
    public boolean isEmailCodeOverdue() {
        //一小时后 邮箱验证码失效
        return System.currentTimeMillis() - emailCodeTime > EMAIL_CODE_OVERDUE * 1000 * 60;
    }

    //是否可以发送邮箱验证码
    public boolean isEmailSleep() {
        //一分钟后 才能获取邮箱验证码
        return System.currentTimeMillis() - emailCodeTime < EMAIL_SLEEP * 1000 * 60;
    }

    //邮箱验证码 获取次数过多
    public boolean isEmailExceed() {
        return emailCodeAns > EMAIL_EXCEED;
    }

    //图形验证码 验证次数过多
    public boolean isImageVerifyExceed() {
        return imageCodeVerifyAns > IMAGE_VERIFY_EXCEED;
    }

    //邮箱验证码 验证次数过多
    public boolean isEmailVerifyExceed() {
        return imageCodeVerifyAns > EMAIL_VERIFY_EXCEED;
    }


    //累加 邮箱验证码 获取次数
    public void addEmailCodeAns() {
        emailCodeAns++;
    }

    //累加 图片验证码 验证次数
    public void addImageCodeVerifyAns() {
        imageCodeVerifyAns++;
    }

    //累加 邮箱验证码 验证次数
    public void addEmailCodeVerifyAns() {
        emailCodeVerifyAns++;
    }


    public void setImageCodeArgs(String code) {
        long time = System.currentTimeMillis();

        createTime = time;
        imageCode = code;
        imageCodeTime = time;
        imageCodeVerifyAns = 0;
    }

    public void setEmailCodeArgs(String code, String email) {
        Long time = System.currentTimeMillis();

        createTime = time;
        emailCode = code;
        emailVerify = email;
        emailCodeTime = time;
        emailCodeVerifyAns = 0;
    }

    //清空 图片验证码
    public void clearImageCode() {
        imageCode = "";
        imageCodeTime = 0L;
        imageCodeVerifyAns = 0;
    }

    //清空 邮箱验证码
    public void clearEmailCode() {
        emailCode = "";
        emailVerify = "";
        emailCodeTime = 0L;
        emailCodeVerifyAns = 0;
    }

}
