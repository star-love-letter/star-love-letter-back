package cn.conststar.wall.service.table;

import cn.conststar.wall.pojo.dto.TableDto;
import cn.conststar.wall.pojo.dto.UserInfoPublicDto;
import cn.conststar.wall.pojo.model.TableDomain;

import java.util.List;

public interface TableService {

    /**
     * 获取分页帖子列表
     *
     * @param pageIndex 页码
     * @param pageSize  每页的数量
     * @param rankName  排序字段
     * @param rankType  排序类型 true升序 false降序
     * @return 帖子列表
     */
    List<TableDto> getTables(int pageIndex, int pageSize, String rankName, boolean rankType) throws Exception;

    /**
     * 获取帖子的数量
     *
     * @return 数量
     */
    int getCount() throws Exception;

    /**
     * 搜索帖子
     *
     * @param keyword   关键词
     * @param pageIndex 页码
     * @param pageSize  每页多少个
     * @param rankName  排序字段
     * @param rankType  排序类型 true升序 false降序
     * @return 帖子列表
     */
    List<TableDto> getSearchTables(String keyword, int pageIndex, int pageSize, String rankName, boolean rankType) throws Exception;

    /**
     * 获取搜索帖子的数量
     *
     * @param keyword 关键词
     * @return 数量
     */
    int getSearchCount(String keyword) throws Exception;

    /**
     * 获取分页我的帖子列表
     *
     * @param pageIndex 页码
     * @param pageSize  每页的数量
     * @param rankName  排序字段
     * @param rankType  排序类型 true升序 false降序
     * @return 帖子列表
     */
    List<TableDto> getMyTables(int pageIndex, int pageSize, String rankName, boolean rankType) throws Exception;

    /**
     * 获取我的帖子的数量
     *
     * @return 数量
     */
    int getMyCount() throws Exception;

    /**
     * 搜索我的帖子
     *
     * @param keyword   关键词
     * @param pageIndex 页码
     * @param pageSize  每页多少个
     * @param rankName  排序字段
     * @param rankType  排序类型 true升序 false降序
     * @return 帖子列表
     */
    List<TableDto> getMySearchTables(String keyword, int pageIndex, int pageSize, String rankName, boolean rankType) throws Exception;

    /**
     * 获取搜索我的帖子的数量
     *
     * @param keyword 关键词
     * @return 数量
     */
    int getMySearchCount(String keyword) throws Exception;

    /**
     * 获取帖子
     *
     * @param id 帖子id
     * @return 帖子
     */
    TableDto getTable(int id) throws Exception;

    /**
     * 获取帖子实体
     *
     * @param id 帖子id
     * @return 帖子实体
     */
    TableDomain getTableDomain(int id) throws Exception;

    /**
     * 添加帖子
     *
     * @param table 帖子
     */
    void addTable(TableDomain table) throws Exception;

    /**
     * 点赞
     *
     * @param tableId 帖子id
     */
    void addSupport(int tableId) throws Exception;

    /**
     * 取消点赞
     *
     * @param tableId 帖子id
     */
    void deleteSupport(int tableId) throws Exception;


    /**
     * 删除帖子
     *
     * @param tableId 帖子id
     */
    void deleteTable(int tableId) throws Exception;
}
