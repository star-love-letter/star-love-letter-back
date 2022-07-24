package cn.conststar.wall.controller;

import cn.conststar.wall.pojo.dto.TableDto;
import cn.conststar.wall.constant.ResponseEnumConstant;
import cn.conststar.wall.handler.FormatHandler;
import cn.conststar.wall.handler.GenericHandler;
import cn.conststar.wall.pojo.model.TableDomain;
import cn.conststar.wall.service.table.TableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/table")
@Api(tags = "帖子内容")
public class TableController {

    @Autowired
    private TableService tableService;

    @GetMapping("/pageList")
    @ApiOperation("获取帖子分页列表")
    public GenericHandler<List<TableDto>> getPageList(
            @ApiParam("页索引") @RequestParam("pageIndex") int pageIndex,
            @ApiParam("页大小") @RequestParam("pageSize") int pageSize,
            @ApiParam("排序字段 默认为id") @RequestParam(value = "rankName", defaultValue = "id") String rankName,
            @ApiParam("排序类型 true升序 false降序 默认降序") @RequestParam(value = "rankType", defaultValue = "false") boolean rankType) throws Exception {

        List<TableDto> tables = tableService.getTables(pageIndex, pageSize, rankName, rankType);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, tables);
    }

    @GetMapping("/count")
    @ApiOperation("获取帖子的数量")
    public GenericHandler<Integer> getCount() throws Exception {

        int count = tableService.getCount();

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, count);
    }

    @GetMapping("/searchList")
    @ApiOperation("搜索帖子")
    public GenericHandler<List<TableDto>> getSearchTables(
            @ApiParam("关键词") @RequestParam("keyword") String keyword,
            @ApiParam("页索引") @RequestParam("pageIndex") int pageIndex,
            @ApiParam("页大小") @RequestParam("pageSize") int pageSize,
            @ApiParam("排序字段 默认为id") @RequestParam(value = "rankName", defaultValue = "id") String rankName,
            @ApiParam("排序类型 true升序 false降序 默认降序") @RequestParam(value = "rankType", defaultValue = "false") boolean rankType) throws Exception {

        List<TableDto> tables = tableService.getSearchTables(keyword, pageIndex, pageSize, rankName, rankType);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, tables, "搜索成功");
    }

    @GetMapping("/searchCount")
    @ApiOperation("获取搜索帖子的数量")
    public GenericHandler<Integer> getSearchCount(@ApiParam("关键词") @RequestParam("keyword") String keyword) throws Exception {

        int count = tableService.getSearchCount(keyword);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, count);
    }

    @GetMapping("/myPageList")
    @ApiOperation("获取我的帖子分页列表")
    public GenericHandler<List<TableDto>> getMyPageList(
            @ApiParam("页索引") @RequestParam("pageIndex") int pageIndex,
            @ApiParam("页大小") @RequestParam("pageSize") int pageSize,
            @ApiParam("排序字段 默认为id") @RequestParam(value = "rankName", defaultValue = "id") String rankName,
            @ApiParam("排序类型 true升序 false降序 默认降序") @RequestParam(value = "rankType", defaultValue = "false") boolean rankType) throws Exception {

        List<TableDto> tables = tableService.getMyTables(pageIndex, pageSize, rankName, rankType);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, tables);
    }

    @GetMapping("/myCount")
    @ApiOperation("获取我的帖子的数量")
    public GenericHandler<Integer> getMyCount() throws Exception {

        int count = tableService.getMyCount();

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, count);
    }

    @GetMapping("/mySearchList")
    @ApiOperation("搜索我的帖子")
    public GenericHandler<List<TableDto>> getMySearchTables(
            @ApiParam("关键词") @RequestParam("keyword") String keyword,
            @ApiParam("页索引") @RequestParam("pageIndex") int pageIndex,
            @ApiParam("页大小") @RequestParam("pageSize") int pageSize,
            @ApiParam("排序字段 默认为id") @RequestParam(value = "rankName", defaultValue = "id") String rankName,
            @ApiParam("排序类型 true升序 false降序 默认降序") @RequestParam(value = "rankType", defaultValue = "false") boolean rankType) throws Exception {

        List<TableDto> tables = tableService.getMySearchTables(keyword, pageIndex, pageSize, rankName, rankType);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, tables, "搜索成功");
    }

    @GetMapping("/mySearchCount")
    @ApiOperation("获取搜索我的帖子的数量")
    public GenericHandler<Integer> getMySearchCount(@ApiParam("关键词") @RequestParam("keyword") String keyword) throws Exception {

        int count = tableService.getMySearchCount(keyword);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, count);
    }

    @GetMapping("/table")
    @ApiOperation("获取单个帖子内容")
    public GenericHandler<TableDto> getTable(
            @ApiParam("帖子id") @RequestParam("id") int id) throws Exception {

        TableDto table = tableService.getTable(id);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, table);
    }

    @PostMapping("/add")
    @ApiOperation("发布表白")
    public GenericHandler<Object> add(
            @ApiParam("表白者名称") @RequestParam("sender") String sender,
            @ApiParam("表白者性别 1男生 2女生 0保密") @RequestParam("senderGender") int senderGender,
            @ApiParam("被表白者名称") @RequestParam("recipient") String recipient,
            @ApiParam("被表白者性别 1男生 2女生 0保密") @RequestParam("recipientGender") int recipientGender,
            @ApiParam("是否匿名") @RequestParam("anonymous") boolean anonymous,
            @ApiParam("表白内容") @RequestParam("content") String content,
            @ApiParam("图片列表") @RequestParam("images") String images,
            @ApiParam("是否邮箱通知") @RequestParam("notifyEmail") boolean notifyEmail) throws Exception {

        TableDomain table = new TableDomain();
        table.setAnonymous(anonymous);
        table.setSender(sender.trim());
        table.setSenderGender(senderGender);
        table.setRecipient(recipient.trim());
        table.setRecipientGender(recipientGender);
        table.setContent(content.trim());
        table.setImages(images.trim());
        table.setNotifyEmail(notifyEmail);

        tableService.addTable(table);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, null, "发布成功");
    }

    @PutMapping("/support")
    @ApiOperation("点赞")
    public GenericHandler<Object> putSupport(@ApiParam("帖子id") @RequestParam("tableId") int tableId) throws Exception {

        tableService.addSupport(tableId);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, null, "点赞成功");
    }

    @DeleteMapping("/support")
    @ApiOperation("取消点赞")
    public GenericHandler<Object> deleteSupport(@ApiParam("帖子id") @RequestParam("tableId") int tableId) throws Exception {

        tableService.deleteSupport(tableId);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, null, "已取消点赞");
    }

    @DeleteMapping("/table")
    @ApiOperation("删除帖子")
    public GenericHandler<Object> deleteTable(@ApiParam("帖子id") @RequestParam("tableId") int tableId) throws Exception {

        tableService.deleteTable(tableId);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, null, "删除成功");
    }
}

