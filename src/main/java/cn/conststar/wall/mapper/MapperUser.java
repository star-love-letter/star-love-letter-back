package cn.conststar.wall.mapper;

import cn.conststar.wall.pojo.PojoUserPublic;
import cn.conststar.wall.pojo.PojoUser;
import org.apache.ibatis.annotations.Param;

public interface MapperUser {
    //获取用户
    PojoUser getUser(String token) throws Exception;

    //登录用户  通过 id || 邮箱
    boolean login(@Param("id") String id, @Param("password") String password, @Param("token") String token) throws Exception;

    //通过微信登录用户
    boolean loginByWeChat(@Param("openId") String openId, @Param("token") String token) throws Exception;

    //退出用户
    boolean logout(String token) throws Exception;

    //通过邮箱注册用户
    int addUserByEmail(@Param("email") String email, @Param("password") String password,
                @Param("name") String name, @Param("status") int status) throws Exception;

    //通过邮箱注册用户
    int addUserByWeChat(@Param("openId") String openId, @Param("password") String password,
                       @Param("name") String name, @Param("status") int status) throws Exception;

    //绑定微信
    void bindWeChat(@Param("userId") int userId,@Param("openId") String openId);

    //绑定邮箱
    void bindEmail(@Param("userId") int userId,@Param("email") String email);

    //查找用户  通过 id || 邮箱
    PojoUser findUser(@Param("id") String id);

    //通过邮箱查找用户
    PojoUser findUserByEmail(@Param("email") String email);

    //通过微信查找用户
    PojoUser findUserByWeChat(@Param("openId") String openId);

    //获取公开用户信息
    PojoUserPublic getUserPublic(@Param("id") int id) throws Exception;
}
