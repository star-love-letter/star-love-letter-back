package cn.conststar.wall.mapper;

import cn.conststar.wall.pojo.PojoPost;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MapperPost {
    //获取分页帖子列表
    List<PojoPost> getPostsLimit(@Param("startIndex") int startIndex,
                                 @Param("pageSize") int pageSize) throws Exception;

    //搜索帖子
    List<PojoPost> getSearchPostsLimit(@Param("keyword") String keyword,
                                       @Param("startIndex") int startIndex,
                                       @Param("pageSize") int pageSize) throws Exception;

    //获取单个帖子内容
    PojoPost getPost(@Param("id") int id) throws Exception;

    //发布表白
    int addPost(@Param("sender") String sender,
                @Param("senderSex") int senderSex,
                @Param("recipient") String recipient,
                @Param("recipientSex") int recipientSex,
                @Param("content") String content) throws Exception;

    //点赞
    void incrementThumbsUp(@Param("id") int id) throws Exception;

    //取消点赞
    void incrementUnThumbsUp(@Param("id") int id) throws Exception;

    //获取帖子总数量
    int getCount() throws Exception;

    //获取搜索总数量
    int getSearchCount(@Param("keyword") String keyword) throws Exception;
}
