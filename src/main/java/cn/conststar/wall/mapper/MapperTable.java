package cn.conststar.wall.mapper;

import cn.conststar.wall.pojo.PojoUserPublic;
import cn.conststar.wall.pojo.PojoTable;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MapperTable {
    //获取分页帖子列表
    List<PojoTable> getTablesLimit(@Param("startIndex") int startIndex,
                                   @Param("pageSize") int pageSize,
                                   @Param("userId") int userId) throws Exception;

    //搜索帖子
    List<PojoTable> getSearchTablesLimit(@Param("keyword") String keyword,
                                         @Param("startIndex") int startIndex,
                                         @Param("pageSize") int pageSize,
                                         @Param("userId") int userId) throws Exception;


    //获取单个帖子内容
    PojoTable getTable(@Param("id") int id,
                       @Param("userId") int userId) throws Exception;

    //发布表白
    int addTable(@Param("userId") int userId,
                 @Param("anonymous") boolean anonymous,
                 @Param("sender") String sender,
                 @Param("senderSex") int senderSex,
                 @Param("recipient") String recipient,
                 @Param("recipientSex") int recipientSex,
                 @Param("content") String content,
                 @Param("images") String images,
                 @Param("notifyEmail") boolean notifyEmail,
                 @Param("status") int status) throws Exception;

    //点赞
    void addSupport(@Param("tableId") int tableId, @Param("userId") int userId) throws Exception;

    //取消点赞
    void removeSupport(@Param("tableId") int tableId, @Param("userId") int userId) throws Exception;

    //获取帖子总数量
    int getCount(@Param("userId") int userId) throws Exception;

    //获取搜索总数量
    int getSearchCount(@Param("keyword") String keyword,@Param("userId") int userId) throws Exception;

    //获取帖子的用户信息 （帖子必须是非匿名的）
    PojoUserPublic getUser(@Param("tableId") int tableId) throws Exception;

    //获取用户是否点过赞
    boolean isSupport(@Param("tableId") int tableId, @Param("userId") int userId) throws Exception;
}
