package cn.conststar.wall.controller;

import cn.conststar.wall.pojo.PojoTable;
import cn.conststar.wall.pojo.PojoUser;
import cn.conststar.wall.pojo.PojoVerifyCode;
import cn.conststar.wall.response.ResponseCodeEnums;
import cn.conststar.wall.response.ResponseFormat;
import cn.conststar.wall.response.ResponseGeneric;
import cn.conststar.wall.service.ServiceTable;
import cn.conststar.wall.service.ServiceUser;
import cn.conststar.wall.utils.UtilsText;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/api/table", produces = {"application/json;charset=UTF-8"})
@Api(tags = "帖子内容")
public class ControllerTable {
    private Logger logger = Logger.getLogger(ControllerTable.class);

    @Autowired
    @Qualifier("serviceTable")
    private ServiceTable serviceTable;

    @Autowired
    @Qualifier("serviceUser")
    private ServiceUser serviceUser;


    @GetMapping("/pageList")
    @ApiOperation(value = "获取帖子分页列表", notes = "获取帖子分页列表，返回帖子列表")
    public ResponseGeneric<List<PojoTable>> getPageList(
            @ApiParam("页索引") @RequestParam("pageIndex") int pageIndex,
            @ApiParam("页大小") @RequestParam("pageSize") int pageSize,
            @ApiParam("token") @RequestHeader(value = "token", required = false) String token) throws Exception {

        int userId = serviceUser.getUserId(token);
        List<PojoTable> tables = serviceTable.getTablesPage(pageIndex, pageSize, userId);

        return ResponseFormat.retParam(ResponseCodeEnums.CODE_200, tables);
    }

    @GetMapping("/count")
    @ApiOperation(value = "获取帖子总数", notes = "获取帖子总数，返回帖子总数，用来计算页数")
    public ResponseGeneric<Integer> getCount(
            @ApiParam("token") @RequestHeader(value = "token", required = false) String token) throws Exception {

        int userId = serviceUser.getUserId(token);
        int count = serviceTable.getCount(userId);

        return ResponseFormat.retParam(ResponseCodeEnums.CODE_200, count);
    }

    @GetMapping("/table")
    @ApiOperation(value = "获取单个帖子内容", notes = "获取单个帖子内容，返回单个帖子")
    public ResponseGeneric<PojoTable> getTable(
            @ApiParam("帖子id") @RequestParam("id") int id,
            @ApiParam("token") @RequestHeader(value = "token", required = false) String token) throws Exception {

        int userId = serviceUser.getUserId(token);
        PojoTable table = serviceTable.getTable(id, userId);

        return ResponseFormat.retParam(ResponseCodeEnums.CODE_200, table);
    }

    @GetMapping("/searchList")
    @ApiOperation(value = "搜索帖子", notes = "搜索帖子，返回帖子列表")
    public ResponseGeneric<List<PojoTable>> getSearchTables(
            @ApiParam("关键词") @RequestParam("keyword") String keyword,
            @ApiParam("页索引") @RequestParam("pageIndex") int pageIndex,
            @ApiParam("页大小") @RequestParam("pageSize") int pageSize,
            @ApiParam("token") @RequestHeader(value = "token", required = false) String token) throws Exception {

        int userId = serviceUser.getUserId(token);
        List<PojoTable> tables = serviceTable.getSearchTablesPage(keyword, pageIndex, pageSize, userId);

        return ResponseFormat.retParam(ResponseCodeEnums.CODE_200, tables, "搜索成功");
    }

    @GetMapping("/searchCount")
    @ApiOperation(value = "获取搜索帖子总数", notes = "获取搜索帖子总数，返回帖子数量")
    public ResponseGeneric<Integer> getCount(
            @ApiParam("关键词") @RequestParam("keyword") String keyword,
            @ApiParam("token") @RequestHeader(value = "token", required = false) String toekn) throws Exception {

        int userId = serviceUser.getUserId(toekn);
        int count = serviceTable.getSearchCount(keyword, userId);

        return ResponseFormat.retParam(ResponseCodeEnums.CODE_200, count);
    }

    @PostMapping("/add")
    @ApiOperation(value = "发布表白", notes = "发布表白，不返回内容")
    public ResponseGeneric<Object> add(
            @ApiParam("表白者名称") @RequestParam("sender") String sender,
            @ApiParam("表白者性别") @RequestParam("senderSex") int senderSex,
            @ApiParam("被表白者名称") @RequestParam("recipient") String recipient,
            @ApiParam("被表白者性别") @RequestParam("recipientSex") int recipientSex,
            @ApiParam("是否匿名") @RequestParam("anonymous") boolean anonymous,
            @ApiParam("表白内容") @RequestParam("content") String content,
            @ApiParam("图片列表") @RequestParam("images") String images,
            @ApiParam("是否邮箱通知") @RequestParam("notifyEmail") boolean notifyEmail,
            @ApiParam("token") @RequestHeader(value = "token", required = false) String token) throws Exception {

        PojoUser user = serviceUser.getUser(token); //验证用户登录状态

        //是否不需要审核
        int status = 0;

        ObjectMapper mapper = new ObjectMapper();
        List<String> imageList = Arrays.asList(mapper.readValue(images, String[].class));
        if (!imageList.isEmpty()
                || UtilsText.checkText(sender) || UtilsText.checkText(recipient)
                || UtilsText.checkText(content)) {
            status = 1;
        }

        serviceTable.addTable(user.getId(), anonymous, sender, senderSex, recipient, recipientSex, content, images, notifyEmail, status);

        if (status == 1)
            return ResponseFormat.retParam(ResponseCodeEnums.CODE_200, null, "发布成功，等待审核中");

        return ResponseFormat.retParam(ResponseCodeEnums.CODE_200, null, "发布成功");
    }

    @PutMapping("/support")
    @ApiOperation(value = "点赞", notes = "点赞，不返回内容")
    public ResponseGeneric<Object> putSupport(
            @ApiParam("帖子id") @RequestParam("tableId") int tableId,
            @ApiParam("token") @RequestHeader(value = "token", required = false) String token) throws Exception {

        PojoUser user = serviceUser.getUser(token); //验证用户登录状态
        serviceTable.addSupport(tableId, user.getId());

        return ResponseFormat.retParam(ResponseCodeEnums.CODE_200, null, "点赞成功");
    }

    @DeleteMapping("/support")
    @ApiOperation(value = "取消点赞", notes = "取消点赞，不返回内容")
    public ResponseGeneric<Object> deleteSupport(
            @ApiParam("帖子id") @RequestParam("tableId") int tableId,
            @ApiParam("token") @RequestHeader(value = "token", required = false) String token) throws Exception {

        PojoUser user = serviceUser.getUser(token); //验证用户登录状态
        serviceTable.removeSupport(tableId, user.getId());

        return ResponseFormat.retParam(ResponseCodeEnums.CODE_200, null, "已取消点赞");
    }
}

