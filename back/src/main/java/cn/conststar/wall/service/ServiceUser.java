package cn.conststar.wall.service;

import cn.conststar.wall.exception.ExceptionMain;
import cn.conststar.wall.mapper.MapperUser;
import cn.conststar.wall.pojo.PojoToken;
import cn.conststar.wall.pojo.PojoUserPublic;
import cn.conststar.wall.pojo.PojoUser;
import cn.conststar.wall.pojo.PojoVerifyCode;
import cn.conststar.wall.response.ResponseCodeEnums;
import cn.conststar.wall.utils.UtilsImage;
import cn.conststar.wall.utils.UtilsVerifyCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.ByteArrayOutputStream;
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

    private Map getWeChat(String code) throws Exception {
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.get("https://api.weixin.qq.com/sns/jscode2session" +
                        "?appid=wx6aab5a7855da31e7" +
                        "&secret=e873f76b236b50435d06f72bc790fff0" +
                        "&js_code=" +
                        code +
                        "&grant_type=authorization_code")
                .asString();
        String body = response.getBody();

        HashMap data;
        ObjectMapper mapper = new ObjectMapper();
        data = mapper.readValue(body, HashMap.class);

        Integer errcode = (Integer) data.get("errcode");
        if (errcode != null && 0 != errcode)
            throw new ExceptionMain((String) data.get("errmsg"));

        return data;
    }


    //登录用户  通过 id || 邮箱
    public String loginMakeToken(String id, String password) throws Exception {
        PojoUser user = findUser(id);
        if (user == null)
            throw new ExceptionMain("用户不存在");

        PojoToken pojoToken = new PojoToken(user.getId(), PojoToken.TYPE.Email, id);
        String token = pojoToken.toToken();

        login(id, password, token);

        return token;
    }

    //通过微信登录用户
    public String loginByWeChatCode(String code) throws Exception {
        Map data = getWeChat(code);
        String openid = (String) data.get("openid");
        String sessionKey = (String) data.get("session_key");

        PojoUser user = findUserByWeChat(openid);
        if (user == null) {
            throw new ExceptionMain("微信用户未注册", ResponseCodeEnums.CODE_20001);
        }

        PojoToken pojoToken = new PojoToken(user.getId(), PojoToken.TYPE.WeChat, sessionKey);
        String token = pojoToken.toToken();

        boolean res = loginByWeChat(openid, token);
        if (!res)
            throw new ExceptionMain("登录失败,数据库出现问题", ResponseCodeEnums.CODE_1000);

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

        PojoToken pojoToken = PojoToken.getToken(token);
        if (pojoToken.isExpire())
            throw new ExceptionMain("用户登录已失效", ResponseCodeEnums.CODE_20001);

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

    //获取用户公开信息
    @Override
    public PojoUserPublic getUserPublic(int id) throws Exception {
        PojoUserPublic user = mapperUser.getUserPublic(id);
        if (user == null)
            throw new ExceptionMain("用户不存在");
        return user;
    }

    //登录用户  通过 id || 邮箱
    @Override
    public boolean login(String id, String password, String token) throws Exception {
        boolean b = mapperUser.login(id, password, token);
        if (!b)
            throw new ExceptionMain("请检查账号和密码", ResponseCodeEnums.CODE_201);
        return true;
    }

    //通过微信登录用户
    @Override
    public boolean loginByWeChat(String openId, String token) throws Exception {
        boolean b = mapperUser.loginByWeChat(openId, token);
        if (!b)
            throw new ExceptionMain("没有此微信用户", ResponseCodeEnums.CODE_201);
        return true;
    }

    @Override
    public boolean logout(String token) throws Exception {
        boolean b = mapperUser.logout(token);
        if (!b)
            throw new ExceptionMain("用户登录状态异常", ResponseCodeEnums.CODE_20001);

        return true;
    }


    //通过邮箱注册用户
    @Override
    public int addUserByEmail(String email, String password, String name, int status) throws Exception {

        if (!isEmail(email))
            throw new ExceptionMain("邮箱格式有误");

        if (this.findUserByEmail(email) != null)
            throw new ExceptionMain("用户已存在");

        if (password.length() <= 6 || password.length() >= 18)
            throw new ExceptionMain("密码必须大于6个字符串小于18个字符");

        if (name.length() > 6)
            throw new ExceptionMain("名称不得超过6个字符");

        if (name.isEmpty())
            throw new ExceptionMain("名称不能为空");

        int line = mapperUser.addUserByEmail(email, password, name, status);

        if (line != 1) {
            throw new ExceptionMain("数据库操作失败，数据库添加行数为" + line, ResponseCodeEnums.CODE_50002); //wait
        }
        return line;
    }

    //通过微信注册用户
    @Override
    public int addUserByWeChat(String openId, String password, String name, int status) throws Exception {

        if (this.findUserByWeChat(openId) != null)
            throw new ExceptionMain("微信用户已存在");

        if (password.length() <= 6 || password.length() >= 18)
            throw new ExceptionMain("密码必须大于6个字符串小于18个字符");

        if (name.length() > 6)
            throw new ExceptionMain("名称不得超过6个字符");

        if (name.isEmpty())
            throw new ExceptionMain("名称不能为空");

        int line = mapperUser.addUserByWeChat(openId, password, name, status);

        if (line != 1) {
            throw new ExceptionMain("数据库操作失败，数据库添加行数为" + line, ResponseCodeEnums.CODE_50002); //wait
        }
        return line;
    }

    //通过微信Code注册用户
    public void addUserByWeChatCode(String code, String password, String name, int status) throws Exception {
        Map wechat = getWeChat(code);
        String openid = (String) wechat.get("openid");

        addUserByWeChat(openid, password, name, status);
    }

    //绑定微信
    @Override
    public void bindWeChat(String id, String openId) {
        PojoUser weChat = findUserByWeChat(openId);
        if (weChat != null)
            throw new ExceptionMain("微信用户已存在");

        mapperUser.bindWeChat(id, openId);
    }

    //绑定微信 会覆盖之前绑定的微信
    public void bindWeChatByCode(String code, String id, String password) throws Exception {
        loginMakeToken(id, password); //验证用户账号和密码

        Map weChat = getWeChat(code);
        String openid = (String) weChat.get("openid");
        bindWeChat(id, openid);
    }

    //查找用户  通过 id || 邮箱
    @Override
    public PojoUser findUser(String id) {
        return mapperUser.findUser(id);
    }


    //通过邮箱查找用户
    @Override
    public PojoUser findUserByEmail(String email) {
        return mapperUser.findUserByEmail(email);
    }

    //通过微信查找用户
    @Override
    public PojoUser findUserByWeChat(String openId) {
        return mapperUser.findUserByWeChat(openId);
    }

    //是否通过微信注册过
    public boolean isAddedByWeChatCode(String code) throws Exception {
        Map wechat = getWeChat(code);
        String openid = (String) wechat.get("openid");

        return findUserByWeChat(openid) != null;
    }

    //验证码 临时实现方法 并不优雅

    //获取验证码对象
    public PojoVerifyCode getPojoVerifyCode(String email) {
        if (!isEmail(email))
            throw new ExceptionMain("邮箱格式有误");

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

    //获取 旋转验证码
    public String getRotateCode(String email) throws Exception {
        PojoVerifyCode pojoVerifyCode = getPojoVerifyCode(email);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int angle = UtilsVerifyCode.getRotateImage(out);
        pojoVerifyCode.setRotateCodeArgs(angle);

        return UtilsImage.base64(out, "png");
    }

    //验证 旋转验证码
    public void verifyRotateCode(String email, int angle) throws ExceptionMain {
        PojoVerifyCode pojoVerifyCode = getPojoVerifyCode(email);
        pojoVerifyCode.verifyRotateCode(angle);
    }

    //获取 邮箱验证码
    public void getEmailCode(String email, int angle) throws Exception {
        PojoVerifyCode pojoVerifyCode = getPojoVerifyCode(email);

        //验证旋转验证码
        pojoVerifyCode.verifyRotateCode(angle);

        //发送邮箱验证码
        pojoVerifyCode.sendEmailCode(email);
    }

    //验证 邮箱验证码
    public void verifyEmailCode(String email, String code) {
        PojoVerifyCode pojoVerifyCode = getPojoVerifyCode(email);
        pojoVerifyCode.verifyEmailCode(code);
    }
}
