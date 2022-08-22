package cn.conststar.wall;

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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class ServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private TableService tableService;
    @Autowired
    private CommentService commentService;

    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private AdminTableService adminTableService;
    @Autowired
    private AdminCommentService adminCommentService;

    @Autowired
    private AdminReportUserService adminReportUserService;
    @Autowired
    private AdminReportTableService adminReportTableService;
    @Autowired
    private AdminReportCommentService adminReportCommentService;

    @Autowired
    private AdminLogService adminLogService;
    @Autowired
    private AdminSettingService adminSettingService;

//    @Test
//    void userTest() throws Exception {
//        System.out.println("开始测试 用户服务层");
//        userService.getUserInfoPublic(1);
//        userService.getUserDomain("1");
//        userService.getUserInfoPublicByTable(1);
//        userService.getUserInfoPublicByComment(1);
//    }

    @Test
    void tableTest() throws Exception {
        System.out.println("开始测试 帖子服务层");
        tableService.getTables(1, 1, "id", true);
        tableService.getCount();
        tableService.getSearchTables("1", 1, 1, "id", true);
        tableService.getSearchCount("1");
        tableService.getTable(1);
    }

    @Test
    void commentTest() throws Exception {
        System.out.println("开始测试 评论服务层");
        commentService.getComments(1, 1, 1, "id", true);
        commentService.getCount(1);
    }

    @Test
    void adminUserTest() throws Exception {
        System.out.println("开始测试 管理用户服务层");
        adminUserService.getUsers(1, 1, "id", true);
        adminUserService.getUserCount();
        adminUserService.getUserById(1);
        adminUserService.getSearchUsers("1", 1, 1, "id", true);
        adminUserService.getSearchUserCount("1");
        adminUserService.getClass();
    }

    @Test
    void adminTableTest() throws Exception {
        System.out.println("开始测试 管理帖子服务层");
        adminTableService.getTables(1, 1, "id", true);
        adminTableService.getTableCount();
        adminTableService.getSearchTables("1", 1, 1, "id", true);
        adminTableService.getSearchTableCount("id");
        adminTableService.getTableById(1);
        adminTableService.getExamineCount();

    }

    @Test
    void adminCommentTest() throws Exception {
        System.out.println("开始测试 管理评论服务层");
        adminCommentService.getComments(1, 1, "id", true);
        adminCommentService.getCommentCount();
        adminCommentService.getSearchComments("1", 1, 1, "id", true);
        adminCommentService.getSearchCommentCount("id");
        adminCommentService.getCommentById(1);
        adminCommentService.getExamineCount();
    }

    @Test
    void adminReportUserTest() throws Exception {
        System.out.println("开始测试 管理举报用户服务层");
        adminReportUserService.getReportUsers(1,1,0);
        adminReportUserService.getReportUserCount(0);
        adminReportUserService.getSearchReportUsers("1",1,1,0);
        adminReportUserService.getSearchReportUserCount("1",0);
        adminReportUserService.getReportByUser(1);
        adminReportUserService.getReportByReportUser(1);
    }

    @Test
    void adminReportTableTest() throws Exception {
        System.out.println("开始测试 管理举报帖子服务层");
        adminReportTableService.getReportTables(1,1,0);
        adminReportTableService.getReportTableCount(0);
        adminReportTableService.getSearchReportTables("1",1,1,0);
        adminReportTableService.getSearchReportTableCount("1",0);
        adminReportTableService.getReportByTable(1);
        adminReportTableService.getReportByReportUser(1);
    }

    @Test
    void adminReportCommentTest() throws Exception {
        System.out.println("开始测试 管理举报评论服务层");
        adminReportCommentService.getReportComments(1,1,0);
        adminReportCommentService.getReportCommentCount(0);
        adminReportCommentService.getSearchReportComments("1",1,1,0);
        adminReportCommentService.getSearchReportCommentCount("1",0);
        adminReportCommentService.getReportByComment(1);
        adminReportCommentService.getReportByReportUser(1);
    }

    @Test
    void adminLogTest() throws Exception {
        System.out.println("开始测试 管理日志服务层");
        adminLogService.getLogs(1,1,"id",true);
        adminLogService.getLogCount();
        adminLogService.getSearchLogs("1",1,1,"id",true);
        adminLogService.getSearchLogCount("1");
        adminLogService.all();
    }

    @Test
    void adminSettingTest() throws Exception {
        System.out.println("开始测试 管理设置服务层");
        adminSettingService.getSettingMap();
    }

}
