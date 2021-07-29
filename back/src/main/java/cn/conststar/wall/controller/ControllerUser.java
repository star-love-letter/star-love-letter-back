package cn.conststar.wall.controller;

import cn.conststar.wall.pojo.PojoUserPublic;
import cn.conststar.wall.pojo.PojoUser;
import cn.conststar.wall.service.ServiceUser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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
                        @RequestParam("password") String password,
                        HttpSession session) throws Exception {
        JSONObject jsonObject = new JSONObject();

        PojoUser user = serviceUser.login(email, password);
        session.setAttribute("user", user);

        JSONObject userJson = (JSONObject) JSON.toJSON(user);
        jsonObject.put("user", userJson);
        jsonObject.put("code", 0);
        jsonObject.put("msg", "登录成功");


        return jsonObject.toJSONString();
    }

    //退出登录
    @PostMapping("/quit")
    public String quit(HttpSession session) {
        JSONObject jsonObject = new JSONObject();
        session.removeAttribute("user");

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
                      @RequestParam("emailCode") String emailCode,
                      HttpSession session) throws Exception {
        JSONObject jsonObject = new JSONObject();

        serviceUser.isVerifyImage(imageCode, session);
        serviceUser.isVerifyEmail(email, emailCode, session);
        serviceUser.addUser(email, password, name);
        serviceUser.removePojoVerifyCode(session);

        jsonObject.put("code", 0);
        jsonObject.put("msg", "注册成功");

        return jsonObject.toJSONString();
    }

    //获取用户信息
    @GetMapping("/user")
    public String getUser(HttpSession session) throws Exception {
        JSONObject jsonObject = new JSONObject();
        PojoUser user = (PojoUser) session.getAttribute("user");
        serviceUser.verifyUser(user);

        jsonObject.put("user", user);
        jsonObject.put("code", 0);
        jsonObject.put("msg", "获取成功");

        return jsonObject.toJSONString();
    }

    //获取图形验证码
    @GetMapping("/verifyImage")
    public String getVerifyImage(HttpSession session) throws IOException {
        JSONObject jsonObject = new JSONObject();

        String verifyImage = serviceUser.getVerifyImage(session);

        jsonObject.put("image", verifyImage);
        jsonObject.put("code", 0);
        jsonObject.put("msg", "获取成功");

        return jsonObject.toJSONString();
    }

    //获取短信验证码
    @GetMapping("/verifyEmail")
    public String getVerifyEmail(@RequestParam("email") String email,
                                 HttpSession session) throws Exception {
        JSONObject jsonObject = new JSONObject();

        serviceUser.getVerifyEmail(email, session);

        jsonObject.put("code", 0);
        jsonObject.put("msg", "获取成功");
        return jsonObject.toJSONString();
    }

    //获取公开用户信息
    @GetMapping("/userPublic")
    public String getPublicUser(@RequestParam("id") int id,
                                HttpSession session) throws Exception {
        JSONObject jsonObject = new JSONObject();

        PojoUser user = (PojoUser) session.getAttribute("user");
        serviceUser.verifyUser(user);
        PojoUserPublic userPublic = serviceUser.getUserPublic(id);

        jsonObject.put("userPublic", userPublic);
        jsonObject.put("code", 0);
        jsonObject.put("msg", "获取成功");

        return jsonObject.toJSONString();
    }
}
