package cn.conststar.wall.controller;

import cn.conststar.wall.constant.ResponseEnumConstant;
import cn.conststar.wall.dao.ReportTableDao;
import cn.conststar.wall.handler.FormatHandler;
import cn.conststar.wall.handler.GenericHandler;
import cn.conststar.wall.pojo.model.ReportCommentDomain;
import cn.conststar.wall.pojo.model.ReportTableDomain;
import cn.conststar.wall.service.reportTable.ReportTableService;
import cn.conststar.wall.service.reportTable.impl.ReportTableServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reportTable")
@Api(tags = "帖子举报")
public class ReportTableController {

    @Autowired
    private ReportTableService reportTableService;

    @PostMapping("/report")
    @ApiOperation("举报帖子")
    public GenericHandler<Object> report(
            @ApiParam("帖子id") @RequestParam("tableId") int tableId,
            @ApiParam("类型") @RequestParam("type") int type,
            @ApiParam("内容") @RequestParam("content") String content) throws Exception {

        ReportTableDomain reportTable = new ReportTableDomain();
        reportTable.setTableId(tableId);
        reportTable.setType(type);
        reportTable.setContent(content);

        reportTableService.report(reportTable);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, null, "举报成功");
    }

}
