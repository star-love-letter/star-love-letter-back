package cn.conststar.wall.controller.admin;

import cn.conststar.wall.constant.ResponseEnumConstant;
import cn.conststar.wall.handler.FormatHandler;
import cn.conststar.wall.handler.GenericHandler;
import cn.conststar.wall.pojo.model.LogDomain;
import cn.conststar.wall.service.adminLog.AdminLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiresRoles("admin")
@RequestMapping("/api/admin/log")
@Api(tags ="后台--日志")
public class AdminLogController {
    @Autowired
    private AdminLogService adminLogService;

    @GetMapping("/pageList")
    @ApiOperation(value = "获取日志分页列表")
    public GenericHandler<List<LogDomain>> pageList(
            @ApiParam("页索引") @RequestParam("pageIndex") int pageIndex,
            @ApiParam("页大小") @RequestParam("pageSize") int pageSize,
            @ApiParam("排序字段 默认为id") @RequestParam(value = "rankName", defaultValue = "id") String rankName,
            @ApiParam("排序类型 true升序 false降序 默认降序") @RequestParam(value = "rankType", defaultValue = "false") boolean rankType) throws Exception {

        List<LogDomain> logs = adminLogService.getLogs(pageIndex, pageSize, rankName, rankType);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, logs);
    }

    @GetMapping("/count")
    @ApiOperation(value = "获取日志的数量")
    public GenericHandler<Integer> getCount() throws Exception {

        int count = adminLogService.getLogCount();

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, count);
    }

    @GetMapping("/searchList")
    @ApiOperation(value = "搜索日志分页列表")
    public GenericHandler<List<LogDomain>> searchList(
            @ApiParam("关键词") @RequestParam("keyword") String keyword,
            @ApiParam("页索引") @RequestParam("pageIndex") int pageIndex,
            @ApiParam("页大小") @RequestParam("pageSize") int pageSize,
            @ApiParam("排序字段 默认为id") @RequestParam(value = "rankName", defaultValue = "id") String rankName,
            @ApiParam("排序类型 true升序 false降序 默认降序") @RequestParam(value = "rankType", defaultValue = "false") boolean rankType) throws Exception {

        List<LogDomain> logs = adminLogService.getSearchLogs(keyword, pageIndex, pageSize, rankName, rankType);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, logs);
    }

    @GetMapping("/searchCount")
    @ApiOperation(value = "搜索日志的数量")
    public GenericHandler<Integer> searchCount(@ApiParam("关键词") @RequestParam("keyword") String keyword) throws Exception {

        int count = adminLogService.getSearchLogCount(keyword);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, count);
    }
}
