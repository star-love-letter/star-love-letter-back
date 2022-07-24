package cn.conststar.wall.dao;

import cn.conststar.wall.pojo.dto.UserInfoPublicDto;
import cn.conststar.wall.pojo.dto.TableDto;
import cn.conststar.wall.pojo.model.TableDomain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TableDao {
    //获取分页帖子列表
    List<TableDto> getTables(
            @Param("startIndex") int startIndex, @Param("pageSize") int pageSize,
            @Param("userId") int userId,
            @Param("rankName") String rankName, @Param("rankType") boolean rankType) throws Exception;

    //搜索帖子
    List<TableDto> getSearchTables(
            @Param("keyword") String keyword,
            @Param("startIndex") int startIndex, @Param("pageSize") int pageSize,
            @Param("userId") int userId,
            @Param("rankName") String rankName, @Param("rankType") boolean rankType) throws Exception;


    //获取单个帖子内容
    TableDto getTable(
            @Param("id") int id,
            @Param("userId") int userId) throws Exception;

    //获取帖子实体
    TableDomain getTableDomain(@Param("id") int id) throws Exception;

    //发布表白
    int addTable(TableDomain table) throws Exception;

    //点赞
    void addSupport(
            @Param("tableId") int tableId,
            @Param("userId") int userId) throws Exception;

    //取消点赞
    void removeSupport(
            @Param("tableId") int tableId,
            @Param("userId") int userId) throws Exception;

    //获取帖子的数量
    int getCount(@Param("userId") int userId) throws Exception;

    //获取搜索帖子的数量
    int getSearchCount(
            @Param("keyword") String keyword,
            @Param("userId") int userId) throws Exception;

    //获取用户是否点过赞
    boolean isSupport(
            @Param("tableId") int tableId,
            @Param("userId") int userId) throws Exception;

    void deleteTable(
            @Param("tableId") int tableId,
            @Param("userId") int userId) throws Exception;

    List<TableDto> getMyTables(
            @Param("startIndex") int startIndex, @Param("pageSize") int pageSize,
            @Param("userId") int userId,
            @Param("rankName") String rankName, @Param("rankType") boolean rankType) throws Exception;

    int getMyCount(@Param("userId") int userId) throws Exception;

    List<TableDto> getMySearchTables(
            @Param("keyword") String keyword,
            @Param("startIndex") int startIndex, @Param("pageSize") int pageSize,
            @Param("userId") int userId,
            @Param("rankName") String rankName, @Param("rankType") boolean rankType) throws Exception;

    int getMySearchCount(
            @Param("keyword") String keyword,
            @Param("userId") int userId) throws Exception;
}
