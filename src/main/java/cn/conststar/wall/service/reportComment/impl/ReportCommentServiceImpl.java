package cn.conststar.wall.service.reportComment.impl;

import cn.conststar.wall.dao.ReportCommentDao;
import cn.conststar.wall.pojo.model.ReportCommentDomain;
import cn.conststar.wall.pojo.model.UserDomain;
import cn.conststar.wall.service.reportComment.ReportCommentService;
import cn.conststar.wall.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportCommentServiceImpl implements ReportCommentService {

    @Autowired
    private ReportCommentDao reportCommentDao;

    @Autowired
    private UserService userService;


    @Override
    public void report(ReportCommentDomain reportComment) throws Exception {
        UserDomain user = userService.getUserBySubject();
        reportComment.setUserId(user.getId());

        reportCommentDao.report(reportComment);
    }
}
