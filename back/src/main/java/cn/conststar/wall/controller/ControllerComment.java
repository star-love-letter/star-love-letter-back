package cn.conststar.wall.controller;

import cn.conststar.wall.exception.ExceptionMain;
import cn.conststar.wall.pojo.PojoComment;
import cn.conststar.wall.service.ServiceComment;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class ControllerComment {
    private Logger logger = Logger.getLogger(ControllerComment.class);

    @Autowired
    @Qualifier("serviceComment")
    private ServiceComment serviceComment;

    //获取帖子分页评论列表
    @GetMapping("/pageList")
    public String getPageList(@RequestParam("pId") int pId,
                              @RequestParam("pageIndex") int pageIndex,
                              @RequestParam("pageSize") int pageSize) {

        JSONObject jsonObject = new JSONObject();
        try {
            List<PojoComment> comments = serviceComment.getCommentsPage(pId, pageIndex, pageSize);

            JSONArray listJson = (JSONArray) JSON.toJSON(comments);
            jsonObject.put("list", listJson);
            jsonObject.put("msg", "获取成功");
            jsonObject.put("code", 0);
        } catch (ExceptionMain ex) {
            jsonObject.put("msg", ex.getMessage());
            jsonObject.put("Exception", ex.toString());
            jsonObject.put("code", ex.getCode());
        } catch (Exception ex) {
            jsonObject.put("msg", ex.getMessage());
            jsonObject.put("Exception", ex.toString());
            jsonObject.put("code", -1);
        } finally {
            return jsonObject.toJSONString();
        }
    }

    //获取帖子评论总数
    @GetMapping("/count")
    public String getCount(@RequestParam("pId") int pId) {
        JSONObject jsonObject = new JSONObject();
        try {
            int count = serviceComment.getCount(pId);

            jsonObject.put("count", count);
            jsonObject.put("code", 0);
            jsonObject.put("msg", "获取成功");
        } catch (ExceptionMain ex) {
            jsonObject.put("msg", ex.getMessage());
            jsonObject.put("Exception", ex.toString());
            jsonObject.put("code", ex.getCode());
        } catch (Exception ex) {
            jsonObject.put("msg", ex.getMessage());
            jsonObject.put("Exception", ex.toString());
            jsonObject.put("code", -1);
        } finally {
            return jsonObject.toJSONString();
        }
    }

    //发布评论
    @PostMapping("/add")
    public String post(@RequestParam("pId") int pId,
                       @RequestParam("name") String name,
                       @RequestParam("content") String content) {
        JSONObject jsonObject = new JSONObject();
        try {
            serviceComment.addComment(pId, name, content);

            jsonObject.put("code", 0);
            jsonObject.put("msg", "发布成功");

        } catch (ExceptionMain ex) {
            jsonObject.put("msg", ex.getMessage());
            jsonObject.put("Exception", ex.toString());
            jsonObject.put("code", ex.getCode());
        } catch (Exception ex) {
            jsonObject.put("msg", ex.getMessage());
            jsonObject.put("Exception", ex.toString());
            jsonObject.put("code", -1);
        } finally {
            return jsonObject.toJSONString();
        }
    }
}
