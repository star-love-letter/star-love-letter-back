package cn.conststar.wall.service.reportUser.Impl;

import cn.conststar.wall.dao.ReportUserDao;
import cn.conststar.wall.pojo.model.ReportUserDomain;
import cn.conststar.wall.pojo.model.UserDomain;
import cn.conststar.wall.service.reportUser.ReportUserService;
import cn.conststar.wall.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportUserServiceImpl implements ReportUserService {

    @Autowired
    private ReportUserDao reportUserDao;

    @Autowired
    private UserService userService;

    @Override
    public void report(ReportUserDomain reportUser) throws Exception {
        UserDomain user = userService.getUserBySubject();
        reportUser.setReportUserId(user.getId());

        reportUserDao.report(reportUser);
    }
}
