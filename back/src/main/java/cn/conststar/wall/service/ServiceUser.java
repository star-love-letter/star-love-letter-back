package cn.conststar.wall.service;

import cn.conststar.wall.exception.ExceptionMain;
import cn.conststar.wall.mapper.MapperUser;
import cn.conststar.wall.pojo.PojoUser;
import cn.conststar.wall.pojo.PojoVerifyCode;
import cn.conststar.wall.utils.UtilsEmail;
import cn.conststar.wall.utils.UtilsVerifyCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceUser implements MapperUser {

    private MapperUser mapperUser;

    private boolean isEmail(String email) {
        Pattern emailPattern = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        Matcher matcher = emailPattern.matcher(email);
        return matcher.find();
    }

    public PojoUser login(String email, String password) throws Exception {
        PojoUser user = getUser(email, password);
        updateLastTime(user);
        return user;
    }

    @Override
    public PojoUser getUser(String email, String password) throws Exception {
        PojoUser user = mapperUser.getUser(email, password);
        if (user == null)
            throw new ExceptionMain("请检查账号和密码", ExceptionMain.NOT_LOGIN);
        return user;
    }

    @Override
    public int updateLastTime(PojoUser pojoUser) throws Exception {
        return mapperUser.updateLastTime(pojoUser);
    }

    @Override
    public boolean verifyUser(PojoUser pojoUser) throws Exception {
        if (pojoUser == null)
            throw new ExceptionMain("用户未登录", ExceptionMain.NOT_LOGIN);
        boolean b = mapperUser.verifyUser(pojoUser);
        if (!b)
            throw new ExceptionMain("用户登录已失效", ExceptionMain.NOT_LOGIN);

        return true;
    }

    @Override
    public int addUser(String email, String password, String name) throws Exception {

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

        int line = mapperUser.addUser(email, password, name);

        if (line != 1) {
            throw new ExceptionMain("数据库操作失败，数据库添加行数为" + line, ExceptionMain.DEADLY); //wait
        }
        return line;
    }

    @Override
    public boolean findUser(String email) {
        return mapperUser.findUser(email);
    }


    //验证码 多个页面的时候会出现问题

    public PojoVerifyCode getPojoVerifyCode(HttpSession session) {

        Object verifyCode = session.getAttribute("VerifyCode");
        if (verifyCode != null) {
            return (PojoVerifyCode) verifyCode;
        }

        PojoVerifyCode pojoVerifyCode = new PojoVerifyCode();
        session.setAttribute("VerifyCode", pojoVerifyCode);
        return pojoVerifyCode;
    }

    public void removePojoVerifyCode(HttpSession session) {
        session.removeAttribute("VerifyCode");
    }

    public String getVerifyImage(HttpSession session) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        String code = UtilsVerifyCode.outputVerifyImage(80, 30, out, 4);

        PojoVerifyCode pojoVerifyCode = getPojoVerifyCode(session);

        pojoVerifyCode.setImageCode(code);
        pojoVerifyCode.setImageCodeTime(System.currentTimeMillis());
        pojoVerifyCode.setImageCodeVerifyAns(0);

        String imgBase64 = Base64.getEncoder().encodeToString(out.toByteArray());
        return "data:image/png;base64," + imgBase64;
    }

    public boolean isVerifyImage(String code, HttpSession session) throws ExceptionMain {
        PojoVerifyCode pojoVerifyCode = getPojoVerifyCode(session);

        String imageCode = pojoVerifyCode.getImageCode();
        if (imageCode.isEmpty())
            throw new ExceptionMain("请先获取图形验证码");

        Long time = System.currentTimeMillis();

        //五分钟后 图形验证码失效
        if (pojoVerifyCode.getImageCodeTime() + 1000 * 60 * 5 < time)
            throw new ExceptionMain("图形验证码已失效");

        Integer imageCodeVerifyAns = pojoVerifyCode.getImageCodeVerifyAns();
        if (imageCodeVerifyAns > 5)
            throw new ExceptionMain("图形验证码错误次数过多，请重新图形获取验证码");


        if (!imageCode.equalsIgnoreCase(code))
            throw new ExceptionMain("图形验证码有误");

        pojoVerifyCode.setImageCodeVerifyAns(imageCodeVerifyAns + 1);
        return true;
    }

    public void getVerifyEmail(String email, HttpSession session) throws Exception {
        PojoVerifyCode pojoVerifyCode = getPojoVerifyCode(session);

        Integer emailCodeAns = pojoVerifyCode.getEmailCodeAns();
        if (emailCodeAns > 5)
            throw new ExceptionMain("今日获取邮箱验证码次数过多");

        Long time = System.currentTimeMillis();
        Long emailCodeTime = pojoVerifyCode.getEmailCodeTime();
        //一分钟内 不得获取短信验证码
        if (emailCodeTime + 1000 * 60 > time)
            throw new ExceptionMain("1分钟后才能获取新的验证码");


        String code = UtilsVerifyCode.generateVerifyCode(5);
        UtilsEmail.send("星愿表白墙-验证码", "您的验证码为：" + code, email, "");

        pojoVerifyCode.setEmailCodeAns(emailCodeAns + 1);
        pojoVerifyCode.setEmailVerify(email);
        pojoVerifyCode.setEmailCode(code);
        pojoVerifyCode.setEmailCodeTime(time);
        pojoVerifyCode.setEmailCodeVerifyAns(0);

    }

    public boolean isVerifyEmail(String email, String code, HttpSession session) throws Exception {
        PojoVerifyCode pojoVerifyCode = getPojoVerifyCode(session);

        String emailVerify = pojoVerifyCode.getEmailVerify();
        String emailCode = pojoVerifyCode.getEmailCode();
        Long emailCodeTime = pojoVerifyCode.getEmailCodeTime();
        Integer emailCodeVerifyAns = pojoVerifyCode.getEmailCodeVerifyAns();

        if (emailCode.isEmpty())
            throw new ExceptionMain("请先获取短信验证码");

        if (!emailVerify.equals(email))
            throw new ExceptionMain("更换邮箱后请重新获取短信验证码");

        Long time = System.currentTimeMillis();
        //一小时后 邮箱验证码失效
        if (emailCodeTime + 1000 * 60 * 60 < time)
            throw new ExceptionMain("短信验证码已失效");

        pojoVerifyCode.setEmailCodeAns(emailCodeVerifyAns + 1);
        if (emailCodeVerifyAns > 5)
            throw new ExceptionMain("短信验证码错误次数过多，请重新短信获取验证码");

        if (!emailCode.equalsIgnoreCase(code))
            throw new ExceptionMain("短信验证码有误");

        return true;
    }
}
