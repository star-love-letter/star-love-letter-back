package cn.conststar.wall.service.adminComment;

import cn.conststar.wall.pojo.model.CommentDomain;

import java.util.List;

public interface AdminCommentService {

    /**
     * 获取评论列表
     *
     * @param pageIndex 页码
     * @param pageSize  每页的数量
     * @param rankName  排序字段
     * @param rankType  排序类型 true升序 false降序
     * @return 评论列表
     */
    List<CommentDomain> getComments(int pageIndex, int pageSize, String rankName, boolean rankType) throws Exception;

    /**
     * 获取评论的数量
     *
     * @return 数量
     */
    int getCommentCount() throws Exception;


    /**
     * 搜索评论
     *
     * @param keyword   关键词
     * @param pageIndex 页码
     * @param pageSize  每页的数量
     * @param rankName  排序字段
     * @param rankType  排序类型 true升序 false降序
     * @return 评论列表
     */
    List<CommentDomain> getSearchComments(String keyword, int pageIndex, int pageSize, String rankName, boolean rankType) throws Exception;

    /**
     * 获取搜索评论的数量
     *
     * @param keyword 关键词
     * @return 数量
     */
    int getSearchCommentCount(String keyword) throws Exception;

    /**
     * 通过评论id获取评论
     *
     * @param commentId 评论id
     * @return 评论
     */
    CommentDomain getCommentById(int commentId) throws Exception;

    /**
     * 获取待审核评论的数量
     *
     * @return 数量
     */
    int getExamineCount() throws Exception;

    /**
     * 更新评论
     *
     * @param comment 评论
     */
    void updateComment(CommentDomain comment) throws Exception;

    /**
     * 删除评论
     *
     * @param commentId 评论id
     */
    void deleteComment(int commentId) throws Exception;

}
