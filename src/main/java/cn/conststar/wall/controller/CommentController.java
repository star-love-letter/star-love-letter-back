package cn.conststar.wall.controller;

import cn.conststar.wall.pojo.dto.CommentDto;
import cn.conststar.wall.constant.ResponseEnumConstant;
import cn.conststar.wall.handler.FormatHandler;
import cn.conststar.wall.handler.GenericHandler;
import cn.conststar.wall.pojo.model.CommentDomain;
import cn.conststar.wall.service.comment.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@Api(tags = "评论内容")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/pageList")
    @ApiOperation("获取帖子分页评论列表")
    public GenericHandler<List<CommentDto>> getPageList(
            @ApiParam("帖子id") @RequestParam("tableId") int tableId,
            @ApiParam("页索引") @RequestParam("pageIndex") int pageIndex,
            @ApiParam("页大小") @RequestParam("pageSize") int pageSize,
            @ApiParam("排序字段 默认为id") @RequestParam(value = "rankName", defaultValue = "id") String rankName,
            @ApiParam("排序类型 true升序 false降序 默认降序") @RequestParam(value = "rankType", defaultValue = "false") boolean rankType) throws Exception {

        List<CommentDto> comments = commentService.getComments(tableId, pageIndex, pageSize, rankName, rankType);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, comments);
    }

    @GetMapping("/count")
    @ApiOperation("获取帖子评论的数量")
    public GenericHandler<Integer> getCount(
            @ApiParam("帖子id") @RequestParam("tableId") int tableId) throws Exception {

        int count = commentService.getCount(tableId);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, count);
    }

    @GetMapping("/myPageList")
    @ApiOperation("获取我的评论分页列表")
    public GenericHandler<List<CommentDto>> getMyPageList(
            @ApiParam("页索引") @RequestParam("pageIndex") int pageIndex,
            @ApiParam("页大小") @RequestParam("pageSize") int pageSize,
            @ApiParam("排序字段 默认为id") @RequestParam(value = "rankName", defaultValue = "id") String rankName,
            @ApiParam("排序类型 true升序 false降序 默认降序") @RequestParam(value = "rankType", defaultValue = "false") boolean rankType) throws Exception {

        List<CommentDto> tables = commentService.getMyComments(pageIndex, pageSize, rankName, rankType);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, tables);
    }

    @GetMapping("/myCount")
    @ApiOperation("获取我的评论的数量")
    public GenericHandler<Integer> getMyCount() throws Exception {

        int count = commentService.getMyCount();

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, count);
    }

    @GetMapping("/mySearchList")
    @ApiOperation("搜索我的评论")
    public GenericHandler<List<CommentDto>> getMySearchComments(
            @ApiParam("关键词") @RequestParam("keyword") String keyword,
            @ApiParam("页索引") @RequestParam("pageIndex") int pageIndex,
            @ApiParam("页大小") @RequestParam("pageSize") int pageSize,
            @ApiParam("排序字段 默认为id") @RequestParam(value = "rankName", defaultValue = "id") String rankName,
            @ApiParam("排序类型 true升序 false降序 默认降序") @RequestParam(value = "rankType", defaultValue = "false") boolean rankType) throws Exception {

        List<CommentDto> tables = commentService.getMySearchComments(keyword, pageIndex, pageSize, rankName, rankType);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, tables, "搜索成功");
    }

    @GetMapping("/mySearchCount")
    @ApiOperation("获取搜索我的评论的数量")
    public GenericHandler<Integer> getMySearchCount(@ApiParam("关键词") @RequestParam("keyword") String keyword) throws Exception {

        int count = commentService.getMySearchCount(keyword);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, count);
    }

    @PostMapping("/add")
    @ApiOperation("发布评论")
    public GenericHandler<Object> post(
            @ApiParam("帖子id") @RequestParam("tableId") int tableId,
            @ApiParam("内容") @RequestParam("content") String content,
            @ApiParam("图片列表") @RequestParam("images") String images) throws Exception {

        CommentDomain comment = new CommentDomain();
        comment.setTableId(tableId);
        comment.setContent(content);
        comment.setImages(images);

        commentService.addComment(comment);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, null, "发布成功");
    }

    @DeleteMapping("/comment")
    @ApiOperation(value = "删除评论", notes = "只能是发布者删除")
    public GenericHandler<Object> deleteTable(@ApiParam("评论id") @RequestParam("commentId") int commentId) throws Exception {

        commentService.deleteComment(commentId);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, null, "删除成功");
    }
}
