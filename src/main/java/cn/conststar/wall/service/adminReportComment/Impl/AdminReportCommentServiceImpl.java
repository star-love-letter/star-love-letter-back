package cn.conststar.wall.service.adminReportComment.Impl;

import cn.conststar.wall.constant.StateCodeConstant;
import cn.conststar.wall.dao.AdminReportCommentDao;
import cn.conststar.wall.exception.BusinessException;
import cn.conststar.wall.pojo.model.CommentDomain;
import cn.conststar.wall.pojo.model.ReportCommentDomain;
import cn.conststar.wall.pojo.model.CommentDomain;
import cn.conststar.wall.service.adminComment.AdminCommentService;
import cn.conststar.wall.service.adminReportComment.AdminReportCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminReportCommentServiceImpl implements AdminReportCommentService {

    @Autowired
    private AdminReportCommentDao adminReportCommentDao;

    @Autowired
    private AdminCommentService commentService;

    @Override
    public List<CommentDomain> getReportComments(int pageIndex, int pageSize, int state) {
        if (pageIndex < 1 || pageSize < 0)
            throw new BusinessException("页数有误");

        int startIndex = (pageIndex - 1) * pageSize;
        return adminReportCommentDao.getReportComments(startIndex, pageSize, state);
    }

    @Override
    public int getReportCommentCount(int state) {
        return adminReportCommentDao.getReportCommentCount(state);
    }

    @Override
    public List<CommentDomain> getSearchReportComments(String keyword, int pageIndex, int pageSize, int state) {
        if (pageIndex < 1 || pageSize < 0)
            throw new BusinessException("页数有误");

        int startIndex = (pageIndex - 1) * pageSize;
        return adminReportCommentDao.getSearchReportComments(keyword, startIndex, pageSize, state);
    }

    @Override
    public int getSearchReportCommentCount(String keyword, int state) {
        return adminReportCommentDao.getSearchReportCommentCount(keyword, state);
    }

    @Override
    public List<ReportCommentDomain> getReportByComment(int commentId) {
        return adminReportCommentDao.getReportByComment(commentId);
    }

    @Override
    public List<ReportCommentDomain> getReportByUser(int userId) {
        return adminReportCommentDao.getReportByUser(userId);
    }

    @Override
    public void handleReport(int commentId, boolean agree) throws Exception {
        // 同意举报
        if (agree) {
            // 封禁帖子
            CommentDomain comment = commentService.getCommentById(commentId);
            comment.setState(StateCodeConstant.COMMENT_LOCK);
            commentService.updateComment(comment);

            // 处理举报
            adminReportCommentDao.updateReportState(commentId, StateCodeConstant.REPORT_SUCCESS);
        }
        // 拒绝举报
        else {
            adminReportCommentDao.updateReportState(commentId, StateCodeConstant.REPORT_FAILED);
        }
    }
}
