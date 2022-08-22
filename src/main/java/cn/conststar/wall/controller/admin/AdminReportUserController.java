package cn.conststar.wall.controller.admin;

import cn.conststar.wall.constant.ResponseEnumConstant;
import cn.conststar.wall.handler.FormatHandler;
import cn.conststar.wall.handler.GenericHandler;
import cn.conststar.wall.pojo.model.ReportUserDomain;
import cn.conststar.wall.pojo.model.UserDomain;
import cn.conststar.wall.service.adminReportUser.AdminReportUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiresRoles("admin")
@RequestMapping("/api/admin/reportUser")
@Api(tags = "后台--用户举报")
public class AdminReportUserController {

    @Autowired
    private AdminReportUserService adminReportUserService;


    @GetMapping("/pageList")
    @ApiOperation("获取举报用户分页列表")
    public GenericHandler<List<UserDomain>> pageList(
            @ApiParam("页索引") @RequestParam("pageIndex") int pageIndex,
            @ApiParam("页大小") @RequestParam("pageSize") int pageSize,
            @ApiParam("获取举报的状态") @RequestParam("state") int state) throws Exception {

        List<UserDomain> users = adminReportUserService.getReportUsers(pageIndex, pageSize, state);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, users);
    }

    @GetMapping("/count")
    @ApiOperation("获取举报用户的数量")
    public GenericHandler<Integer> getReportUserCount(
            @ApiParam("获取举报的状态") @RequestParam("state") int state) throws Exception {

        int count = adminReportUserService.getReportUserCount(state);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, count);
    }

    @GetMapping("/searchList")
    @ApiOperation("搜索举报用户分页列表")
    public GenericHandler<List<UserDomain>> searchList(
            @ApiParam("关键词") @RequestParam("keyword") String keyword,
            @ApiParam("页索引") @RequestParam("pageIndex") int pageIndex,
            @ApiParam("页大小") @RequestParam("pageSize") int pageSize,
            @ApiParam("获取举报的状态") @RequestParam("state") int state) throws Exception {

        List<UserDomain> users = adminReportUserService.getSearchReportUsers(keyword, pageIndex, pageSize, state);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, users);
    }

    @GetMapping("/searchCount")
    @ApiOperation("搜索举报用户的数量")
    public GenericHandler<Integer> searchCount(
            @ApiParam("关键词") @RequestParam("keyword") String keyword,
            @ApiParam("获取举报的状态") @RequestParam("state") int state) throws Exception {

        int count = adminReportUserService.getSearchReportUserCount(keyword, state);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, count);
    }

    @GetMapping("/getReportByUser")
    @ApiOperation("通过用户获取举报内容")
    public GenericHandler<List<ReportUserDomain>> getReportByUser(
            @ApiParam("用户id") @RequestParam("userId") int userId) throws Exception {

        List<ReportUserDomain> reportUsers = adminReportUserService.getReportByUser(userId);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, reportUsers);
    }


    @GetMapping("/getReportByReportUser")
    @ApiOperation("通过举报者获取举报内容")
    public GenericHandler<List<ReportUserDomain>> getReportByReportUser(
            @ApiParam("举报者的用户id") @RequestParam("reportUserId") int reportUserId) throws Exception {

        List<ReportUserDomain> reportUsers = adminReportUserService.getReportByReportUser(reportUserId);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, reportUsers);
    }

    @PutMapping("/handleReport")
    @ApiOperation("处理举报用户")
    public GenericHandler<Object> handleReport(
            @ApiParam("用户id") @RequestParam("userId") int userId,
            @ApiParam("是否同意举报") @RequestParam("agree") boolean agree) throws Exception {

        adminReportUserService.handleReport(userId, agree);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, null, "处理成功");
    }
}
