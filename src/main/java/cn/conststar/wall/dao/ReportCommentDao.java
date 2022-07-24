package cn.conststar.wall.dao;

import cn.conststar.wall.pojo.model.ReportCommentDomain;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ReportCommentDao {

    void report(ReportCommentDomain reportComment) throws Exception;
}
