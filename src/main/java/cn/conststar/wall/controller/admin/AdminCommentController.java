package cn.conststar.wall.controller.admin;

import cn.conststar.wall.constant.ResponseEnumConstant;
import cn.conststar.wall.handler.FormatHandler;
import cn.conststar.wall.handler.GenericHandler;
import cn.conststar.wall.pojo.model.CommentDomain;
import cn.conststar.wall.service.adminComment.AdminCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiresRoles("admin")
@RequestMapping("/api/admin/comment")
@Api(tags = "后台--评论管理")
public class AdminCommentController {

    @Autowired
    private AdminCommentService adminCommentService;

    @GetMapping("/pageList")
    @ApiOperation("获取评论分页列表")
    public GenericHandler<List<CommentDomain>> pageList(
            @ApiParam("页索引") @RequestParam("pageIndex") int pageIndex,
            @ApiParam("页大小") @RequestParam("pageSize") int pageSize,
            @ApiParam("排序字段 默认为id") @RequestParam(value = "rankName", defaultValue = "id") String rankName,
            @ApiParam("排序类型 true升序 false降序 默认降序") @RequestParam(value = "rankType", defaultValue = "false") boolean rankType) throws Exception {

        List<CommentDomain> comments = adminCommentService.getComments(pageIndex, pageSize, rankName, rankType);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, comments);
    }

    @GetMapping("/count")
    @ApiOperation("获取评论的数量")
    public GenericHandler<Integer> getCount() throws Exception {

        int count = adminCommentService.getCommentCount();

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, count);
    }

    @GetMapping("/searchList")
    @ApiOperation("搜索评论分页列表")
    public GenericHandler<List<CommentDomain>> searchList(
            @ApiParam("关键词") @RequestParam("keyword") String keyword,
            @ApiParam("页索引") @RequestParam("pageIndex") int pageIndex,
            @ApiParam("页大小") @RequestParam("pageSize") int pageSize,
            @ApiParam("排序字段 默认为id") @RequestParam(value = "rankName", defaultValue = "id") String rankName,
            @ApiParam("排序类型 true升序 false降序 默认降序") @RequestParam(value = "rankType", defaultValue = "false") boolean rankType) throws Exception {

        List<CommentDomain> comments = adminCommentService.getSearchComments(keyword, pageIndex, pageSize, rankName, rankType);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, comments);
    }

    @GetMapping("/searchCount")
    @ApiOperation("搜索评论的数量")
    public GenericHandler<Integer> searchCount(@ApiParam("关键词") @RequestParam("keyword") String keyword) throws Exception {

        int count = adminCommentService.getSearchCommentCount(keyword);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, count);
    }

    @GetMapping("/comment")
    @ApiOperation("获取评论信息")
    public GenericHandler<CommentDomain> getComment(
            @ApiParam("评论id") @RequestParam(value = "commentId") int commentId) throws Exception {

        CommentDomain comment = adminCommentService.getCommentById(commentId);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, comment);
    }

    @PutMapping("/updateComment")
    @ApiOperation("更新评论")
    public GenericHandler<Object> updateComment(
            @ApiParam("帖子id") @RequestParam("commentId") int commentId,
            @ApiParam("是否修改内容") @RequestParam("hasContent") boolean hasContent,
            @ApiParam("内容") @RequestParam(value = "content", required = false) String content,
            @ApiParam("是否修改图片列表") @RequestParam("hasImages") boolean hasImages,
            @ApiParam("图片列表") @RequestParam(value = "images", required = false) String images,
            @ApiParam("是否修改状态") @RequestParam("hasState") boolean hasState,
            @ApiParam("状态") @RequestParam(value = "state", required = false) Integer state) throws Exception {

        CommentDomain comment = adminCommentService.getCommentById(commentId);
        if (hasContent) {
            comment.setContent(content);
        }
        if (hasImages) {
            comment.setImages(images);
        }
        if (hasState) {
            comment.setState(state);
        }


        adminCommentService.updateComment(comment);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, null, "修改成功");
    }

    @DeleteMapping("/deleteComment")
    @ApiOperation("删除评论")
    public GenericHandler<Object> deleteComment(
            @ApiParam("评论id") @RequestParam(value = "commentId") int commentId) throws Exception {

        adminCommentService.deleteComment(commentId);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, null, "删除成功");
    }
}
