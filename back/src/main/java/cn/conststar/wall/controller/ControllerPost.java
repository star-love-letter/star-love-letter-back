package cn.conststar.wall.controller;

import cn.conststar.wall.exception.ExceptionMain;
import cn.conststar.wall.pojo.PojoPost;
import cn.conststar.wall.service.ServicePost;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/post")
public class ControllerPost {
    private Logger logger = Logger.getLogger(ControllerPost.class);

    @Autowired
    @Qualifier("servicePost")
    private ServicePost servicePost;

    //获取分页帖子列表
    @GetMapping("/pageList")
    public String getPageList(@RequestParam("pageIndex") int pageIndex,
                              @RequestParam("pageSize") int pageSize,
                              HttpServletRequest request, HttpServletResponse response) {

        JSONObject jsonObject = new JSONObject();
        try {
            List<PojoPost> posts = servicePost.getPostsPage(pageIndex, pageSize);

            JSONArray listJson = (JSONArray) JSON.toJSON(posts);
            jsonObject.put("list", listJson);
            jsonObject.put("msg", "获取成功");
            jsonObject.put("code", 0);
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

    //获取帖子总数
    @GetMapping("/count")
    public String getCount() {
        JSONObject jsonObject = new JSONObject();
        try {
            int count = servicePost.getCount();

            jsonObject.put("count", count);
            jsonObject.put("msg", "获取成功");
            jsonObject.put("code", 0);
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

    //获取单个帖子内容
    @GetMapping("/post")
    public String getPost(@RequestParam("id") int id) {
        JSONObject jsonObject = new JSONObject();
        try {
            PojoPost post = servicePost.getPost(id);

            JSONObject postJson = (JSONObject) JSON.toJSON(post);
            jsonObject.put("post", postJson);
            jsonObject.put("msg", "获取成功");
            jsonObject.put("code", 0);
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

    //搜索帖子
    @GetMapping("/searchList")
    public String getSearchPosts(@RequestParam("keyword") String keyword,
                                 @RequestParam("pageIndex") int pageIndex,
                                 @RequestParam("pageSize") int pageSize) {
        JSONObject jsonObject = new JSONObject();
        try {
            List<PojoPost> posts = servicePost.getSearchPostsPage(keyword, pageIndex, pageSize);

            JSONArray listJson = (JSONArray) JSON.toJSON(posts);
            jsonObject.put("list", listJson);
            jsonObject.put("msg", "获取成功");
            jsonObject.put("code", 0);

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

    //获取搜索帖子总数
    @GetMapping("/searchCount")
    public String getCount(@RequestParam("keyword") String keyword) throws ExceptionMain {
        JSONObject jsonObject = new JSONObject();
        try {
            int count = servicePost.getSearchCount(keyword);

            jsonObject.put("count", count);
            jsonObject.put("code", 0);
            jsonObject.put("msg", "获取成功");
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

    //发布表白
    @PostMapping("/add")
    public String post(@RequestParam("sender") String sender,
                       @RequestParam("senderSex") int senderSex,
                       @RequestParam("recipient") String recipient,
                       @RequestParam("recipientSex") int recipientSex,
                       @RequestParam("content") String content) {
        JSONObject jsonObject = new JSONObject();
        try {
            servicePost.addPost(sender, senderSex, recipient, recipientSex, content);

            jsonObject.put("code", 0);
            jsonObject.put("msg", "发布成功");
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

    //点赞
    @PutMapping("/thumbsUp")
    public String putThumbsUp(@RequestParam("id") int id,
                              @RequestParam("thumbsUp") boolean thumbsUp) {
        JSONObject jsonObject = new JSONObject();
        try {
            servicePost.thumbsUp(id, thumbsUp);

            jsonObject.put("code", 0);
            if (thumbsUp)
                jsonObject.put("msg", "已点赞");
            else
                jsonObject.put("msg", "取消点赞");
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
}

