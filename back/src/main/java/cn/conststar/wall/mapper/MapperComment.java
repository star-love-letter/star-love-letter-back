package cn.conststar.wall.mapper;

import cn.conststar.wall.pojo.PojoComment;
import cn.conststar.wall.pojo.PojoUserPublic;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MapperComment {

    //获取帖子分页评论列表
    List<PojoComment> getCommentsLimit(@Param("tableId") int tableId,
                                       @Param("startIndex") int startIndex,
                                       @Param("pageSize") int pageSize,
                                       @Param("userId") int userId) throws Exception;


    //添加帖子评论
    int addComment(@Param("tableId") int tableId,
                   @Param("userId") int userId,
                   @Param("name") String name,
                   @Param("anonymous") boolean anonymous,
                   @Param("content") String content,
                   @Param("images") String images,
                   @Param("status") int status) throws Exception;

    //获取帖子评论总数量
    int getCount(@Param("tableId") int tableId, @Param("userId") int userId) throws Exception;

    //获取评论的用户信息 （评论必须是非匿名的）
    PojoUserPublic getUser(@Param("commentId") int commentId) throws Exception;
}
