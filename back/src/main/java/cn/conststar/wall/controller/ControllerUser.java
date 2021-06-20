package cn.conststar.wall.controller;

import cn.conststar.wall.exception.ExceptionMain;
import cn.conststar.wall.pojo.PojoComment;
import cn.conststar.wall.pojo.PojoPost;
import cn.conststar.wall.pojo.PojoUser;
import cn.conststar.wall.service.ServiceUser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class ControllerUser {

    @Autowired
    @Qualifier("serviceUser")
    private ServiceUser serviceUser;

    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password) {
        JSONObject jsonObject = new JSONObject();
        try {
            PojoUser user = serviceUser.login(email, password);

            JSONObject userJson = (JSONObject) JSON.toJSON(user);
            jsonObject.put("user", userJson);
            jsonObject.put("code", 0);
            jsonObject.put("msg", "登录成功");
        }  catch (ExceptionMain ex) {
            jsonObject.put("msg", ex.getMessage());
            jsonObject.put("Exception", ex.toString());
            jsonObject.put("code", ex.getCode());
        }  catch (Exception ex) {
            jsonObject.put("msg", ex.getMessage());
            jsonObject.put("Exception", ex.toString());
            jsonObject.put("code", -1);
        } finally {
            return jsonObject.toJSONString();
        }
    }

    @PostMapping("/add")
    public String add(@RequestParam("email") String email,
                      @RequestParam("password") String password,
                      @RequestParam("name") String name) {
        JSONObject jsonObject = new JSONObject();
        try {
            serviceUser.addUser(email, password, name);

            jsonObject.put("code", 0);
            jsonObject.put("msg", "注册成功");

        } catch (ExceptionMain ex) {
            jsonObject.put("msg", ex.getMessage());
            jsonObject.put("Exception", ex.toString());
            jsonObject.put("code", ex.getCode());
        }  catch (Exception ex) {
            jsonObject.put("msg", ex.getMessage());
            jsonObject.put("Exception", ex.toString());
            jsonObject.put("code", -1);
        } finally {
            return jsonObject.toJSONString();
        }
    }


}
