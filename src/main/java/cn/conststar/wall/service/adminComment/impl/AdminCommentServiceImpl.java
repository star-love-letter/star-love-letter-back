package cn.conststar.wall.service.adminComment.impl;

import cn.conststar.wall.config.GlobalConfig;
import cn.conststar.wall.dao.AdminCommentDao;
import cn.conststar.wall.exception.BusinessException;
import cn.conststar.wall.pojo.model.CommentDomain;
import cn.conststar.wall.pojo.model.UserDomain;
import cn.conststar.wall.service.adminComment.AdminCommentService;
import cn.conststar.wall.service.adminLog.AdminLogService;
import cn.conststar.wall.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class AdminCommentServiceImpl implements AdminCommentService {
    @Autowired
    private AdminCommentDao commentDao;

    @Autowired
    private UserService userService;

    @Autowired
    private AdminLogService adminLogService;

    // 获评论列表
    public List<CommentDomain> getComments(int pageIndex, int pageSize, String rankName, boolean rankType) throws Exception {
        if (pageIndex < 1 || pageSize < 0)
            throw new BusinessException("页数有误");

        int startIndex = (pageIndex - 1) * pageSize;
        return commentDao.getComments(startIndex, pageSize, rankName, rankType);
    }

    // 获取评论数量
    public int getCommentCount() throws Exception {
        return commentDao.getCommentCount();
    }

    // 获取评论
    public CommentDomain getCommentById(int commentId) throws Exception {
        return commentDao.getCommentById(commentId);
    }

    @Override
    public int getExamineCount() throws Exception {
        return commentDao.getExamineCount();
    }

    // 更新评论
    public void updateComment(CommentDomain comment) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        List<String> imageList = null;
        if (comment.getImages() != null && !comment.getImages().isEmpty())
            imageList = Arrays.asList(mapper.readValue(comment.getImages(), String[].class));

        if (imageList == null || imageList.isEmpty())
            comment.setImages(null);
        else if (imageList.size() > GlobalConfig.commentImageMaxNumber)
            throw new BusinessException("评论最多上传" + GlobalConfig.commentImageMaxNumber + "张图片");
        else
            comment.setImages(mapper.writeValueAsString(imageList));

        UserDomain admin = userService.getUserBySubject();
        adminLogService.addLog("更新评论", comment.toString(), admin.getId());

        commentDao.updateComment(comment);
    }

    // 删除评论
    public void deleteComment(int commentId) throws Exception {

        CommentDomain comment = getCommentById(commentId);
        UserDomain admin = userService.getUserBySubject();
        adminLogService.addLog("删除评论", comment.toString(), admin.getId());

        commentDao.deleteComment(commentId);
    }

    // 获取搜索评论列表
    @Override
    public List<CommentDomain> getSearchComments(String keyword, int pageIndex, int pageSize, String rankName, boolean rankType) throws Exception {
        if (pageIndex < 1 || pageSize < 0)
            throw new BusinessException("页数有误");

        int startIndex = (pageIndex - 1) * pageSize;
        return commentDao.getSearchComments(keyword, startIndex, pageSize, rankName, rankType);
    }

    // 获取搜索评论数量
    @Override
    public int getSearchCommentCount(String keyword) throws Exception {
        return commentDao.getSearchCommentCount(keyword);
    }

}
