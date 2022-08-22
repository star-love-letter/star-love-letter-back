package cn.conststar.wall.controller.admin;

import cn.conststar.wall.constant.ResponseEnumConstant;
import cn.conststar.wall.handler.FormatHandler;
import cn.conststar.wall.handler.GenericHandler;
import cn.conststar.wall.service.adminSetting.AdminSettingService;
import cn.conststar.wall.utils.EmailSend;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiresRoles("admin")
@RequestMapping("/api/admin/setting")
@Api(tags = "后台--系统设置")
public class AdminSettingController {

    @Autowired
    private AdminSettingService adminSettingService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/sqlForExecute")
    @ApiOperation(value = "直接执行SQL execute")
    public GenericHandler<Object> sqlForExecute(@ApiParam("SQL") @RequestParam("sql") String sql) throws Exception {

        jdbcTemplate.execute(sql);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, null, "执行成功");
    }

    @PostMapping("/sqlForUpdate")
    @ApiOperation(value = "直接执行SQL update")
    public GenericHandler<Integer> sqlForUpdate(@ApiParam("SQL") @RequestParam("sql") String sql) throws Exception {

        int line = jdbcTemplate.update(sql);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, null, "本次操作总共更新了" + line + "行");
    }

    @PostMapping("/sqlForQuery")
    @ApiOperation(value = "直接执行SQL query")
    public GenericHandler<List<Map<String, Object>>> sqlForQuery(@ApiParam("SQL") @RequestParam("sql") String sql) throws Exception {

        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, maps, "执行成功");
    }


    @GetMapping("/getSettings")
    @ApiOperation(value = "获取系统设置")
    public GenericHandler<Map<String, String>> getList() throws Exception {

        Map<String, String> settingMap = adminSettingService.getSettingMap();

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, settingMap);
    }

    @PutMapping("/updateSettings")
    @ApiOperation(value = "修改系统设置")
    public GenericHandler<Object> updateSettings(@ApiParam("需要更新的键值对") @RequestParam Map<String, String> settingMap) throws Exception {

        adminSettingService.updateSettingMap(settingMap);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, null, "修改成功");
    }

    @GetMapping("/emailTest")
    @ApiOperation(value = "发送测试邮件")
    public GenericHandler<Object> emailTest(
            @ApiParam("收件人地址") @RequestParam("receiveMail") String receiveMail,
            @ApiParam("主题") @RequestParam("subject") String subject,
            @ApiParam("内容") @RequestParam("content") String content) throws Exception {

        EmailSend.sendAsync(subject, content, receiveMail);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, null, "邮件发送成功");
    }
}
