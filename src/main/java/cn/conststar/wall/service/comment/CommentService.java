package cn.conststar.wall.service.comment;

import cn.conststar.wall.pojo.dto.CommentDto;
import cn.conststar.wall.pojo.model.CommentDomain;

import java.util.List;

public interface CommentService {

    /**
     * 获取评论列表
     *
     * @param tableId   帖子id
     * @param pageIndex 页码
     * @param pageSize  每页的数量
     * @param rankName  排序字段
     * @param rankType  排序类型 true升序 false降序
     * @return 评论列表
     */
    List<CommentDto> getComments(int tableId, int pageIndex, int pageSize, String rankName, boolean rankType) throws Exception;

    /**
     * 获取帖子评论的数量
     *
     * @param tableId 帖子id
     * @return 数量
     */
    int getCount(int tableId) throws Exception;

    /**
     * 获取分页我的评论列表
     *
     * @param pageIndex 页码
     * @param pageSize  每页的数量
     * @param rankName  排序字段
     * @param rankType  排序类型 true升序 false降序
     * @return 评论列表
     */
    List<CommentDto> getMyComments(int pageIndex, int pageSize, String rankName, boolean rankType) throws Exception;

    /**
     * 获取我的评论的数量
     *
     * @return 数量
     */
    int getMyCount() throws Exception;

    /**
     * 搜索我的评论
     *
     * @param keyword   关键词
     * @param pageIndex 页码
     * @param pageSize  每页多少个
     * @param rankName  排序字段
     * @param rankType  排序类型 true升序 false降序
     * @return 评论列表
     */
    List<CommentDto> getMySearchComments(String keyword, int pageIndex, int pageSize, String rankName, boolean rankType) throws Exception;

    /**
     * 获取搜索我的评论的数量
     *
     * @param keyword 关键词
     * @return 数量
     */
    int getMySearchCount(String keyword) throws Exception;

    /**
     * 发布评论
     *
     * @param comment 评论
     */
    void addComment(CommentDomain comment) throws Exception;

    /**
     * 删除评论
     *
     * @param commentId 评论id
     */
    void deleteComment(int commentId) throws Exception;
}
