package cn.conststar.wall.controller;

import cn.conststar.wall.pojo.PojoComment;
import cn.conststar.wall.pojo.PojoUser;
import cn.conststar.wall.response.ResponseCodeEnums;
import cn.conststar.wall.response.ResponseFormat;
import cn.conststar.wall.response.ResponseGeneric;
import cn.conststar.wall.service.ServiceComment;
import cn.conststar.wall.service.ServiceUser;
import cn.conststar.wall.utils.UtilsMain;
import com.alibaba.fastjson.JSONArray;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/comment", produces = {"application/json;charset=UTF-8"})
@Api(tags = "评论内容")
public class ControllerComment {
    private Logger logger = Logger.getLogger(ControllerComment.class);

    @Autowired
    @Qualifier("serviceComment")
    private ServiceComment serviceComment;


    @Autowired
    @Qualifier("serviceUser")
    private ServiceUser serviceUser;


    @GetMapping("/pageList")
    @ApiOperation(value = "获取帖子分页评论列表",notes="获取帖子分页评论列表，返回评论列表")
    public ResponseGeneric<List<PojoComment>> getPageList(
            @ApiParam("帖子id") @RequestParam("tableId") int tableId,
            @ApiParam("页索引") @RequestParam("pageIndex") int pageIndex,
            @ApiParam("页大小") @RequestParam("pageSize") int pageSize,
            @ApiParam("token") @RequestHeader(value = "token", required = false) String token) throws Exception {

        int userId = serviceUser.getUserId(token);
        List<PojoComment> comments = serviceComment.getCommentsPage(tableId, pageIndex, pageSize, userId);

        return ResponseFormat.retParam(ResponseCodeEnums.CODE_200, comments);
    }

    @GetMapping("/count")
    @ApiOperation(value = "获取帖子评论总数",notes = "获取帖子评论总数，返回评论总数")
    public ResponseGeneric<Integer> getCount(
            @ApiParam("帖子id") @RequestParam("tableId") int tableId,
            @ApiParam("token") @RequestHeader(value = "token", required = false) String token) throws Exception {

        int userId = serviceUser.getUserId(token);
        int count = serviceComment.getCount(tableId, userId);

        return ResponseFormat.retParam(ResponseCodeEnums.CODE_200, count);
    }

    @PostMapping("/add")
    @ApiOperation(value = "发布评论",notes = "发布评论，不返回内容")
    public ResponseGeneric<Object> post(
            @ApiParam("帖子id") @RequestParam("tableId") int tableId,
            @ApiParam("内容") @RequestParam("content") String content,
            @ApiParam("图片列表") @RequestParam("images") String images,
            @ApiParam("token") @RequestHeader(value = "token", required = false) String token) throws Exception {

        PojoUser user = serviceUser.getUser(token); //验证用户登录状态

        //是否不需要审核
        List<String> imageList = JSONArray.parseArray(images).toJavaList(String.class);
        int status = 0;
        if (!imageList.isEmpty() || UtilsMain.checkText(content)) {
            status = 1;
        }

        serviceComment.addComment(tableId, user.getId(), content, images, status);

        if (status == 1)
            return ResponseFormat.retParam(ResponseCodeEnums.CODE_200, null,"发布成功，等待审核");

        return ResponseFormat.retParam(ResponseCodeEnums.CODE_200, null,"发布成功");
    }
}
