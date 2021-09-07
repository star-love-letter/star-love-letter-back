package cn.conststar.wall.controller;

import cn.conststar.wall.pojo.PojoTable;
import cn.conststar.wall.pojo.PojoUser;
import cn.conststar.wall.service.ServiceTable;
import cn.conststar.wall.service.ServiceUser;
import cn.conststar.wall.utils.UtilsMain;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/table", produces = {"application/json;charset=UTF-8"})
public class ControllerTable {
    private Logger logger = Logger.getLogger(ControllerTable.class);

    @Autowired
    @Qualifier("serviceTable")
    private ServiceTable serviceTable;

    @Autowired
    @Qualifier("serviceUser")
    private ServiceUser serviceUser;


    //获取帖子分页列表
    @GetMapping("/pageList")
    public String getPageList(@RequestParam("pageIndex") int pageIndex,
                              @RequestParam("pageSize") int pageSize,
                              @RequestHeader(value = "token", required = false) String token) throws Exception {

        JSONObject jsonObject = new JSONObject();

        int userId = serviceUser.getUserId(token);
        List<PojoTable> tables = serviceTable.getTablesPage(pageIndex, pageSize, userId);

        JSONArray listJson = (JSONArray) JSON.toJSON(tables);
        jsonObject.put("list", listJson);
        jsonObject.put("msg", "获取成功");
        jsonObject.put("code", 0);

        return jsonObject.toJSONString();
    }

    //获取帖子总数
    @GetMapping("/count")
    public String getCount() throws Exception {
        JSONObject jsonObject = new JSONObject();

        int count = serviceTable.getCount();

        jsonObject.put("count", count);
        jsonObject.put("msg", "获取成功");
        jsonObject.put("code", 0);

        return jsonObject.toJSONString();
    }

    //获取单个帖子内容
    @GetMapping("/table")
    public String getTable(@RequestParam("id") int id,
                           @RequestHeader(value = "token", required = false) String token) throws Exception {
        JSONObject jsonObject = new JSONObject();

        int userId = serviceUser.getUserId(token);
        PojoTable table = serviceTable.getTable(id, userId);

        JSONObject tableJson = (JSONObject) JSON.toJSON(table);
        jsonObject.put("table", tableJson);
        jsonObject.put("msg", "获取成功");
        jsonObject.put("code", 0);

        return jsonObject.toJSONString();
    }

    //搜索帖子
    @GetMapping("/searchList")
    public String getSearchTables(@RequestParam("keyword") String keyword,
                                  @RequestParam("pageIndex") int pageIndex,
                                  @RequestParam("pageSize") int pageSize,
                                  @RequestHeader(value = "token", required = false) String token) throws Exception {
        JSONObject jsonObject = new JSONObject();

        int userId = serviceUser.getUserId(token);
        List<PojoTable> tables = serviceTable.getSearchTablesPage(keyword, pageIndex, pageSize, userId);

        JSONArray listJson = (JSONArray) JSON.toJSON(tables);
        jsonObject.put("list", listJson);
        jsonObject.put("msg", "获取成功");
        jsonObject.put("code", 0);


        return jsonObject.toJSONString();

    }

    //获取搜索帖子总数
    @GetMapping("/searchCount")
    public String getCount(@RequestParam("keyword") String keyword) throws Exception {
        JSONObject jsonObject = new JSONObject();

        int count = serviceTable.getSearchCount(keyword);

        jsonObject.put("count", count);
        jsonObject.put("msg", "获取成功");
        jsonObject.put("code", 0);

        return jsonObject.toJSONString();
    }

    //发布表白
    @PostMapping("/add")
    public String add(@RequestParam("sender") String sender,
                      @RequestParam("senderSex") int senderSex,
                      @RequestParam("recipient") String recipient,
                      @RequestParam("recipientSex") int recipientSex,
                      @RequestParam("anonymous") boolean anonymous,
                      @RequestParam("content") String content,
                      @RequestParam("images") String images,
                      @RequestHeader(value = "token", required = false) String token) throws Exception {
        JSONObject jsonObject = new JSONObject();

        PojoUser user = serviceUser.getUser(token); //验证用户登录状态

        List<String> imageList = JSONArray.parseArray(images).toJavaList(String.class);
        UtilsMain.addImages(imageList);
        if (imageList.isEmpty())
            images = null;

        serviceTable.addTable(user.getId(), anonymous, sender, senderSex, recipient, recipientSex, content, images);

        jsonObject.put("code", 0);
        jsonObject.put("msg", "发布成功");

        return jsonObject.toJSONString();
    }

    //点赞
    @PutMapping("/support")
    public String putSupport(@RequestParam("tableId") int tableId,
                             @RequestHeader(value = "token", required = false) String token) throws Exception {
        JSONObject jsonObject = new JSONObject();

        PojoUser user = serviceUser.getUser(token); //验证用户登录状态
        serviceTable.addSupport(tableId, user.getId());

        jsonObject.put("msg", "已点赞");
        jsonObject.put("code", 0);


        return jsonObject.toJSONString();
    }

    //取消点赞
    @DeleteMapping("/support")
    public String deleteSupport(@RequestParam("tableId") int tableId,
                                @RequestHeader(value = "token", required = false) String token) throws Exception {
        JSONObject jsonObject = new JSONObject();

        PojoUser user = serviceUser.getUser(token); //验证用户登录状态
        serviceTable.removeSupport(tableId, user.getId());

        jsonObject.put("msg", "已取消点赞");
        jsonObject.put("code", 0);


        return jsonObject.toJSONString();
    }
}

