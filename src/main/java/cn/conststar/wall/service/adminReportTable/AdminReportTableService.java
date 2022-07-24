package cn.conststar.wall.service.adminReportTable;

import cn.conststar.wall.pojo.model.ReportTableDomain;
import cn.conststar.wall.pojo.model.TableDomain;

import java.util.List;

public interface AdminReportTableService {

    /**
     * 获取举报帖子列表
     *
     * @param pageIndex 页码
     * @param pageSize  每页的数量
     * @param state     举报的状态
     * @return 帖子列表
     */
    List<TableDomain> getReportTables(int pageIndex, int pageSize, int state) throws Exception;

    /**
     * 获取举报帖子的数量
     *
     * @param state 举报的状态
     * @return 数量
     */
    int getReportTableCount(int state) throws Exception;

    /**
     * 搜索举报帖子
     *
     * @param keyword   关键词
     * @param pageIndex 页码
     * @param pageSize  每页的数量
     * @param state     举报的状态
     * @return 帖子列表
     */
    List<TableDomain> getSearchReportTables(String keyword, int pageIndex, int pageSize, int state) throws Exception;

    /**
     * 搜索举报帖子的数量
     *
     * @param keyword 关键词
     * @param state   举报的状态
     * @return 数量
     */
    int getSearchReportTableCount(String keyword, int state) throws Exception;

    /**
     * 通过帖子id获取举报帖子
     *
     * @param tableId 帖子id
     * @return 举报帖子内容列表
     */
    List<ReportTableDomain> getReportByTable(int tableId) throws Exception;

    /**
     * 通过用户id获取举报帖子
     *
     * @param userId 用户id
     * @return 举报帖子内容列表
     */
    List<ReportTableDomain> getReportByUser(int userId) throws Exception;

    /**
     * 处理举报帖子
     *
     * @param tableId 帖子id
     * @param agree   是否同意举报
     */
    void handleReport(int tableId, boolean agree) throws Exception;
}
