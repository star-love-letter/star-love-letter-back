package cn.conststar.wall.service.adminTable;

import cn.conststar.wall.pojo.model.CommentDomain;
import cn.conststar.wall.pojo.model.TableDomain;

import java.util.List;

public interface AdminTableService {

    /**
     * 获取帖子列表
     *
     * @param pageIndex 页码
     * @param pageSize  每页的数量
     * @param rankName  排序字段
     * @param rankType  排序类型 true升序 false降序
     * @return 帖子列表
     */
    List<TableDomain> getTables(int pageIndex, int pageSize, String rankName, boolean rankType) throws Exception;

    /**
     * 获取帖子的数量
     *
     * @return 数量
     */
    int getTableCount() throws Exception;

    /**
     * 搜索帖子
     *
     * @param keyword   关键词
     * @param pageIndex 页码
     * @param pageSize  每页的数量
     * @param rankName  排序字段
     * @param rankType  排序类型 true升序 false降序
     * @return 帖子列表
     */
    List<TableDomain> getSearchTables(String keyword, int pageIndex, int pageSize, String rankName, boolean rankType) throws Exception;

    /**
     * 搜索帖子的数量
     *
     * @param keyword 关键词
     * @return 数量
     */
    int getSearchTableCount(String keyword) throws Exception;

    /**
     * 通过帖子id获取帖子
     *
     * @param tableId 帖子id
     * @return 帖子
     */
    TableDomain getTableById(int tableId) throws Exception;


    /**
     * 更新帖子
     *
     * @param table 帖子
     */
    void updateTable(TableDomain table) throws Exception;

    /**
     * 通过帖子id删除帖子
     *
     * @param tableId 帖子id
     */
    void deleteTable(int tableId) throws Exception;
}
