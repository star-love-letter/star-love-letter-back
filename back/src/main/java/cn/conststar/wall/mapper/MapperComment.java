package cn.conststar.wall.mapper;

import cn.conststar.wall.pojo.PojoComment;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MapperComment {

    //获取帖子分页评论列表
    List<PojoComment> getCommentsLimit(@Param("tableId") int tableId,
                                       @Param("startIndex") int startIndex,
                                       @Param("pageSize") int pageSize) throws Exception;

    //添加帖子评论
    int addComment(@Param("tableId") int tableId,
                   @Param("name") String name,
                   @Param("content") String content) throws Exception;

    //获取帖子评论总数量
    int getCount(@Param("tableId") int tableId) throws Exception;
}
