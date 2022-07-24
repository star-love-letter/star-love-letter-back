package cn.conststar.wall.dao;

import cn.conststar.wall.pojo.model.CommentDomain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdminCommentDao {

    List<CommentDomain> getComments(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize,
                                    @Param("rankName") String rankName, @Param("rankType") boolean rankType) throws Exception;

    int getCommentCount() throws Exception;

    List<CommentDomain> getSearchComments(@Param("keyword") String keyword,
                                          @Param("startIndex") int startIndex, @Param("pageSize") int pageSize,
                                          @Param("rankName") String rankName, @Param("rankType") boolean rankType) throws Exception;

    int getSearchCommentCount(@Param("keyword") String keyword) throws Exception;

    CommentDomain getCommentById(@Param("id") int id) throws Exception;

    void updateComment(CommentDomain comment) throws Exception;

    void deleteComment(@Param("id") int id) throws Exception;
}
