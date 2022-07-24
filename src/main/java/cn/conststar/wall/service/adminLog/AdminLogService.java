package cn.conststar.wall.service.adminLog;

import cn.conststar.wall.pojo.model.LogDomain;

import java.util.List;

public interface AdminLogService {

    /**
     * 添加日志
     *
     * @param action 操作
     * @param data   数据
     * @param userId 用户id
     */
    void addLog(String action, String data, int userId) throws Exception;

    /**
     * 获取日志列表
     *
     * @param pageIndex 页码
     * @param pageSize  每页的数量
     * @param rankName  排序字段
     * @param rankType  排序类型 true升序 false降序
     * @return 日志列表
     */
    List<LogDomain> getLogs(int pageIndex, int pageSize, String rankName, boolean rankType) throws Exception;

    /**
     * 获取日志的数量
     *
     * @return 数量
     */
    int getLogCount() throws Exception;

    /**
     * 搜索日志
     *
     * @param keyword   关键词
     * @param pageIndex 页码
     * @param pageSize  每页数量
     * @param rankName  排序字段
     * @param rankType  排序类型 true升序 false降序
     * @return 日志列表
     */
    List<LogDomain> getSearchLogs(String keyword, int pageIndex, int pageSize, String rankName, boolean rankType) throws Exception;

    /**
     * 搜索日志的数量
     *
     * @param keyword 关键词
     * @return 数量
     */
    int getSearchLogCount(String keyword) throws Exception;

    /**
     * 清空过期的日志
     */
    void clearLogExpire() throws Exception;
}
