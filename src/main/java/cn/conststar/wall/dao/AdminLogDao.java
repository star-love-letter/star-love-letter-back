package cn.conststar.wall.dao;

import cn.conststar.wall.pojo.model.LogDomain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdminLogDao {

    //获取全部日志
    List<LogDomain> all() throws Exception;


    void addLog(LogDomain log);


    List<LogDomain> getLogs(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize,
                             @Param("rankName") String rankName, @Param("rankType") boolean rankType) throws Exception;

    int getLogCount() throws Exception;

    List<LogDomain> getSearchLogs(@Param("keyword") String keyword,
                                   @Param("startIndex") int startIndex, @Param("pageSize") int pageSize,
                                   @Param("rankName") String rankName, @Param("rankType") boolean rankType) throws Exception;

    int getSearchLogCount(@Param("keyword") String keyword) throws Exception;

    // 清空多余的日志
    void clearLog(@Param("logMaxNumber") int logMaxNumber) throws Exception;

}
