package cn.conststar.wall;

import cn.conststar.wall.controller.admin.AdminReportUserController;
import cn.conststar.wall.pojo.dto.CommentDto;
import cn.conststar.wall.service.adminComment.AdminCommentService;
import cn.conststar.wall.service.adminLog.AdminLogService;
import cn.conststar.wall.service.adminReportComment.AdminReportCommentService;
import cn.conststar.wall.service.adminReportTable.AdminReportTableService;
import cn.conststar.wall.service.adminReportUser.AdminReportUserService;
import cn.conststar.wall.service.adminSetting.AdminSettingService;
import cn.conststar.wall.service.adminTable.AdminTableService;
import cn.conststar.wall.service.adminUser.AdminUserService;
import cn.conststar.wall.service.comment.CommentService;
import cn.conststar.wall.service.table.TableService;
import cn.conststar.wall.service.user.UserService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertNotNull;


@SpringBootTest
class StarLoveLetterBackApplicationTests {

    @Test
    public void contextLoads() throws Exception {
    }
}
