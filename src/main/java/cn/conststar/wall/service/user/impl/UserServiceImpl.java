package cn.conststar.wall.service.user.impl;

import cn.conststar.wall.constant.StateCodeConstant;
import cn.conststar.wall.exception.BusinessException;
import cn.conststar.wall.dao.UserDao;
import cn.conststar.wall.constant.ResponseEnumConstant;
import cn.conststar.wall.pojo.dto.*;
import cn.conststar.wall.pojo.model.UserDomain;
import cn.conststar.wall.service.user.UserService;
import cn.conststar.wall.utils.HashUtils;
import cn.conststar.wall.utils.TextUtils;
import cn.conststar.wall.service.verifyCode.VerifyCodeService;
//import com.mashape.unirest.http.HttpResponse;
//import com.mashape.unirest.http.Unirest;
import cn.conststar.wall.utils.Tools;
import com.fasterxml.jackson.databind.ObjectMapper;
import kong.unirest.Unirest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private VerifyCodeService verifyCodeService;

    private Map getWeChat(String code) throws Exception {
        String body = Unirest.get("https://api.weixin.qq.com/sns/jscode2session" +
                "?appid=wx6aab5a7855da31e7" +
                "&secret=e873f76b236b50435d06f72bc790fff0" +
                "&js_code=" +
                code +
                "&grant_type=authorization_code").asString().getBody();

        HashMap data;
        ObjectMapper mapper = new ObjectMapper();
        data = mapper.readValue(body, HashMap.class);

        Integer errcode = (Integer) data.get("errcode");
        if (errcode != null && 0 != errcode)
            throw new BusinessException((String) data.get("errmsg"));

        String openid = (String) data.get("openid");
        String sessionKey = (String) data.get("session_key");
        if (openid == null || sessionKey == null)
            throw new BusinessException("通过code2Session未获取到微信用户信息", ResponseEnumConstant.CODE_60002);

        return data;
    }

    //在本地读取用户实体 同时验证用户是否登录
    public UserDomain getUserBySubject() throws Exception {
        Subject subject = SecurityUtils.getSubject();
        UserDomain userDomain = (UserDomain) subject.getPrincipal();
        if (userDomain == null) {
            throw new BusinessException(ResponseEnumConstant.CODE_20001);
        }

        return userDomain;
    }

    //在本地读取用户信息
    public UserInfoDto getUserInfoBySubject() throws Exception {
        return new UserInfoDto(getUserBySubject());
    }

    // 获取用户实体 userStr可以是id或者邮箱
    public UserDomain getUserDomain(String userStr) throws Exception {
        return userDao.getUserDomain(userStr);
    }

    //通过微信登录用户
    public UserInfoDto loginByWeChatCode(String code) throws Exception {
        Map data = getWeChat(code);
        String openid = (String) data.get("openid");

        UserDomain user = getUserByWeChat(openid);
        if (user == null) {
            throw new BusinessException("微信用户未注册", ResponseEnumConstant.CODE_20001);
        }

        return new UserInfoDto(user);
    }

    //获取用户公开信息
    public UserInfoPublicDto getUserInfoPublic(int id) throws Exception {
        getUserBySubject(); //验证用户登录状态

        UserInfoPublicDto user = userDao.getUserInfoPublic(id);
        if (user == null)
            throw new BusinessException("用户不存在");
        return user;
    }

    //登录用户  通过 id || 邮箱
    public UserInfoDto login(String userStr, String password) throws Exception {
        Subject user = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userStr, password);

        try {
            user.login(token);
        } catch (UnknownAccountException uae) {
            throw new BusinessException(ResponseEnumConstant.CODE_20004);
        } catch (IncorrectCredentialsException ice) {
            throw new BusinessException(ResponseEnumConstant.CODE_20002);
        }

        //更新登录时间
        userDao.updateLoginTime(userStr);

        return new UserInfoDto(getUserBySubject());
    }


    //退出登录
    public void logout() throws Exception {
        Subject user = SecurityUtils.getSubject();
        user.logout();
    }

    //通过微信获取用户
    private UserDomain getUserByWeChat(String openId) throws Exception {
        return userDao.getUserByWeChat(openId);
    }


    public void addUserByEmailCode(UserDomain user, String emailCode) throws Exception {
        //验证邮箱验证码
        verifyCodeService.verifyEmailCode(user.getEmail(), emailCode);

        //开始添加用户
        if (user.getPassword().length() <= 6 || user.getPassword().length() >= 18)
            throw new BusinessException("密码必须大于6个字符串小于18个字符");

        if (this.getUserDomain(user.getEmail()) != null)
            throw new BusinessException("用户已存在");

        //是否不需要审核
        int state = StateCodeConstant.USER_NONE;
        if (TextUtils.checkText(user.getName())) {
            state = StateCodeConstant.USER_WAIT;
        }
        user.setState(state);

        //密码加密
        String salt = VerifyCodeService.generateVerifyCode(5);
        String hashPassword = HashUtils.simpleHash(user.getPassword(), salt);
        user.setSalt(salt);
        user.setPassword(hashPassword);

        //添加用户
        int line = userDao.addUserByEmail(user);

        if (line != 1) {
            throw new BusinessException("数据库操作失败，数据库添加行数为" + line, ResponseEnumConstant.CODE_50002);
        }

        //删除邮箱验证码
        verifyCodeService.removeVerifyCode(user.getEmail());
    }

    //通过微信注册用户
    public void addUserByWeChat(UserDomain user) throws Exception {

        if (getUserByWeChat(user.getWechat()) != null)
            throw new BusinessException("微信用户已存在");


        int line = userDao.addUserByWeChat(user);

        if (line != 1) {
            throw new BusinessException("数据库操作失败，数据库添加行数为" + line, ResponseEnumConstant.CODE_50002);
        }
    }

    //是否通过微信注册过
    private boolean isFindUserByWeChat(String openId) throws Exception {
        UserDomain user = getUserByWeChat(openId);

        return user != null;
    }

    public boolean isFindUserByWeChatCode(String code) throws Exception {
        Map wechat = getWeChat(code);
        String openid = (String) wechat.get("openid");

        return isFindUserByWeChat(openid);
    }

    //通过微信Code注册用户
    public void addUserByWeChatCode(UserDomain user, String code) throws Exception {

        //获取微信openid
        Map wechat = getWeChat(code);
        String openid = (String) wechat.get("openid");
        user.setWechat(openid);

        //是否不需要审核
        int state = StateCodeConstant.USER_NONE;
        if (TextUtils.checkText(user.getName())) {
            state = StateCodeConstant.USER_WAIT;
        }
        user.setState(state);

        addUserByWeChat(user);
    }


    //绑定新邮箱
    public void updateBindEmailCode(String email, String emailCode) throws Exception {

        UserDomain user = getUserBySubject();

        //验证邮箱验证码
        verifyCodeService.verifyEmailCode(email, emailCode);

        UserDomain emailUser = getUserDomain(email);
        if (emailUser != null)
            throw new BusinessException("邮箱已被绑定");

        userDao.updateBindEmail(user.getId(), email);
    }


    //绑定新微信
    public void updateBindWeChatByCode(String code) throws Exception {
        UserDomain user = getUserBySubject();

        Map weChat = getWeChat(code);
        String openid = (String) weChat.get("openid");
        userDao.updateBindWeChat(user.getId(), openid);
    }

    //通过帖子获取用户公开信息
    public UserInfoPublicDto getUserInfoPublicByTable(int tableId) throws Exception {

        UserDomain user = getUserBySubject();//验证用户登录状态

        return userDao.getUserInfoPublicByTable(tableId, user.getId());
    }

    //通过评论获取用户公开信息
    public UserInfoPublicDto getUserInfoPublicByComment(int commentId) throws Exception {

        getUserBySubject(); //验证用户登录状态

        return userDao.getUserInfoPublicByComment(commentId);
    }

    //获取旋转验证码
    public String getRotationVerifyCode(String email) throws Exception {

        return verifyCodeService.getRotationVerifyCode(email);
    }

    //获取邮箱验证码
    public void getEmailVerifyCode(String email, int angle) throws Exception {
        verifyCodeService.getEmailVerifyCode(email, angle);
    }

    //通过邮箱验证码修改密码
    public void updatePasswordByEmailCode(String email, String password, String emailCode) throws Exception {
        if (password.length() <= 6 || password.length() >= 18)
            throw new BusinessException("密码必须大于6个字符串小于18个字符");

        //验证邮箱验证码
        verifyCodeService.verifyEmailCode(email, emailCode);

        //密码加密
        String salt = VerifyCodeService.generateVerifyCode(5);
        String hashPassword = HashUtils.simpleHash(password, salt);

        userDao.updatePasswordByEmail(email, hashPassword, salt);
    }

    //修改头像
    public void updateAvatar(String avatar) throws Exception {
        if (!Tools.avatars.contains(avatar))
            throw new BusinessException("头像不存在");

        UserDomain user = getUserBySubject();

        userDao.updateAvatar(user.getId(), avatar);
    }
}
