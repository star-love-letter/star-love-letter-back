package cn.conststar.wall.controller;

import cn.conststar.wall.constant.ResponseEnumConstant;
import cn.conststar.wall.handler.FormatHandler;
import cn.conststar.wall.handler.GenericHandler;
import cn.conststar.wall.pojo.model.ReportUserDomain;
import cn.conststar.wall.service.reportUser.ReportUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reportUser")
@Api(tags = "用户举报")
public class ReportUserController {

    @Autowired
    private ReportUserService reportUserService;

    @PostMapping("/report")
    @ApiOperation("举报用户")
    public GenericHandler<Object> report(
            @ApiParam("用户id") @RequestParam("userId") int userId,
            @ApiParam("类型") @RequestParam("type") int type,
            @ApiParam("内容") @RequestParam("content") String content) throws Exception {

        ReportUserDomain reportUser = new ReportUserDomain();
        reportUser.setUserId(userId);
        reportUser.setType(type);
        reportUser.setContent(content);

        reportUserService.report(reportUser);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, null, "举报成功");
    }

}
