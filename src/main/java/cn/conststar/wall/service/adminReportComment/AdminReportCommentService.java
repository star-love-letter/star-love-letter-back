package cn.conststar.wall.service.adminReportComment;

import cn.conststar.wall.pojo.model.CommentDomain;
import cn.conststar.wall.pojo.model.ReportCommentDomain;

import java.util.List;

public interface AdminReportCommentService {
    /**
     * 获取举报评论列表
     *
     * @param pageIndex 页码
     * @param pageSize  每页的数量
     * @param state     举报的状态
     * @return 评论列表
     */
    List<CommentDomain> getReportComments(int pageIndex, int pageSize, int state);

    /**
     * 获取举报评论的数量
     *
     * @param state 举报的状态
     * @return 数量
     */
    int getReportCommentCount(int state);

    /**
     * 搜索举报评论
     *
     * @param keyword   关键词
     * @param pageIndex 页码
     * @param pageSize  每页的数量
     * @param state     举报的状态
     * @return 评论列表
     */
    List<CommentDomain> getSearchReportComments(String keyword, int pageIndex, int pageSize, int state);

    /**
     * 搜索举报评论的数量
     *
     * @param keyword 关键词
     * @param state   举报的状态
     * @return 数量
     */
    int getSearchReportCommentCount(String keyword, int state);

    /**
     * 通过评论id获取举报评论
     *
     * @param commentId 评论id
     * @return 举报评论内容列表
     */
    List<ReportCommentDomain> getReportByComment(int commentId);

    /**
     * 通过举报者用户id获取举报评论
     *
     * @param reportUserId 举报者的用户id
     * @return 举报评论内容列表
     */
    List<ReportCommentDomain> getReportByReportUser(int reportUserId);

    /**
     * 处理举报评论
     *
     * @param commentId 评论id
     * @param agree     是否同意举报
     */
    void handleReport(int commentId, boolean agree) throws Exception;
}
