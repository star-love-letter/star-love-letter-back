package cn.conststar.wall.dao;

import cn.conststar.wall.pojo.model.ReportTableDomain;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ReportTableDao {

    void report(ReportTableDomain reportTable) throws Exception;
}
