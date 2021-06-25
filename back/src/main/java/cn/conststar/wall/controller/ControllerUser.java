package cn.conststar.wall.controller;

import cn.conststar.wall.exception.ExceptionMain;
import cn.conststar.wall.pojo.PojoUser;
import cn.conststar.wall.service.ServiceUser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/user")
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
                      @RequestParam("name") String name) throws Exception {
        JSONObject jsonObject = new JSONObject();

        serviceUser.addUser(email, password, name);

        jsonObject.put("code", 0);
        jsonObject.put("msg", "注册成功");

        return jsonObject.toJSONString();
    }

    //获取用户信息
    @GetMapping("/user")
    public String getUser(HttpSession session) {
        JSONObject jsonObject = new JSONObject();

        PojoUser user = (PojoUser)session.getAttribute("user");

        jsonObject.put("user", user);
        jsonObject.put("code", 0);
        jsonObject.put("msg", "获取成功");

        return jsonObject.toJSONString();
    }

}
