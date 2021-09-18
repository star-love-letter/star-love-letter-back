package cn.conststar.wall.service;

import cn.conststar.wall.exception.ExceptionMain;
import cn.conststar.wall.mapper.MapperUser;
import cn.conststar.wall.pojo.PojoUserPublic;
import cn.conststar.wall.pojo.PojoUser;
import cn.conststar.wall.pojo.PojoVerifyCode;
import cn.conststar.wall.response.ResponseCodeEnums;
import cn.conststar.wall.utils.UtilsEmail;
import cn.conststar.wall.utils.UtilsMain;
import cn.conststar.wall.utils.UtilsVerifyCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceUser implements MapperUser {

    private static Map<String, PojoVerifyCode> verifyCodeMap = new HashMap<>();

    private MapperUser mapperUser;


    private boolean isEmail(String email) {
        Pattern emailPattern = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        Matcher matcher = emailPattern.matcher(email);
        return matcher.find();
    }

    public String login(String email, String password) throws Exception {
        String token = email + " " + UtilsMain.HMACSHA256(UtilsMain.getUUID());
        login(email, password, token);

        return token;
    }

    //获取用户id
    //获取错误返回 -1
    //不抛出异常
    public int getUserId(String token) {
        try {
            return getUser(token).getId();
        } catch (Exception ignored) {
        }
        return -1;
    }

    @Override
    public PojoUser getUser(String token) throws Exception {
        if (token == null || token.isEmpty())
            throw new ExceptionMain("用户未登录", ResponseCodeEnums.CODE_20001);

        PojoUser user = mapperUser.getUser(token);
        if (user == null)
            throw new ExceptionMain("用户登录已失效", ResponseCodeEnums.CODE_20001);

        int status = user.getStatus();

        switch (status) {
            case 0:
                break;
            case -1:
                throw new ExceptionMain("用户已被封禁");
            case 1:
                throw new ExceptionMain("用户等待审核");
        }

        return user;
    }

    @Override
    public PojoUserPublic getUserPublic(int id) throws Exception {
        PojoUserPublic user = mapperUser.getUserPublic(id);
        if (user == null)
            throw new ExceptionMain("用户不存在");
        return user;
    }

    @Override
    public boolean login(String email, String password, String token) throws Exception {
        boolean b = mapperUser.login(email, password, token);
        if (!b)
            throw new ExceptionMain("请检查账号和密码", ResponseCodeEnums.CODE_201);
        return true;
    }

    @Override
    public boolean logout(String token) throws Exception {
        boolean b = mapperUser.logout(token);
        if (!b)
            throw new ExceptionMain("用户登录状态异常", ResponseCodeEnums.CODE_20001);

        return true;
    }


    @Override
    public int addUser(String email, String password, String name, int status) throws Exception {

        if (!isEmail(email))
            throw new ExceptionMain("邮箱格式有误");

        if (password.length() <= 6 || password.length() >= 18)
            throw new ExceptionMain("密码必须大于6个字符串小于18个字符");

        if (name.length() > 6)
            throw new ExceptionMain("名称不得超过6个字符");

        if (name.isEmpty())
            throw new ExceptionMain("名称不能为空");

        if (this.findUser(email))
            throw new ExceptionMain("用户已存在");

        int line = mapperUser.addUser(email, password, name, status);

        if (line != 1) {
            throw new ExceptionMain("数据库操作失败，数据库添加行数为" + line, ResponseCodeEnums.CODE_50002); //wait
        }
        return line;
    }

    @Override
    public boolean findUser(String email) {
        return mapperUser.findUser(email);
    }


    //验证码 临时实现方法 并不优雅

    //获取验证码对象
    public PojoVerifyCode getPojoVerifyCode(String email) {

        PojoVerifyCode verifyCode = verifyCodeMap.get(email);
        if (verifyCode == null) {
            PojoVerifyCode pojoVerifyCode = new PojoVerifyCode();
            verifyCodeMap.put(email, pojoVerifyCode);
            return pojoVerifyCode;
        }
        return verifyCode;
    }

    //清除过期验证码
    public static void clearOverdue() {
        synchronized (verifyCodeMap) {
            for (String key : verifyCodeMap.keySet()) {
                PojoVerifyCode pojoVerifyCode = verifyCodeMap.get(key);

                //删除过期的验证码
                if (pojoVerifyCode.isObjectOverdue())
                    verifyCodeMap.remove(key);
            }
        }
    }

    public void removePojoVerifyCode(String email) {
        synchronized (verifyCodeMap) {
            verifyCodeMap.remove(email);
        }
    }

    public String getVerifyImage(String email) throws Exception {
        if (!isEmail(email))
            throw new ExceptionMain("邮箱格式有误");

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        String code = UtilsVerifyCode.outputVerifyImage(80, 30, out, 4);

        PojoVerifyCode pojoVerifyCode = getPojoVerifyCode(email);
        pojoVerifyCode.setImageCodeArgs(code);

        String imgBase64 = Base64.getEncoder().encodeToString(out.toByteArray());
        return "data:image/png;base64," + imgBase64;
    }

    public boolean isVerifyImage(String code, String email) throws ExceptionMain {
        PojoVerifyCode pojoVerifyCode = getPojoVerifyCode(email);

        if (pojoVerifyCode.isImageCodeEmpty())
            throw new ExceptionMain("请先获取图片验证码");

        if (pojoVerifyCode.isImageCodeOverdue())
            throw new ExceptionMain("图片验证码已失效");

        if (pojoVerifyCode.isImageVerifyExceed())
            throw new ExceptionMain("图片验证码错误次数过多，请重新图片获取验证码");


        if (!pojoVerifyCode.isVerifyImageCode(code)) {
            //累加验证次数
            pojoVerifyCode.addImageCodeVerifyAns();
            throw new ExceptionMain("图片验证码有误");
        }

        //清除验证过的图片验证码
        pojoVerifyCode.clearImageCode();
        return true;
    }

    public void getVerifyEmail(String email, String imageCode) throws Exception {
        PojoVerifyCode pojoVerifyCode = getPojoVerifyCode(email);

        if (!isEmail(email))
            throw new ExceptionMain("邮箱格式有误");

        if (!isVerifyImage(imageCode, email)) {
            throw new ExceptionMain("图片验证码错误");
        }

        if (pojoVerifyCode.isEmailExceed())
            throw new ExceptionMain("今日获取邮箱验证码次数过多");

        if (pojoVerifyCode.isEmailSleep())
            throw new ExceptionMain(PojoVerifyCode.EMAIL_SLEEP + "分钟后才能获取新的验证码");


        String code = UtilsVerifyCode.generateVerifyCode(5);
        UtilsEmail.send("星愿表白墙-验证码", "您的验证码为：" + code, email, "");

        //累加获取次数
        pojoVerifyCode.addEmailCodeAns();

        //设置邮箱验证码
        pojoVerifyCode.setEmailCodeArgs(code, email);
    }

    public boolean isVerifyEmail(String email, String code) throws Exception {
        PojoVerifyCode pojoVerifyCode = getPojoVerifyCode(email);

//        String emailVerify = pojoVerifyCode.getEmailVerify();

        if (pojoVerifyCode.isEmailCodeEmpty())
            throw new ExceptionMain("请先获取邮箱验证码");

        //旧方法
//        if (!emailVerify.equals(email))
//            throw new ExceptionMain("更换邮箱后请重新获取短信验证码");

        //一小时后 邮箱验证码失效
        if (pojoVerifyCode.isEmailCodeOverdue())
            throw new ExceptionMain("邮箱验证码已失效");

        if (pojoVerifyCode.isEmailVerifyExceed())
            throw new ExceptionMain("邮箱验证码错误次数过多，请重新邮箱获取验证码");

        if (!pojoVerifyCode.isVerifyEmailCode(code)) {
            //累加验证次数
            pojoVerifyCode.addEmailCodeVerifyAns();
            throw new ExceptionMain("邮箱验证码有误");
        }
        return true;
    }
}
