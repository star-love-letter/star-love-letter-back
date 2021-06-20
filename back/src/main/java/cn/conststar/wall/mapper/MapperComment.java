package cn.conststar.wall.mapper;

import cn.conststar.wall.pojo.PojoComment;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MapperComment {

    //获取帖子分页评论列表
    List<PojoComment> getCommentsLimit(@Param("pId") int pId,
                                       @Param("startIndex") int startIndex,
                                       @Param("pageSize") int pageSize) throws Exception;

    //添加帖子评论
    int addComment(int pId, String name, String content) throws Exception;

    //获取帖子评论总数量
    int getCount(@Param("pId") int pId) throws Exception;
}
