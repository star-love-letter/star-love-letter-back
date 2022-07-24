package cn.conststar.wall.controller;

import cn.conststar.wall.constant.ResponseEnumConstant;
import cn.conststar.wall.handler.FormatHandler;
import cn.conststar.wall.handler.GenericHandler;
import cn.conststar.wall.pojo.model.ReportCommentDomain;
import cn.conststar.wall.service.reportComment.ReportCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reportComment")
@Api(tags = "评论举报")
public class ReportCommentController {

    @Autowired
    private ReportCommentService reportCommentService;

    @PostMapping("/report")
    @ApiOperation("举报评论")
    public GenericHandler<Object> report(
            @ApiParam("评论id") @RequestParam("commentId") int commentId,
            @ApiParam("类型") @RequestParam("type") int type,
            @ApiParam("内容") @RequestParam("content") String content) throws Exception {

        ReportCommentDomain reportComment = new ReportCommentDomain();
        reportComment.setCommentId(commentId);
        reportComment.setType(type);
        reportComment.setContent(content);

        reportCommentService.report(reportComment);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, null, "举报成功");
    }

}
