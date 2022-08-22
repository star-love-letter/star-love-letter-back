package cn.conststar.wall.controller.admin;

import cn.conststar.wall.constant.ResponseEnumConstant;
import cn.conststar.wall.handler.FormatHandler;
import cn.conststar.wall.handler.GenericHandler;
import cn.conststar.wall.pojo.model.ReportTableDomain;
import cn.conststar.wall.pojo.model.TableDomain;
import cn.conststar.wall.service.adminReportTable.AdminReportTableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiresRoles("admin")
@RequestMapping("/api/admin/reportTable")
@Api(tags = "后台--帖子举报")
public class AdminReportTableController {

    @Autowired
    private AdminReportTableService adminReportTableService;


    @GetMapping("/pageList")
    @ApiOperation("获取举报帖子分页列表")
    public GenericHandler<List<TableDomain>> pageList(
            @ApiParam("页索引") @RequestParam("pageIndex") int pageIndex,
            @ApiParam("页大小") @RequestParam("pageSize") int pageSize,
            @ApiParam("获取举报的状态") @RequestParam("state") int state) throws Exception {

        List<TableDomain> tables = adminReportTableService.getReportTables(pageIndex, pageSize, state);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, tables);
    }

    @GetMapping("/count")
    @ApiOperation("获取举报帖子的数量")
    public GenericHandler<Integer> getReportTableCount(
            @ApiParam("获取举报的状态") @RequestParam("state") int state) throws Exception {

        int count = adminReportTableService.getReportTableCount(state);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, count);
    }

    @GetMapping("/searchList")
    @ApiOperation("搜索举报帖子分页列表")
    public GenericHandler<List<TableDomain>> searchList(
            @ApiParam("关键词") @RequestParam("keyword") String keyword,
            @ApiParam("页索引") @RequestParam("pageIndex") int pageIndex,
            @ApiParam("页大小") @RequestParam("pageSize") int pageSize,
            @ApiParam("获取举报的状态") @RequestParam("state") int state) throws Exception {

        List<TableDomain> tables = adminReportTableService.getSearchReportTables(keyword, pageIndex, pageSize, state);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, tables);
    }

    @GetMapping("/searchCount")
    @ApiOperation("搜索举报帖子的数量")
    public GenericHandler<Integer> searchCount(
            @ApiParam("关键词") @RequestParam("keyword") String keyword,
            @ApiParam("获取举报的状态") @RequestParam("state") int state) throws Exception {

        int count = adminReportTableService.getSearchReportTableCount(keyword, state);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, count);
    }

    @GetMapping("/getReportByTable")
    @ApiOperation("通过帖子获取举报内容")
    public GenericHandler<List<ReportTableDomain>> getReportByTable(
            @ApiParam("帖子id") @RequestParam("tableId") int tableId) throws Exception {

        List<ReportTableDomain> reportTables = adminReportTableService.getReportByTable(tableId);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, reportTables);
    }

    @GetMapping("/getReportByReportUser")
    @ApiOperation("通过举报者获取举报内容")
    public GenericHandler<List<ReportTableDomain>> getReportByReportUser(
            @ApiParam("举报者的用户id") @RequestParam("reportUserId") int reportUserId) throws Exception {

        List<ReportTableDomain> reportTables = adminReportTableService.getReportByReportUser(reportUserId);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, reportTables);
    }

    @PutMapping("/handleReport")
    @ApiOperation("处理举报帖子")
    public GenericHandler<Object> handleReport(
            @ApiParam("帖子id") @RequestParam("tableId") int tableId,
            @ApiParam("是否同意举报") @RequestParam("agree") boolean agree) throws Exception {

        adminReportTableService.handleReport(tableId, agree);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, null, "处理成功");
    }
}
