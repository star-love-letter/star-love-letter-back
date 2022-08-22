package cn.conststar.wall.dao;

import cn.conststar.wall.pojo.model.ReportUserDomain;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ReportUserDao {

    void report(ReportUserDomain reportUser) throws Exception;
}
