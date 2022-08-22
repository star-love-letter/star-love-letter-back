package cn.conststar.wall.service.adminReportUser.Impl;

import cn.conststar.wall.constant.StateCodeConstant;
import cn.conststar.wall.dao.AdminReportUserDao;
import cn.conststar.wall.exception.BusinessException;
import cn.conststar.wall.pojo.model.ReportUserDomain;
import cn.conststar.wall.pojo.model.UserDomain;
import cn.conststar.wall.service.adminReportUser.AdminReportUserService;
import cn.conststar.wall.service.adminUser.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminReportUserServiceImpl implements AdminReportUserService {
    @Autowired
    private AdminReportUserDao adminReportUserDao;

    @Autowired
    private AdminUserService userService;

    @Override
    public List<UserDomain> getReportUsers(int pageIndex, int pageSize, int state) throws Exception {
        if (pageIndex < 1 || pageSize < 0)
            throw new BusinessException("页数有误");

        int startIndex = (pageIndex - 1) * pageSize;
        return adminReportUserDao.getReportUsers(startIndex, pageSize, state);
    }

    @Override
    public int getReportUserCount(int state) throws Exception {
        return adminReportUserDao.getReportUserCount(state);
    }

    @Override
    public List<UserDomain> getSearchReportUsers(String keyword, int pageIndex, int pageSize, int state) throws Exception {
        if (pageIndex < 1 || pageSize < 0)
            throw new BusinessException("页数有误");

        int startIndex = (pageIndex - 1) * pageSize;
        return adminReportUserDao.getSearchReportUsers(keyword, startIndex, pageSize, state);
    }

    @Override
    public int getSearchReportUserCount(String keyword, int state) throws Exception {
        return adminReportUserDao.getSearchReportUserCount(keyword, state);
    }

    @Override
    public List<ReportUserDomain> getReportByUser(int userId) throws Exception {
        return adminReportUserDao.getReportByUser(userId);
    }

    @Override
    public List<ReportUserDomain> getReportByReportUser(int reportUserId) {
        return adminReportUserDao.getReportByReportUser(reportUserId);
    }

    @Override
    public void handleReport(int userId, boolean agree) throws Exception {
        // 同意举报
        if (agree) {
            // 封禁帖子
            UserDomain user = userService.getUserById(userId);
            user.setState(StateCodeConstant.USER_LOCK);
            userService.updateUser(user);

            // 处理举报
            adminReportUserDao.updateReportState(userId, StateCodeConstant.REPORT_SUCCESS);
        }
        // 拒绝举报
        else {
            adminReportUserDao.updateReportState(userId,  StateCodeConstant.REPORT_FAILED);
        }
    }
}
