package cn.conststar.wall.controller;

import cn.conststar.wall.exception.BusinessException;
import cn.conststar.wall.pojo.dto.UserInfoPublicDto;
import cn.conststar.wall.pojo.dto.UserInfoDto;
import cn.conststar.wall.constant.ResponseEnumConstant;
import cn.conststar.wall.handler.FormatHandler;
import cn.conststar.wall.handler.GenericHandler;
import cn.conststar.wall.pojo.model.UserDomain;
import cn.conststar.wall.service.user.UserService;
import cn.conststar.wall.utils.Tools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/user")
@Api(tags = "账号操作")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @ApiOperation(value = "登录", notes = "登录用户，返回用户信息")
    public GenericHandler<UserInfoDto> login(
            @ApiParam("用户id或者邮箱") @RequestParam("userStr") String userStr,
            @ApiParam("密码") @RequestParam("password") String password) throws Exception {


        UserInfoDto login = userService.login(userStr, password);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, login, "登录成功");
    }

    @PostMapping("/logout")
    @ApiOperation(value = "退出登录", notes = "退出登录，不返回内容")
    public GenericHandler<Object> logout() throws Exception {

        userService.logout();

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, null, "已退出登录");
    }


    @PostMapping("/loginByWeChat")
    @ApiOperation(value = "微信登录", notes = "微信登录，返回用户信息")
    public GenericHandler<Object> loginByWeChat(
            @ApiParam("临时登录凭证")
            @RequestParam("code") String code) throws Exception {

        UserInfoDto userInfo = userService.loginByWeChatCode(code);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, userInfo, "登录成功");
    }


    @PostMapping("/addByEmail")
    @ApiOperation(value = "通过邮箱注册", notes = "通过邮箱注册账号，不返回内容")
    public GenericHandler<Object> add(
            @ApiParam("邮箱") @RequestParam("email") String email,
            @ApiParam("密码") @RequestParam("password") String password,
            @ApiParam("名称") @RequestParam("name") String name,
            @ApiParam("邮箱验证码") @RequestParam("emailCode") String emailCode) throws Exception {

        UserDomain user = new UserDomain();
        user.setEmail(email);
        user.setPassword(password);
        user.setName(name);

        userService.addUserByEmailCode(user, emailCode);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, null, "注册成功");
    }

    @PostMapping("/addByWeChat")
    @ApiOperation(value = "通过微信注册", notes = "通过微信注册账号，不返回内容")
    public GenericHandler<Object> addByWeChat(
            @ApiParam("临时登录凭证") @RequestParam("code") String code,
            @ApiParam("名称") @RequestParam("name") String name) throws Exception {

        UserDomain user = new UserDomain();
        user.setName(name);

        userService.addUserByWeChatCode(user, code);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, null, "注册成功");
    }

    @GetMapping("/user")
    @ApiOperation(value = "获取登录用户信息", notes = "获取登录用户信息，返回用户公开信息")
    public GenericHandler<UserInfoDto> getUser() throws Exception {

        UserInfoDto user = userService.getUserInfoBySubject();

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, user);
    }

    @GetMapping("/rotateCode")
    @ApiOperation(value = "获取旋转验证码", notes = "获取旋转验证码，返回图片base64")
    public GenericHandler<String> getRotateCode(
            @ApiParam("邮箱") @RequestParam("email") String email) throws Exception {

        String rotateCode = userService.getRotationVerifyCode(email);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, rotateCode);
    }


    @GetMapping("/emailCode")
    @ApiOperation(value = "获取邮箱验证码", notes = "获取邮箱验证码，不返回内容")
    public GenericHandler<Object> getEmailCode(
            @ApiParam("邮箱") @RequestParam("email") String email,
            @ApiParam("旋转验证码的角度") @RequestParam("angle") int angle) throws Exception {

        userService.getEmailVerifyCode(email, angle);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, null, "验证码发送成功");
    }

    @GetMapping("/isAddedByWeChat")
    @ApiOperation(value = "是否通过微信注册过", notes = "是否通过微信注册过，返回true/false")
    public GenericHandler<Boolean> isAddedByWeChat(
            @ApiParam("临时登录凭证") @RequestParam("code") String code) throws Exception {

        Boolean data = userService.isFindUserByWeChatCode(code);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, data);
    }

    @PutMapping("/bindEmail")
    @ApiOperation(value = "绑定邮箱", notes = "绑定新的邮箱，不返回内容")
    public GenericHandler<Object> bindEmail(
            @ApiParam("邮箱") @RequestParam("email") String email,
            @ApiParam("邮箱验证码") @RequestParam("emailCode") String emailCode) throws Exception {

        userService.updateBindEmailCode(email, emailCode);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, null, "绑定成功");
    }

    @PutMapping("/bindWeChatByCode")
    @ApiOperation(value = "绑定微信", notes = "绑定新的微信，不返回内容")
    public GenericHandler<Object> bindWeChatByCode(
            @ApiParam("临时登录凭证") @RequestParam("code") String code) throws Exception {

        userService.updateBindWeChatByCode(code);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, null, "绑定成功");
    }


    @GetMapping("/userInfoPublic")
    @ApiOperation(value = "获取用户公开信息", notes = "获取用户公开信息，返回用户公开信息")
    public GenericHandler<UserInfoPublicDto> getUserInfoPublic(@ApiParam("用户id") @RequestParam("id") int id) throws Exception {

        UserInfoPublicDto userPublic = userService.getUserInfoPublic(id);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, userPublic);
    }

    @GetMapping("/userInfoPublicByTable")
    @ApiOperation(value = "通过帖子获取用户公开信息", notes = "通过帖子获取用户公开信息，返回用户公开信息")
    public GenericHandler<UserInfoPublicDto> getUserInfoPublicByTable(@ApiParam("帖子id") @RequestParam("tableId") int tableId) throws Exception {

        UserInfoPublicDto userPublic = userService.getUserInfoPublicByTable(tableId);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, userPublic);
    }

    @GetMapping("/userInfoPublicByComment")
    @ApiOperation(value = "通过评论获取用户公开信息", notes = "通过评论获取用户公开信息，返回用户公开信息")
    public GenericHandler<UserInfoPublicDto> getUserInfoPublicByComment(@ApiParam("评论id") @RequestParam("commentId") int commentId) throws Exception {

        UserInfoPublicDto userPublic = userService.getUserInfoPublicByComment(commentId);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, userPublic);
    }

    @PutMapping("/updatePasswordByEmailCode")
    @ApiOperation(value = "通过邮箱验证码修改密码", notes = "通过邮箱验证码修改密码，不返回内容")
    public GenericHandler<Object> updatePasswordByEmailCode(
            @ApiParam("邮箱") @RequestParam("email") String email,
            @ApiParam("新密码") @RequestParam("password") String password,
            @ApiParam("邮箱验证码") @RequestParam("emailCode") String emailCode) throws Exception {

        userService.updatePasswordByEmailCode(email, password, emailCode);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, null, "修改成功");
    }

    @GetMapping("/getAvatars")
    @ApiOperation("获取头像列表")
    public GenericHandler<Set<String>> getAvatars() throws Exception {

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, Tools.avatars);
    }

    @PutMapping("/updateAvatar")
    @ApiOperation("修改头像")
    public GenericHandler<Object> updateAvatar(@ApiParam("新头像") @RequestParam("avatar") String avatar) throws Exception {

        userService.updateAvatar(avatar);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, null, "修改成功");
    }

    //无权限访问
    @RequestMapping("/noAuth")
    @ApiOperation(value = "无权限访问", hidden = true)
    public String noAuth() {

        Subject subject = SecurityUtils.getSubject();

        //已登录账号
        if (subject.isAuthenticated()) {
            throw new BusinessException(ResponseEnumConstant.CODE_70001);
        }

        throw new BusinessException(ResponseEnumConstant.CODE_20001);
    }
}
