package cn.conststar.wall.utils;

import cn.conststar.wall.exception.BusinessException;
import cn.conststar.wall.service.verifyCode.VerifyCodeService;

public class VerifyCode {

    //创建时间
    private Long createTime = 0L;

    //旋转验证码
    private Integer rotateCode = null;
    //图片验证码获取时间
    private Long rotateCodeTime = 0L;

    //邮箱验证码
    private String emailCode = null;
    //验证的邮箱
    private String emailVerify = null;
    //邮箱验证码获取时间
    private Long emailCodeTime = 0L;
    //邮箱验证码验证验证次数
    private Integer emailCodeVerifyAns = 0;

    //邮箱验证码获取次数
    private Integer emailCodeAns = 0;


    /*设置*/

    //验证码失效时间
    public static final int OBJECT_OVERDUE = 60 * 12;
    public static final int ROTATE_CODE_OVERDUE = 5;
    public static final int EMAIL_CODE_OVERDUE = 60;

    //验证码获取间隔
    public static final int EMAIL_SLEEP = 1;

    //验证码获取次数
    public static final int EMAIL_EXCEED = 5;

    //验证码错误次数
    public static final int EMAIL_VERIFY_EXCEED = 5;

    //旋转验证码精度
    public static final int ROTATE_VERIFY_ANGLE = 20;

    //构造函数
    public VerifyCode() {
        createTime = System.currentTimeMillis();
    }

    //对象是否需要删除
    public boolean isObjectOverdue() {
        //超过一天就过期
        return System.currentTimeMillis() - createTime > OBJECT_OVERDUE * 1000 * 60;
    }


    //判断 邮箱验证码
    public boolean isVerifyEmailCode(String code) {
        return emailCode.equalsIgnoreCase(code);
    }

    //判断 旋转验证码
    public boolean isVerifyRotateCode(int code) {
        code = (code + 360) % 360;

        if (code == rotateCode)
            return true;

        if (code > rotateCode)
            return code - rotateCode < ROTATE_VERIFY_ANGLE;
        else
            return rotateCode - code < ROTATE_VERIFY_ANGLE;
    }


    //设置 旋转验证码
    public void setRotateCodeArgs(int angle) {
        long time = System.currentTimeMillis();

        createTime = time;
        rotateCode = angle;
        rotateCodeTime = time;
    }

    //设置 邮箱验证码
    public void setEmailCodeArgs(String code, String email) {
        Long time = System.currentTimeMillis();

        createTime = time;
        emailCode = code;
        emailVerify = email;
        emailCodeTime = time;
        emailCodeVerifyAns = 0;
    }

    //清空 旋转验证码
    public void clearRotateCode() {
        rotateCode = null;
        rotateCodeTime = 0L;
    }

    //清空 邮箱验证码
    public void clearEmailCode() {
        emailCode = null;
        emailVerify = null;
        emailCodeTime = 0L;
        emailCodeVerifyAns = 0;
    }

    //验证 旋转验证码
    public void verifyRotateCode(int code) {
        try {
            if (this.rotateCode == null)
                throw new BusinessException("请先获取旋转验证码");

            if (System.currentTimeMillis() - rotateCodeTime > ROTATE_CODE_OVERDUE * 1000 * 60)
                throw new BusinessException("旋转验证码已失效");

            if (!this.isVerifyRotateCode(code)) {
                throw new BusinessException("角度不对，再试试吧！");
            }
        } finally {
            //不管是否验证成功都要清除旋转验证码
            this.clearRotateCode();
        }
    }

    //验证 邮箱验证码
    public void verifyEmailCode(String code) {
        if (emailCode == null || emailCode.isEmpty())
            throw new BusinessException("请先获取邮箱验证码");

        if (System.currentTimeMillis() - emailCodeTime > EMAIL_CODE_OVERDUE * 1000 * 60)
            throw new BusinessException("邮箱验证码已失效");

        if (emailCodeVerifyAns > EMAIL_VERIFY_EXCEED)
            throw new BusinessException("邮箱验证码错误次数超过" + EMAIL_VERIFY_EXCEED + "次，请重新邮箱获取验证码");

        if (!this.isVerifyEmailCode(code)) {
            //累加验证次数
            emailCodeVerifyAns++;
            throw new BusinessException("邮箱验证码有误");
        }

        //清除验证过的邮箱验证码
        this.clearEmailCode();
    }

    //获取并发送邮箱验证码
    public void sendEmailCode(String email) throws Exception {
        if (emailCodeAns > EMAIL_EXCEED)
            throw new BusinessException("今日获取邮箱验证码次数超过" + EMAIL_EXCEED + "次");

        if (System.currentTimeMillis() - emailCodeTime < EMAIL_SLEEP * 1000 * 60)
            throw new BusinessException(EMAIL_SLEEP + "分钟后才能获取新的验证码");


        String code = VerifyCodeService.generateVerifyCode(5);

        EmailSend.sendAsync("星愿表白墙-验证码",
                "<div class=\"content_head\"><span>邮箱验证码</span></div>" +
                        "<div class=\"content_body\">" +
                        "<p>您请求的邮箱验证码为：<span class=\"code\">" + code + "</span></p>" +
                        "<p>请在网页中填写，完成验证。</p>" +
                        "<p>(验证码2小时内有效)</p>" +
                        "<p style=\"margin-top:15px;\">如果此验证码非您本人所请求，请直接忽视。</p></div>",
                email);

        //累加获取次数
        emailCodeAns++;

        //设置邮箱验证码
        this.setEmailCodeArgs(code, email);
    }

}
