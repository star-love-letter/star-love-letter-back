package cn.conststar.wall.controller;

import cn.conststar.wall.pojo.PojoUserPublic;
import cn.conststar.wall.pojo.PojoUser;
import cn.conststar.wall.response.ResponseCodeEnums;
import cn.conststar.wall.response.ResponseFormat;
import cn.conststar.wall.response.ResponseGeneric;
import cn.conststar.wall.service.ServiceUser;
import cn.conststar.wall.utils.UtilsMain;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@Api(tags = "账号操作")
@RequestMapping(value = "/api/user", produces = {"application/json;charset=UTF-8"})
public class ControllerUser {

    @Autowired
    @Qualifier("serviceUser")
    private ServiceUser serviceUser;

    @ApiOperation(value = "登录", notes = "登录用户，返回token")
    @PostMapping("/login")
    public ResponseGeneric<String> login(
            @ApiParam("邮箱") @RequestParam("email") String email,
            @ApiParam("密码") @RequestParam("password") String password) throws Exception {

        String token = serviceUser.login(email, password);

        return ResponseFormat.retParam(ResponseCodeEnums.CODE_200, token, "登录成功");
    }

    @ApiOperation(value = "退出登录", notes = "退出登录，不返回内容")
    @PostMapping("/logout")
    public ResponseGeneric<Object> quit(
            @ApiParam("token") @RequestHeader(value = "token", required = false) String token) throws Exception {

        serviceUser.logout(token);

        return ResponseFormat.retParam(ResponseCodeEnums.CODE_200, null, "已退出登录");
    }

    @ApiOperation(value = "注册", notes = "注册账号，不返回内容")
    @PostMapping("/add")
    public ResponseGeneric<Object> add(
            @ApiParam("邮箱") @RequestParam("email") String email,
            @ApiParam("密码") @RequestParam("password") String password,
            @ApiParam("姓名") @RequestParam("name") String name,
            @ApiParam("图片验证码") @RequestParam("imageCode") String imageCode,
            @ApiParam("邮箱验证") @RequestParam("emailCode") String emailCode) throws Exception {

        //是否不需要审核
        int status = 0;
        if (UtilsMain.checkText(name)) {
            status = 1;
        }

        serviceUser.isVerifyImage(imageCode, email);
        serviceUser.isVerifyEmail(email, emailCode);
        serviceUser.addUser(email, password, name, status);
        serviceUser.removePojoVerifyCode(email);

        if (status == 1)
            return ResponseFormat.retParam(ResponseCodeEnums.CODE_200, null, "注册成功，等待审核");


        return ResponseFormat.retParam(ResponseCodeEnums.CODE_200, null, "注册成功");
    }

    @ApiOperation(value = "获取登录用户信息", notes = "获取登录用户信息，返回用户公开信息")
    @GetMapping("/user")
    public ResponseGeneric<PojoUser> getUser(
            @ApiParam("token") @RequestHeader(value = "token", required = false) String token) throws Exception {

        PojoUser user = serviceUser.getUser(token);

        return ResponseFormat.retParam(ResponseCodeEnums.CODE_200, user);
    }

    @ApiOperation(value = "获取图片验证码", notes = "获取图片验证码，返回图片base64\n\n" +
            "验证过的图片验证码需要重新获取\n\n" +
            "建议：在输入邮箱后、获取邮箱验证码后、点击注册后 都要重新获取一下图片验证码")
    @GetMapping("/verifyImage")
    public ResponseGeneric<String> getVerifyImage(
            @ApiParam("邮箱") @RequestParam("email") String email) throws Exception {

        String verifyImage = serviceUser.getVerifyImage(email);

        return ResponseFormat.retParam(ResponseCodeEnums.CODE_200, verifyImage);
    }


    @ApiOperation(value = "获取邮箱验证码", notes = "获取邮箱验证码，不返回内容")
    @GetMapping("/verifyEmail")
    public ResponseGeneric<Object> getVerifyEmail(
            @ApiParam("邮箱") @RequestParam("email") String email,
            @ApiParam("图形验证码") @RequestParam("imageCode") String imageCode) throws Exception {

        serviceUser.getVerifyEmail(email, imageCode);

        return ResponseFormat.retParam(ResponseCodeEnums.CODE_200, null, "验证码发送成功");
    }

    @ApiOperation(value = "获取用户公开信息", notes = "获取用户公开信息，返回用户公开信息")
    @GetMapping("/userPublic")
    public ResponseGeneric<PojoUserPublic> getPublicUser(
            @ApiParam("用户id") @RequestParam("id") int id,
            @ApiParam("token") @RequestHeader(value = "token", required = false) String token) throws Exception {

        PojoUser user = serviceUser.getUser(token); //验证用户登录状态
        PojoUserPublic userPublic = serviceUser.getUserPublic(id);

        return ResponseFormat.retParam(ResponseCodeEnums.CODE_200, userPublic);
    }

}
