package cn.conststar.wall.dao;

import cn.conststar.wall.pojo.model.ReportUserDomain;
import cn.conststar.wall.pojo.model.UserDomain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdminReportUserDao {
    List<UserDomain> getReportUsers(
            @Param("startIndex") int startIndex, @Param("pageSize") int pageSize,
            @Param("state") int state) throws Exception;

    int getReportUserCount(@Param("state") int state) throws Exception;

    List<UserDomain> getSearchReportUsers(
            @Param("keyword") String keyword,
            @Param("startIndex") int startIndex, @Param("pageSize") int pageSize,
            @Param("state") int state) throws Exception;

    int getSearchReportUserCount(
            @Param("keyword") String keyword, @Param("state") int state) throws Exception;

    List<ReportUserDomain> getReportByUser(@Param("userId") int userId) throws Exception;

    List<ReportUserDomain> getReportByReportUser(@Param("reportUserId")int reportUserId);

    void updateReportState(@Param("userId") int userId, @Param("state") int state) throws Exception;

}
