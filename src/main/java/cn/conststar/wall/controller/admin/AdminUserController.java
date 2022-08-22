package cn.conststar.wall.controller.admin;

import cn.conststar.wall.constant.ResponseEnumConstant;
import cn.conststar.wall.handler.FormatHandler;
import cn.conststar.wall.handler.GenericHandler;
import cn.conststar.wall.pojo.model.UserDomain;
import cn.conststar.wall.service.adminUser.AdminUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiresRoles("admin")
@RequestMapping("/api/admin/user")
@Api(tags = "后台--用户管理")
public class AdminUserController {

    @Autowired
    private AdminUserService adminUserService;

    @GetMapping("/pageList")
    @ApiOperation(value = "获取用户分页列表")
    public GenericHandler<List<UserDomain>> pageList(
            @ApiParam("页索引") @RequestParam("pageIndex") int pageIndex,
            @ApiParam("页大小") @RequestParam("pageSize") int pageSize,
            @ApiParam("排序字段 默认为id") @RequestParam(value = "rankName", defaultValue = "id") String rankName,
            @ApiParam("排序类型 true升序 false降序 默认降序") @RequestParam(value = "rankType", defaultValue = "false") boolean rankType) throws Exception {

        List<UserDomain> users = adminUserService.getUsers(pageIndex, pageSize, rankName, rankType);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, users);
    }

    @GetMapping("/count")
    @ApiOperation(value = "获取用户的数量")
    public GenericHandler<Integer> getCount() throws Exception {

        int count = adminUserService.getUserCount();

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, count);
    }

    @GetMapping("/searchList")
    @ApiOperation(value = "搜索帖子用户列表")
    public GenericHandler<List<UserDomain>> searchList(
            @ApiParam("关键词") @RequestParam("keyword") String keyword,
            @ApiParam("页索引") @RequestParam("pageIndex") int pageIndex,
            @ApiParam("页大小") @RequestParam("pageSize") int pageSize,
            @ApiParam("排序字段 默认为id") @RequestParam(value = "rankName", defaultValue = "id") String rankName,
            @ApiParam("排序类型 true升序 false降序 默认降序") @RequestParam(value = "rankType", defaultValue = "false") boolean rankType) throws Exception {

        List<UserDomain> users = adminUserService.getSearchUsers(keyword, pageIndex, pageSize, rankName, rankType);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, users);
    }

    @GetMapping("/searchCount")
    @ApiOperation(value = "搜索用户的数量")
    public GenericHandler<Integer> searchCount(@ApiParam("关键词") @RequestParam("keyword") String keyword) throws Exception {

        int count = adminUserService.getSearchUserCount(keyword);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, count);
    }

    @GetMapping("/user")
    @ApiOperation(value = "获取用户信息")
    public GenericHandler<UserDomain> getUser(
            @ApiParam("用户id") @RequestParam(value = "userId") int userId) throws Exception {

        UserDomain user = adminUserService.getUserById(userId);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, user);
    }

    @GetMapping("/getExamineCount")
    @ApiOperation(value = "获取待审核用户的数量")
    public GenericHandler<Integer> getExamineCount() throws Exception {

        int count = adminUserService.getExamineCount();

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, count);
    }

    @PutMapping("/updateUser")
    @ApiOperation(value = "更新用户")
    public GenericHandler<Object> updateUser(
            @ApiParam("用户id") @RequestParam("userId") int userId,
            @ApiParam("名称") @RequestParam("name") String name,
            @ApiParam("邮箱") @RequestParam(value = "email", required = false) String email,
            @ApiParam("微信") @RequestParam(value = "wechat", required = false) String wechat,
            @ApiParam("学号") @RequestParam(value = "studentId", required = false) String studentId,
            @ApiParam("状态") @RequestParam("state") Integer state) throws Exception {


        UserDomain user = adminUserService.getUserById(userId);
        user.setName(name);
        user.setEmail(email == null || email.isEmpty() ? null : email);
        user.setWechat(wechat == null || wechat.isEmpty() ? null : wechat);
        user.setStudentId(studentId);
        user.setState(state);

        adminUserService.updateUser(user);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, null, "修改成功");
    }

    @DeleteMapping("/deleteUser")
    @ApiOperation(value = "删除用户")
    public GenericHandler<Object> deleteUser(
            @ApiParam("用户id") @RequestParam(value = "userId") int userId) throws Exception {

        adminUserService.deleteUser(userId);

        return FormatHandler.retParam(ResponseEnumConstant.CODE_200, null, "删除成功");
    }
}
