package cn.conststar.wall.dao;

import cn.conststar.wall.pojo.model.CommentDomain;
import cn.conststar.wall.pojo.model.ReportCommentDomain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdminReportCommentDao {
    List<CommentDomain> getReportComments(
            @Param("startIndex") int startIndex, @Param("pageSize") int pageSize,
            @Param("state") int state);

    int getReportCommentCount(@Param("state") int state);

    List<CommentDomain> getSearchReportComments(
            @Param("keyword") String keyword,
            @Param("startIndex") int startIndex, @Param("pageSize") int pageSize,
            @Param("state") int state);

    int getSearchReportCommentCount
            (@Param("keyword") String keyword, @Param("state") int state);

    List<ReportCommentDomain> getReportByComment(@Param("commentId") int commentId);

    List<ReportCommentDomain> getReportByReportUser(@Param("reportUserId") int reportUserId);

    void updateReportState(@Param("commentId") int commentId, @Param("state") int state);
}
