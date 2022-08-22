package cn.conststar.wall.controller.admin;

import cn.conststar.wall.constant.ResponseEnumConstant;
import cn.conststar.wall.handler.FormatHandler;
import cn.conststar.wall.handler.GenericHandler;
import cn.conststar.wall.pojo.model.CommentDomain;
import cn.conststar.wall.pojo.model.ReportCommentDomain;
import cn.conststar.wall.service.adminReportComment.AdminReportCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiresRoles("admin")
@RequestMapping("/api/admin/reportComment")
@Api(tags = "后台--评论举报")
public class AdminReportCommentController {

    @Autowired
    private AdminReportCommentService adminReportCommentService;


    @GetMapping("/pageList")
    @ApiOperation("获取举报评论分页列表")
    public GenericHandler<List<CommentDomain>> pageList(
            @ApiParam("页索引") @RequestParam("pageIndex") int pageIndex,
            @ApiParam("页大小") @RequestParam("pageSize") int pageSize,
            @ApiParam("获取举报的状态") @RequestParam("state") int state) throws Exception {

        List<CommentDomain> reportComments = adminReportCommentService.getReportComments(pageIndex, pageSize, state);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, reportComments);
    }

    @GetMapping("/count")
    @ApiOperation("获取举报评论的数量")
    public GenericHandler<Integer> getReportCommentCount(
            @ApiParam("获取举报的状态") @RequestParam("state") int state) throws Exception {

        int count = adminReportCommentService.getReportCommentCount(state);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, count);
    }

    @GetMapping("/searchList")
    @ApiOperation("搜索举报评论分页列表")
    public GenericHandler<List<CommentDomain>> searchList(
            @ApiParam("关键词") @RequestParam("keyword") String keyword,
            @ApiParam("页索引") @RequestParam("pageIndex") int pageIndex,
            @ApiParam("页大小") @RequestParam("pageSize") int pageSize,
            @ApiParam("获取举报的状态") @RequestParam("state") int state) throws Exception {

        List<CommentDomain> reportComments = adminReportCommentService.getSearchReportComments(keyword, pageIndex, pageSize, state);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, reportComments);
    }

    @GetMapping("/searchCount")
    @ApiOperation("搜索举报评论的数量")
    public GenericHandler<Integer> searchCount(
            @ApiParam("关键词") @RequestParam("keyword") String keyword,
            @ApiParam("获取举报的状态") @RequestParam("state") int state) throws Exception {

        int count = adminReportCommentService.getSearchReportCommentCount(keyword, state);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, count);
    }

    @GetMapping("/getReportByComment")
    @ApiOperation("通过评论获取举报内容")
    public GenericHandler<List<ReportCommentDomain>> getReportByComment(
            @ApiParam("评论id") @RequestParam("commentId") int commentId) throws Exception {

        List<ReportCommentDomain> reportComments = adminReportCommentService.getReportByComment(commentId);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, reportComments);
    }

    @GetMapping("/getReportByReportUser")
    @ApiOperation("通过举报者获取举报内容")
    public GenericHandler<List<ReportCommentDomain>> getReportByUser(
            @ApiParam("举报者的用户id") @RequestParam("reportUserId") int reportUserId) throws Exception {

        List<ReportCommentDomain> reportComments = adminReportCommentService.getReportByReportUser(reportUserId);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, reportComments);
    }

    @PutMapping("/handleReport")
    @ApiOperation("处理举报评论")
    public GenericHandler<Object> handleReport(
            @ApiParam("评论id") @RequestParam("commentId") int commentId,
            @ApiParam("是否同意举报") @RequestParam("agree") boolean agree) throws Exception {

        adminReportCommentService.handleReport(commentId, agree);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, null, "处理成功");
    }
}
