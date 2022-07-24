package cn.conststar.wall.service.comment.impl;

import cn.conststar.wall.config.GlobalConfig;
import cn.conststar.wall.constant.StateCodeConstant;
import cn.conststar.wall.exception.BusinessException;
import cn.conststar.wall.dao.CommentDao;
import cn.conststar.wall.pojo.dto.CommentDto;
import cn.conststar.wall.pojo.dto.TableDto;
import cn.conststar.wall.constant.ResponseEnumConstant;
import cn.conststar.wall.pojo.model.CommentDomain;
import cn.conststar.wall.pojo.model.UserDomain;
import cn.conststar.wall.service.comment.CommentService;
import cn.conststar.wall.service.table.TableService;
import cn.conststar.wall.service.user.UserService;
import cn.conststar.wall.utils.EmailSend;
import cn.conststar.wall.utils.Tools;
import cn.conststar.wall.utils.TextUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private TableService tableService;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private UserService userService;

    @Override
    public List<CommentDto> getComments(int tableId, int pageIndex, int pageSize, String rankName, boolean rankType) throws Exception {
        if (pageIndex < 1 || pageSize < 0)
            throw new BusinessException("页数有误");

        int userId = -1;
        try {
            userId = userService.getUserBySubject().getId();
        } catch (Exception ex) {
            //允许未登录
        }

        int startIndex = (pageIndex - 1) * pageSize;
        return commentDao.getComments(tableId, startIndex, pageSize, userId, rankName, rankType);
    }

    @Override
    public int getCount(int tableId) throws Exception {
        int userId = -1;
        try {
            userId = userService.getUserBySubject().getId();
        } catch (Exception ex) {
            //允许未登录
        }

        return commentDao.getCount(tableId, userId);
    }

    @Override
    public List<CommentDto> getMyComments(int pageIndex, int pageSize, String rankName, boolean rankType) throws Exception {
        if (pageIndex < 1 || pageSize < 0)
            throw new BusinessException("页数有误");

        UserDomain user = userService.getUserBySubject();
        int startIndex = (pageIndex - 1) * pageSize;
        return commentDao.getMyComments(startIndex, pageSize, user.getId(), rankName, rankType);
    }

    @Override
    public int getMyCount() throws Exception {
        UserDomain user = userService.getUserBySubject();
        return commentDao.getMyCount(user.getId());
    }

    @Override
    public List<CommentDto> getMySearchComments(String keyword, int pageIndex, int pageSize, String rankName, boolean rankType) throws Exception {
        if (keyword.isEmpty())
            throw new BusinessException("搜索内容为空");

        if (pageIndex < 1 || pageSize < 0)
            throw new BusinessException("页数有误");


        UserDomain user = userService.getUserBySubject();
        int startIndex = (pageIndex - 1) * pageSize;
        return commentDao.getMySearchComments(keyword, startIndex, pageSize, user.getId(), rankName, rankType);
    }

    @Override
    public int getMySearchCount(String keyword) throws Exception {
        UserDomain user = userService.getUserBySubject();

        return commentDao.getMySearchCount(keyword, user.getId());
    }

    @Override
    public void addComment(CommentDomain comment) throws Exception {
        TableDto table = tableService.getTable(comment.getTableId());
        if (table == null)
            throw new BusinessException("帖子不存在");

        ObjectMapper mapper = new ObjectMapper();
        List<String> imageList = null;
        if (comment.getImages() != null && !comment.getImages().isEmpty())
            imageList = Arrays.asList(mapper.readValue(comment.getImages(), String[].class));

        if (imageList == null || imageList.isEmpty())
            comment.setImages(null);
        else if (imageList.size() > GlobalConfig.commentImageMaxNumber)
            throw new BusinessException("评论最多上传" + GlobalConfig.commentImageMaxNumber + "张图片");
        else
            table.setImages(mapper.writeValueAsString(imageList));

        //获取用户
        UserDomain user = userService.getUserBySubject();
        comment.setUserId(user.getId());

        //是否不需要审核
        int state = StateCodeConstant.COMMENT_NONE;
        if ((imageList != null && !imageList.isEmpty())
                || TextUtils.checkText(comment.getContent())) {
            state = StateCodeConstant.TABLE_WAIT;
        }
        comment.setState(state);


        int line = commentDao.addComment(comment);
        if (line != 1) {
            throw new BusinessException("数据库操作失败，数据库添加行数为" + line, ResponseEnumConstant.CODE_50002);
        }

        //发布成功后移动图片
        if (imageList != null)
            Tools.addDataImages(new HashSet<>(imageList));

        //评论通知
        EmailSend.sendNotifyComment(tableService.getTableDomain(table.getId()), comment, user);
    }

    @Override
    public void deleteComment(int commentId) throws Exception {
        UserDomain user = userService.getUserBySubject();

        commentDao.deleteComment(commentId, user.getId());
    }
}
