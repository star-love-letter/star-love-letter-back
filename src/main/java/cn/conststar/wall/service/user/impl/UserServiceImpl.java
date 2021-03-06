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
            throw new BusinessException("??????code2Session??????????????????????????????", ResponseEnumConstant.CODE_60002);

        return data;
    }

    //??????????????????????????? ??????????????????????????????
    public UserDomain getUserBySubject() throws Exception {
        Subject subject = SecurityUtils.getSubject();
        UserDomain userDomain = (UserDomain) subject.getPrincipal();
        if (userDomain == null) {
            throw new BusinessException(ResponseEnumConstant.CODE_20001);
        }

        return userDomain;
    }

    //???????????????????????????
    public UserInfoDto getUserInfoBySubject() throws Exception {
        return new UserInfoDto(getUserBySubject());
    }

    // ?????????????????? userStr?????????id????????????
    public UserDomain getUserDomain(String userStr) throws Exception {
        return userDao.getUserDomain(userStr);
    }

    //????????????????????????
    public UserInfoDto loginByWeChatCode(String code) throws Exception {
        Map data = getWeChat(code);
        String openid = (String) data.get("openid");

        UserDomain user = getUserByWeChat(openid);
        if (user == null) {
            throw new BusinessException("?????????????????????", ResponseEnumConstant.CODE_20001);
        }

        return new UserInfoDto(user);
    }

    //????????????????????????
    public UserInfoPublicDto getUserInfoPublic(int id) throws Exception {
        getUserBySubject(); //????????????????????????

        UserInfoPublicDto user = userDao.getUserInfoPublic(id);
        if (user == null)
            throw new BusinessException("???????????????");
        return user;
    }

    //????????????  ?????? id || ??????
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

        //??????????????????
        userDao.updateLoginTime(userStr);

        return new UserInfoDto(getUserBySubject());
    }


    //????????????
    public void logout() throws Exception {
        Subject user = SecurityUtils.getSubject();
        user.logout();
    }

    //????????????????????????
    private UserDomain getUserByWeChat(String openId) throws Exception {
        return userDao.getUserByWeChat(openId);
    }


    public void addUserByEmailCode(UserDomain user, String emailCode) throws Exception {
        //?????????????????????
        verifyCodeService.verifyEmailCode(user.getEmail(), emailCode);

        //??????????????????
        if (user.getPassword().length() <= 6 || user.getPassword().length() >= 18)
            throw new BusinessException("??????????????????6??????????????????18?????????");

        if (this.getUserDomain(user.getEmail()) != null)
            throw new BusinessException("???????????????");

        //?????????????????????
        int state = StateCodeConstant.USER_NONE;
        if (TextUtils.checkText(user.getName())) {
            state = StateCodeConstant.USER_WAIT;
        }
        user.setState(state);

        //????????????
        String salt = VerifyCodeService.generateVerifyCode(5);
        String hashPassword = HashUtils.simpleHash(user.getPassword(), salt);
        user.setSalt(salt);
        user.setPassword(hashPassword);

        //????????????
        int line = userDao.addUserByEmail(user);

        if (line != 1) {
            throw new BusinessException("????????????????????????????????????????????????" + line, ResponseEnumConstant.CODE_50002);
        }

        //?????????????????????
        verifyCodeService.removeVerifyCode(user.getEmail());
    }

    //????????????????????????
    public void addUserByWeChat(UserDomain user) throws Exception {

        if (getUserByWeChat(user.getWechat()) != null)
            throw new BusinessException("?????????????????????");


        int line = userDao.addUserByWeChat(user);

        if (line != 1) {
            throw new BusinessException("????????????????????????????????????????????????" + line, ResponseEnumConstant.CODE_50002);
        }
    }

    //???????????????????????????
    private boolean isFindUserByWeChat(String openId) throws Exception {
        UserDomain user = getUserByWeChat(openId);

        return user != null;
    }

    public boolean isFindUserByWeChatCode(String code) throws Exception {
        Map wechat = getWeChat(code);
        String openid = (String) wechat.get("openid");

        return isFindUserByWeChat(openid);
    }

    //????????????Code????????????
    public void addUserByWeChatCode(UserDomain user, String code) throws Exception {

        //????????????openid
        Map wechat = getWeChat(code);
        String openid = (String) wechat.get("openid");
        user.setWechat(openid);

        //?????????????????????
        int state = StateCodeConstant.USER_NONE;
        if (TextUtils.checkText(user.getName())) {
            state = StateCodeConstant.USER_WAIT;
        }
        user.setState(state);

        addUserByWeChat(user);
    }


    //???????????????
    public void updateBindEmailCode(String email, String emailCode) throws Exception {

        UserDomain user = getUserBySubject();

        //?????????????????????
        verifyCodeService.verifyEmailCode(email, emailCode);

        UserDomain emailUser = getUserDomain(email);
        if (emailUser != null)
            throw new BusinessException("??????????????????");

        userDao.updateBindEmail(user.getId(), email);
    }


    //???????????????
    public void updateBindWeChatByCode(String code) throws Exception {
        UserDomain user = getUserBySubject();

        Map weChat = getWeChat(code);
        String openid = (String) weChat.get("openid");
        userDao.updateBindWeChat(user.getId(), openid);
    }

    //????????????????????????????????????
    public UserInfoPublicDto getUserInfoPublicByTable(int tableId) throws Exception {

        UserDomain user = getUserBySubject();//????????????????????????

        return userDao.getUserInfoPublicByTable(tableId, user.getId());
    }

    //????????????????????????????????????
    public UserInfoPublicDto getUserInfoPublicByComment(int commentId) throws Exception {

        getUserBySubject(); //????????????????????????

        return userDao.getUserInfoPublicByComment(commentId);
    }

    //?????????????????????
    public String getRotationVerifyCode(String email) throws Exception {

        return verifyCodeService.getRotationVerifyCode(email);
    }

    //?????????????????????
    public void getEmailVerifyCode(String email, int angle) throws Exception {
        verifyCodeService.getEmailVerifyCode(email, angle);
    }

    //?????????????????????????????????
    public void updatePasswordByEmailCode(String email, String password, String emailCode) throws Exception {
        if (password.length() <= 6 || password.length() >= 18)
            throw new BusinessException("??????????????????6??????????????????18?????????");

        //?????????????????????
        verifyCodeService.verifyEmailCode(email, emailCode);

        //????????????
        String salt = VerifyCodeService.generateVerifyCode(5);
        String hashPassword = HashUtils.simpleHash(password, salt);

        userDao.updatePasswordByEmail(email, hashPassword, salt);
    }

    //????????????
    public void updateAvatar(String avatar) throws Exception {
        if (!Tools.avatars.contains(avatar))
            throw new BusinessException("???????????????");

        UserDomain user = getUserBySubject();

        userDao.updateAvatar(user.getId(), avatar);
    }
}
