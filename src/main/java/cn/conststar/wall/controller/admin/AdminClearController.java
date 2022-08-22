package cn.conststar.wall.controller.admin;

import cn.conststar.wall.constant.ResponseEnumConstant;
import cn.conststar.wall.handler.FormatHandler;
import cn.conststar.wall.handler.GenericHandler;
import cn.conststar.wall.service.adminClear.AdminClearService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiresRoles("admin")
@RequestMapping("/api/admin/clear")
@Api(tags = "后台--清理")
public class AdminClearController {

    @Autowired
    private AdminClearService adminClearService;

    @DeleteMapping("/clearReport")
    @ApiOperation(value = "清理举报", notes = "清理多长时间前并且已经处理的举报")
    public GenericHandler<Object> clearReport(@ApiParam("时间戳") @RequestParam("time") long time) throws Exception {

        adminClearService.clearReport(time);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, null, "清理完成");
    }

    @DeleteMapping("/clearImage")
    @ApiOperation(value = "清理图片", notes = "清理无关联的图片文件，返回清理的图片数量")
    public GenericHandler<Object> clearImage() throws Exception {

        int ans = adminClearService.clearImage();

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, ans, "清理完成，总共清理"+ans+"张图片");
    }
}
