package cn.conststar.wall.service.adminUser.impl;

import cn.conststar.wall.constant.ResponseEnumConstant;
import cn.conststar.wall.dao.AdminUserDao;
import cn.conststar.wall.exception.BusinessException;
import cn.conststar.wall.pojo.model.UserDomain;
import cn.conststar.wall.service.adminLog.AdminLogService;
import cn.conststar.wall.service.adminUser.AdminUserService;
import cn.conststar.wall.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    private AdminUserDao adminUserDao;


    @Autowired
    private UserService userService;

    @Autowired
    private AdminLogService adminLogService;

    // 获取用户列表
    public List<UserDomain> getUsers(int pageIndex, int pageSize, String rankName, boolean rankType) throws Exception {
        if (pageIndex < 1 || pageSize < 0)
            throw new BusinessException("页数有误");

        int startIndex = (pageIndex - 1) * pageSize;
        return adminUserDao.getUsers(startIndex, pageSize, rankName, rankType);
    }

    // 搜索用户
    @Override
    public List<UserDomain> getSearchUsers(String keyword, int pageIndex, int pageSize, String rankName, boolean rankType) throws Exception {
        if (pageIndex < 1 || pageSize < 0)
            throw new BusinessException("页数有误");

        int startIndex = (pageIndex - 1) * pageSize;
        return adminUserDao.getSearchUsers(keyword, startIndex, pageSize, rankName, rankType);
    }

    // 搜索用户数量
    @Override
    public int getSearchUserCount(String keyword) throws Exception {
        return adminUserDao.getSearchUserCount(keyword);
    }

    // 获取用户数量
    public int getUserCount() throws Exception {
        return adminUserDao.getUserCount();
    }

    // 获取用户
    public UserDomain getUserById(int userId) throws Exception {
        return adminUserDao.getUserById(userId);
    }

    @Override
    public int getExamineCount() throws Exception {
        return adminUserDao.getExamineCount();
    }

    // 更新用户
    public void updateUser(UserDomain user) throws Exception {
//        if (user.getRoleId() == 0)
//            throw new BusinessException("无法通过后台更新管理员用户", ResponseEnumConstant.CODE_70001);

        if (user.getEmail() == null && user.getWechat() == null) {
            throw new BusinessException("邮箱或微信必须保留一个", ResponseEnumConstant.CODE_201);
        }

        UserDomain admin = userService.getUserBySubject();
        adminLogService.addLog("更新用户", user.toString(), admin.getId());

        adminUserDao.updateUser(user);
    }

    // 删除用户
    public void deleteUser(int userId) throws Exception {

        UserDomain user = userService.getUserBySubject();
        UserDomain admin = userService.getUserBySubject();
        adminLogService.addLog("删除用户", user.toString(), admin.getId());

        adminUserDao.deleteUser(userId);
    }

}
