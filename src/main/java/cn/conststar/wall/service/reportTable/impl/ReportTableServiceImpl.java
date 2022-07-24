package cn.conststar.wall.service.reportTable.impl;

import cn.conststar.wall.dao.ReportTableDao;
import cn.conststar.wall.pojo.model.ReportTableDomain;
import cn.conststar.wall.pojo.model.UserDomain;
import cn.conststar.wall.service.reportTable.ReportTableService;
import cn.conststar.wall.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportTableServiceImpl implements ReportTableService {

    @Autowired
    private ReportTableDao reportTableDao;

    @Autowired
    private UserService userService;

    @Override
    public void report(ReportTableDomain reportTable) throws Exception {
        UserDomain user = userService.getUserBySubject();
        reportTable.setUserId(user.getId());

        reportTableDao.report(reportTable);
    }
}
