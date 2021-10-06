package cn.conststar.wall.controller;

import cn.conststar.wall.pojo.PojoUserPublic;
import cn.conststar.wall.pojo.PojoUser;
import cn.conststar.wall.response.ResponseCodeEnums;
import cn.conststar.wall.response.ResponseFormat;
import cn.conststar.wall.response.ResponseGeneric;
import cn.conststar.wall.service.ServiceUser;
import cn.conststar.wall.utils.UtilsText;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/api/user", produces = {"application/json;charset=UTF-8"})
@Api(tags = "账号操作")
public class ControllerUser {

    @Autowired
    @Qualifier("serviceUser")
    private ServiceUser serviceUser;

    @PostMapping("/login")
    @ApiOperation(value = "登录", notes = "登录用户，返回token")
    public ResponseGeneric<String> login(
            @ApiParam("用户id或邮箱") @RequestParam("id") String id,
            @ApiParam("密码") @RequestParam("password") String password) throws Exception {

        String token = serviceUser.loginMakeToken(id, password);

        return ResponseFormat.retParam(ResponseCodeEnums.CODE_200, token, "登录成功");
    }

    @PostMapping("/logout")
    @ApiOperation(value = "退出登录", notes = "退出登录，不返回内容")
    public ResponseGeneric<Object> quit(
            @ApiParam("token") @RequestHeader(value = "token", required = false) String token) throws Exception {

        serviceUser.logout(token);

        return ResponseFormat.retParam(ResponseCodeEnums.CODE_200, null, "已退出登录");
    }


    @PostMapping("/loginByWeChat")
    @ApiOperation(value = "微信登录", notes = "微信登录，返回token")
    public ResponseGeneric<Object> loginByWeChat(
            @ApiParam("临时登录凭证") @RequestParam("code") String code) throws Exception {

        String token = serviceUser.loginByWeChatCode(code);

        return ResponseFormat.retParam(ResponseCodeEnums.CODE_200, token, "登录成功");
    }


    @PostMapping("/addByEmail")
    @ApiOperation(value = "通过邮箱注册", notes = "通过邮箱注册账号，不返回内容")
    public ResponseGeneric<Object> add(
            @ApiParam("邮箱") @RequestParam("email") String email,
            @ApiParam("密码") @RequestParam("password") String password,
            @ApiParam("名称") @RequestParam("name") String name,
            @ApiParam("邮箱验证码") @RequestParam("emailCode") String emailCode) throws Exception {

        //是否不需要审核
        int status = 0;
        if (UtilsText.checkText(name)) {
            status = 1;
        }

        //验证邮箱验证码
        serviceUser.verifyEmailCode(email, emailCode);
        //通过邮箱添加用户
        serviceUser.addUserByEmail(email, password, name, status);
        //删除邮箱验证码
        serviceUser.removePojoVerifyCode(email);

        if (status == 1)
            return ResponseFormat.retParam(ResponseCodeEnums.CODE_200, null, "注册成功，等待审核");

        return ResponseFormat.retParam(ResponseCodeEnums.CODE_200, null, "注册成功");
    }

    @PostMapping("/addByWeChat")
    @ApiOperation(value = "通过微信注册", notes = "通过微信注册账号，不返回内容")
    public ResponseGeneric<Object> addByWeChat(
            @ApiParam("临时登录凭证") @RequestParam("code") String code,
            @ApiParam("密码") @RequestParam("password") String password,
            @ApiParam("名称") @RequestParam("name") String name) throws Exception {

        //是否不需要审核
        int status = 0;
        if (UtilsText.checkText(name)) {
            status = 1;
        }

        serviceUser.addUserByWeChatCode(code, password, name, status);

        if (status == 1)
            return ResponseFormat.retParam(ResponseCodeEnums.CODE_200, null, "注册成功，等待审核");


        return ResponseFormat.retParam(ResponseCodeEnums.CODE_200, null, "注册成功");
    }

    @GetMapping("/user")
    @ApiOperation(value = "获取登录用户信息", notes = "获取登录用户信息，返回用户公开信息")
    public ResponseGeneric<PojoUser> getUser(
            @ApiParam("token") @RequestHeader(value = "token", required = false) String token) throws Exception {

        PojoUser user = serviceUser.getUser(token);

        return ResponseFormat.retParam(ResponseCodeEnums.CODE_200, user);
    }

    @ApiOperation(value = "获取旋转验证码", notes = "获取旋转验证码，返回图片base64")
    @GetMapping("/rotateCode")
    public ResponseGeneric<String> getRotateCode(
            @ApiParam("邮箱") @RequestParam("email") String email) throws Exception {

        String rotateCode = serviceUser.getRotateCode(email);

        return ResponseFormat.retParam(ResponseCodeEnums.CODE_200, rotateCode);
    }


    @GetMapping("/emailCode")
    @ApiOperation(value = "获取邮箱验证码", notes = "获取邮箱验证码，不返回内容")
    public ResponseGeneric<Object> getEmailCode(
            @ApiParam("邮箱") @RequestParam("email") String email,
            @ApiParam("旋转验证码度数") @RequestParam("angle") int angle) throws Exception {

        serviceUser.getEmailCode(email, angle);

        return ResponseFormat.retParam(ResponseCodeEnums.CODE_200, null, "验证码发送成功");
    }


    @GetMapping("/isAddedByWeChat")
    @ApiOperation(value = "是否通过微信注册过", notes = "是否通过微信注册过，返回true/false")
    public ResponseGeneric<Boolean> isAddedByWeChat(
            @ApiParam("临时登录凭证") @RequestParam("code") String code) throws Exception {

        Boolean data = serviceUser.isAddedByWeChatCode(code);

        return ResponseFormat.retParam(ResponseCodeEnums.CODE_200, data);
    }

    @PostMapping("/bindWeChatByCode")
    @ApiOperation(value = "绑定微信", notes = "绑定微信,会覆盖之前绑定的微信，不返回内容")
    public ResponseGeneric<Object> bindWeChatByCode(
            @ApiParam("临时登录凭证") @RequestParam("code") String code,
            @ApiParam("用户id或邮箱") @RequestParam("id") String id,
            @ApiParam("用户密码") @RequestParam("password") String password) throws Exception {

        serviceUser.bindWeChatByCode(code, id, password);

        return ResponseFormat.retParam(ResponseCodeEnums.CODE_200, null, "绑定成功");
    }


    @GetMapping("/userPublic")
    @ApiOperation(value = "获取用户公开信息", notes = "获取用户公开信息，返回用户公开信息")
    public ResponseGeneric<PojoUserPublic> getPublicUser(
            @ApiParam("用户id") @RequestParam("id") int id,
            @ApiParam("token") @RequestHeader(value = "token", required = false) String token) throws Exception {

        PojoUser user = serviceUser.getUser(token); //验证用户登录状态
        PojoUserPublic userPublic = serviceUser.getUserPublic(id);

        return ResponseFormat.retParam(ResponseCodeEnums.CODE_200, userPublic);
    }

}
