package cn.conststar.wall.controller.admin;


import cn.conststar.wall.pojo.model.UserDomain;
import cn.conststar.wall.service.adminLog.AdminLogService;
import cn.conststar.wall.service.user.UserService;
import cn.conststar.wall.utils.Tools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

@RestController
@RequiresRoles("admin")
@RequestMapping("/api/admin/file")
@Api(tags = "后台--文件管理")
public class AdminFileController {

    @Autowired
    private UserService userService;

    @Autowired
    private AdminLogService adminLogService;

    @GetMapping("/getDataBackup")
    @ApiOperation(value = "获取备份数据文件")
    public String getTableImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        UserDomain admin = userService.getUserBySubject();
        adminLogService.addLog("获取备份数据文件", "sqlite.db", admin.getId());

        File file = new File(Tools.DATA_PATH, "sqlite.db");
        //读取文件--输入流
        FileInputStream input = new FileInputStream(file);

        //写出文件--输出流
        OutputStream out = response.getOutputStream();

        //设置response 响应头
        response.reset(); //设置页面不缓存,清空buffer
        response.setHeader("Content-Disposition", "attachment;filename=sqlite.db");

        byte[] buff = new byte[1024];
        int index = 0;
        //执行 写出操作
        while ((index = input.read(buff)) != -1) {
            out.write(buff, 0, index);
            out.flush();
        }

        out.close();
        input.close();
        return null;
    }
}
