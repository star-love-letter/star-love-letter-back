package cn.conststar.wall.controller.admin;

import cn.conststar.wall.constant.ResponseEnumConstant;
import cn.conststar.wall.handler.FormatHandler;
import cn.conststar.wall.handler.GenericHandler;
import cn.conststar.wall.pojo.model.TableDomain;
import cn.conststar.wall.service.adminTable.AdminTableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiresRoles("admin")
@RequestMapping("/api/admin/table")
@Api(tags = "后台--帖子管理")
public class AdminTableController {

    @Autowired
    private AdminTableService adminTableService;

    @GetMapping("/pageList")
    @ApiOperation(value = "获取帖子分页列表")
    public GenericHandler<List<TableDomain>> pageList(
            @ApiParam("页索引") @RequestParam("pageIndex") int pageIndex,
            @ApiParam("页大小") @RequestParam("pageSize") int pageSize,
            @ApiParam("排序字段 默认为id") @RequestParam(value = "rankName", defaultValue = "id") String rankName,
            @ApiParam("排序类型 true升序 false降序 默认降序") @RequestParam(value = "rankType", defaultValue = "false") boolean rankType) throws Exception {

        List<TableDomain> tables = adminTableService.getTables(pageIndex, pageSize, rankName, rankType);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, tables);
    }

    @GetMapping("/count")
    @ApiOperation(value = "获取帖子的数量")
    public GenericHandler<Integer> getCount() throws Exception {

        int count = adminTableService.getTableCount();

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, count);
    }

    @GetMapping("/searchList")
    @ApiOperation(value = "搜索帖子分页列表")
    public GenericHandler<List<TableDomain>> searchList(
            @ApiParam("关键词") @RequestParam("keyword") String keyword,
            @ApiParam("页索引") @RequestParam("pageIndex") int pageIndex,
            @ApiParam("页大小") @RequestParam("pageSize") int pageSize,
            @ApiParam("排序字段 默认为id") @RequestParam(value = "rankName", defaultValue = "id") String rankName,
            @ApiParam("排序类型 true升序 false降序 默认降序") @RequestParam(value = "rankType", defaultValue = "false") boolean rankType) throws Exception {

        List<TableDomain> tables = adminTableService.getSearchTables(keyword, pageIndex, pageSize, rankName, rankType);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, tables);
    }

    @GetMapping("/searchCount")
    @ApiOperation(value = "搜索帖子的数量")
    public GenericHandler<Integer> searchCount(@ApiParam("关键词") @RequestParam("keyword") String keyword) throws Exception {

        int count = adminTableService.getSearchTableCount(keyword);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, count);
    }

    @GetMapping("/table")
    @ApiOperation(value = "获取帖子信息")
    public GenericHandler<TableDomain> getTable(
            @ApiParam("帖子id") @RequestParam(value = "tableId") int tableId) throws Exception {

        TableDomain table = adminTableService.getTableById(tableId);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, table);
    }

    @GetMapping("/getExamineCount")
    @ApiOperation(value = "获取待审核帖子的数量")
    public GenericHandler<Integer> getExamineCount() throws Exception {

        int count = adminTableService.getExamineCount();

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, count);
    }

    @PutMapping("/updateTable")
    @ApiOperation(value = "更新帖子")
    public GenericHandler<Object> updateTable(
            @ApiParam("帖子id") @RequestParam("tableId") int tableId,
            @ApiParam("表白者名称") @RequestParam("sender") String sender,
            @ApiParam("表白者性别") @RequestParam("senderGender") Integer senderGender,
            @ApiParam("被表白者名称") @RequestParam("recipient") String recipient,
            @ApiParam("被表白者性别") @RequestParam("recipientGender") Integer recipientGender,
            @ApiParam("内容") @RequestParam("content") String content,
            @ApiParam("图片列表") @RequestParam("images") String images,
            @ApiParam("是否通知邮箱") @RequestParam("notifyEmail") Boolean notifyEmail,
            @ApiParam("状态") @RequestParam("state") Integer state) throws Exception {

        TableDomain table = adminTableService.getTableById(tableId);
        table.setSender(sender);
        table.setSenderGender(senderGender);
        table.setRecipient(recipient);
        table.setRecipientGender(recipientGender);
        table.setContent(content);
        table.setImages(images);
        table.setNotifyEmail(notifyEmail);
        table.setState(state);

        adminTableService.updateTable(table);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, null, "修改成功");
    }

    @DeleteMapping("/deleteTable")
    @ApiOperation(value = "删除帖子")
    public GenericHandler<Object> deleteTable(
            @ApiParam("帖子id") @RequestParam(value = "tableId") int tableId) throws Exception {

        adminTableService.deleteTable(tableId);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, null, "删除成功");
    }
}
