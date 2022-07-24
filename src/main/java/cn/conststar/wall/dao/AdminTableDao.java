package cn.conststar.wall.dao;

import cn.conststar.wall.pojo.model.TableDomain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdminTableDao {

    List<TableDomain> getTables(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize,
                                @Param("rankName")  String rankName, @Param("rankType")  boolean rankType) throws Exception;

    int getTableCount() throws Exception;

    List<TableDomain> getSearchTables(@Param("keyword") String keyword,
                                      @Param("startIndex") int startIndex, @Param("pageSize") int pageSize,
                                      @Param("rankName")  String rankName, @Param("rankType")  boolean rankType) throws Exception;

    int getSearchTableCount(@Param("keyword") String keyword) throws Exception;

    TableDomain getTableById(@Param("id") int id) throws Exception;

    void updateTable(TableDomain table) throws Exception;

    void deleteTable(@Param("id") int id) throws Exception;
}
