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

    @PutMapping("/updateUser")
    @ApiOperation(value = "更新用户")
    public GenericHandler<Object> updateUser(
            @ApiParam("用户id") @RequestParam("userId") int userId,
            @ApiParam("是否修改名称") @RequestParam("hasName") boolean hasName,
            @ApiParam("名称") @RequestParam(value = "name", required = false) String name,
            @ApiParam("是否修改邮箱") @RequestParam("hasEmail") boolean hasEmail,
            @ApiParam("邮箱") @RequestParam(value = "email", required = false) String email,
            @ApiParam("是否修改微信") @RequestParam("hasWechat") boolean hasWechat,
            @ApiParam("微信") @RequestParam(value = "wechat", required = false) String wechat,
            @ApiParam("是否修改学号") @RequestParam("hasStudentId") boolean hasStudentId,
            @ApiParam("学号") @RequestParam(value = "studentId", required = false) Integer studentId,
            @ApiParam("是否修改状态") @RequestParam("hasState") boolean hasState,
            @ApiParam("状态") @RequestParam(value = "state", required = false) Integer state) throws Exception {

        UserDomain user = adminUserService.getUserById(userId);
        if (hasName) {
            user.setName(name.isEmpty() ? null : name);
        }
        if (hasEmail) {
            user.setEmail(email.isEmpty() ? null : email);
        }
        if (hasWechat) {
            user.setWechat(wechat.isEmpty() ? null : wechat);
        }
        if (hasStudentId) {
            user.setStudentId(studentId);
        }
        if (hasState) {
            user.setState(state == null ? 0 : state);
        }

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
