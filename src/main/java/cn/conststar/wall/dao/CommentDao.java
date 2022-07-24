package cn.conststar.wall.dao;

import cn.conststar.wall.pojo.dto.CommentDto;
import cn.conststar.wall.pojo.dto.UserInfoPublicDto;
import cn.conststar.wall.pojo.model.CommentDomain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentDao {

    //获取帖子分页评论列表
    List<CommentDto> getComments(
            @Param("tableId") int tableId,
            @Param("startIndex") int startIndex, @Param("pageSize") int pageSize,
            @Param("userId") int userId,
            @Param("rankName") String rankName, @Param("rankType") boolean rankType) throws Exception;

    //获取帖子评论总数量
    int getCount(
            @Param("tableId") int tableId,
            @Param("userId") int userId) throws Exception;

    List<CommentDto> getMyComments(
            @Param("startIndex") int startIndex, @Param("pageSize") int pageSize,
            @Param("userId") int userId,
            @Param("rankName") String rankName, @Param("rankType") boolean rankType) throws Exception;

    int getMyCount(@Param("userId") int userId) throws Exception;

    List<CommentDto> getMySearchComments(
            @Param("keyword") String keyword,
            @Param("startIndex") int startIndex, @Param("pageSize") int pageSize,
            @Param("userId") int userId,
            @Param("rankName") String rankName, @Param("rankType") boolean rankType) throws Exception;

    int getMySearchCount(
            @Param("keyword") String keyword,
            @Param("userId") int userId) throws Exception;

    //添加帖子评论
    int addComment(CommentDomain comment) throws Exception;

    void deleteComment(
            @Param("commentId") int commentId,
            @Param("userId") int userId) throws Exception;
}
