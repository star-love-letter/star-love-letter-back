package cn.conststar.wall.dao;

import cn.conststar.wall.pojo.model.ReportTableDomain;
import cn.conststar.wall.pojo.model.TableDomain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdminReportTableDao {
    List<TableDomain> getReportTables(
            @Param("startIndex") int startIndex, @Param("pageSize") int pageSize,
            @Param("state") int state) throws Exception;

    int getReportTableCount(@Param("state") int state) throws Exception;

    List<TableDomain> getSearchReportTables(
            @Param("keyword") String keyword,
            @Param("startIndex") int startIndex, @Param("pageSize") int pageSize,
            @Param("state") int state) throws Exception;

    int getSearchReportTableCount(
            @Param("keyword") String keyword, @Param("state") int state) throws Exception;

    List<ReportTableDomain> getReportByTable(@Param("tableId") int tableId) throws Exception;

    List<ReportTableDomain> getReportByUser(@Param("userId") int userId) throws Exception;

    void updateReportState(@Param("tableId") int tableId, @Param("state") int state) throws Exception;
}
