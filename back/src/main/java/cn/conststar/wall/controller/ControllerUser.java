package cn.conststar.wall.controller;

import cn.conststar.wall.pojo.PojoUserPublic;
import cn.conststar.wall.pojo.PojoUser;
import cn.conststar.wall.service.ServiceUser;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/api/user", produces = {"application/json;charset=UTF-8"})
public class ControllerUser {

    @Autowired
    @Qualifier("serviceUser")
    private ServiceUser serviceUser;

    //登录
    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password) throws Exception {
        JSONObject jsonObject = new JSONObject();

        String token = serviceUser.login(email, password);

        jsonObject.put("token", token);
        jsonObject.put("code", 0);
        jsonObject.put("msg", "登录成功");

        return jsonObject.toJSONString();
    }

    //退出登录
    @PostMapping("/logout")
    public String quit(@RequestHeader(value = "token", required = false) String token) throws Exception {
        JSONObject jsonObject = new JSONObject();

        serviceUser.logout(token);

        jsonObject.put("code", 0);
        jsonObject.put("msg", "账号已退出");
        return jsonObject.toJSONString();
    }

    //注册
    @PostMapping("/add")
    public String add(@RequestParam("email") String email,
                      @RequestParam("password") String password,
                      @RequestParam("name") String name,
                      @RequestParam("imageCode") String imageCode,
                      @RequestParam("emailCode") String emailCode) throws Exception {
        JSONObject jsonObject = new JSONObject();

        serviceUser.isVerifyImage(imageCode, email);
        serviceUser.isVerifyEmail(email, emailCode);
        serviceUser.addUser(email, password, name);
        serviceUser.removePojoVerifyCode(email);

        jsonObject.put("code", 0);
        jsonObject.put("msg", "注册成功");

        return jsonObject.toJSONString();
    }

    //获取用户信息
    @GetMapping("/user")
    public String getUser(@RequestHeader(value = "token", required = false) String token) throws Exception {
        JSONObject jsonObject = new JSONObject();
        PojoUser user = serviceUser.getUser(token);

        jsonObject.put("user", user);
        jsonObject.put("code", 0);
        jsonObject.put("msg", "获取成功");

        return jsonObject.toJSONString();
    }

    //获取图片验证码
    @GetMapping("/verifyImage")
    public String getVerifyImage(@RequestParam("email") String email) throws Exception {
        JSONObject jsonObject = new JSONObject();

        String verifyImage = serviceUser.getVerifyImage(email);

        jsonObject.put("image", verifyImage);
        jsonObject.put("code", 0);
        jsonObject.put("msg", "获取成功");

        return jsonObject.toJSONString();
    }

    //获取邮箱验证码
    @GetMapping("/verifyEmail")
    public String getVerifyEmail(@RequestParam("email") String email,
                                 @RequestParam("imageCode") String imageCode) throws Exception {
        JSONObject jsonObject = new JSONObject();

        serviceUser.getVerifyEmail(email, imageCode);

        jsonObject.put("code", 0);
        jsonObject.put("msg", "获取成功");
        return jsonObject.toJSONString();
    }

    //获取公开用户信息
    @GetMapping("/userPublic")
    public String getPublicUser(@RequestParam("id") int id,
                                @RequestHeader(value = "token", required = false) String token) throws Exception {
        JSONObject jsonObject = new JSONObject();

        PojoUser user = serviceUser.getUser(token); //验证用户登录状态
        PojoUserPublic userPublic = serviceUser.getUserPublic(id);

        jsonObject.put("userPublic", userPublic);
        jsonObject.put("code", 0);
        jsonObject.put("msg", "获取成功");

        return jsonObject.toJSONString();
    }

}
