package cn.conststar.wall.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AdminClearDao {

    void clearReportTable(@Param("time") long time);

    void clearReportComment(@Param("time") long time);
}
