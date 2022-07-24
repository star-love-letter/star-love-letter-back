package cn.conststar.wall.dao;

import cn.conststar.wall.pojo.model.UserDomain;
import cn.conststar.wall.pojo.dto.UserInfoPublicDto;
import cn.conststar.wall.pojo.dto.UserInfoDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserDao {
    //获取用户实体
    UserDomain getUserDomain(@Param("userStr") String userStr) throws Exception;

    //更新登录时间
    void updateLoginTime(@Param("userStr") String userStr) throws Exception;

    //通过微信登录用户
    UserDomain getUserByWeChat(@Param("openId") String openId) throws Exception;


    //通过邮箱注册用户
    int addUserByEmail(UserDomain user) throws Exception;

    //通过邮箱注册用户
    int addUserByWeChat(UserDomain user) throws Exception;

    //绑定微信
    void updateBindWeChat(@Param("userId") int userId, @Param("openId") String openId);

    //绑定邮箱
    void updateBindEmail(@Param("userId") int userId, @Param("email") String email);

    //获取公开用户信息
    UserInfoPublicDto getUserInfoPublic(@Param("id") int id) throws Exception;

    //获取用户信息
    UserInfoDto getUserInfo(@Param("userStr") String userStr) throws Exception;

    //通过帖子获取用户信息
    UserInfoPublicDto getUserInfoPublicByTable(@Param("tableId") int tableId, @Param("userId") int userId) throws Exception;

    //通过评论获取用户公开信息
    UserInfoPublicDto getUserInfoPublicByComment(@Param("commentId") int commentId) throws Exception;

    //通过邮箱修改密码
    void updatePasswordByEmail(@Param("email") String email, @Param("password") String password, @Param("salt") String salt) throws Exception;

    //修改头像
    void updateAvatar(@Param("userId") int userId, @Param("avatar") String avatar);
}
