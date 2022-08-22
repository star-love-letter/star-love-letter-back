package cn.conststar.wall.service.adminReportUser;

import cn.conststar.wall.pojo.model.ReportUserDomain;
import cn.conststar.wall.pojo.model.UserDomain;

import java.util.List;

public interface AdminReportUserService {

    /**
     * 获取举报用户列表
     *
     * @param pageIndex 页码
     * @param pageSize  每页的数量
     * @param state     举报的状态
     * @return 用户列表
     */
    List<UserDomain> getReportUsers(int pageIndex, int pageSize, int state) throws Exception;

    /**
     * 获取举报用户的数量
     *
     * @param state 举报的状态
     * @return 数量
     */
    int getReportUserCount(int state) throws Exception;

    /**
     * 搜索举报用户
     *
     * @param keyword   关键词
     * @param pageIndex 页码
     * @param pageSize  每页的数量
     * @param state     举报的状态
     * @return 用户列表
     */
    List<UserDomain> getSearchReportUsers(String keyword, int pageIndex, int pageSize, int state) throws Exception;

    /**
     * 搜索举报用户的数量
     *
     * @param keyword 关键词
     * @param state   举报的状态
     * @return 数量
     */
    int getSearchReportUserCount(String keyword, int state) throws Exception;

    /**
     * 通过用户id获取举报用户
     *
     * @param userId 用户id
     * @return 举报用户内容列表
     */
    List<ReportUserDomain> getReportByUser(int userId) throws Exception;


    /**
     * 通过举报者用户id获取举报用户
     *
     * @param reportUserId 举报者的用户id
     * @return 举报用户内容列表
     */
    List<ReportUserDomain> getReportByReportUser(int reportUserId);

    /**
     * 处理举报用户
     *
     * @param userId 用户id
     * @param agree   是否同意举报
     */
    void handleReport(int userId, boolean agree) throws Exception;
}
