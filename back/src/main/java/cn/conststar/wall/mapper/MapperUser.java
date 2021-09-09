package cn.conststar.wall.mapper;

import cn.conststar.wall.pojo.PojoUserPublic;
import cn.conststar.wall.pojo.PojoUser;
import org.apache.ibatis.annotations.Param;

public interface MapperUser {
    //获取用户
    PojoUser getUser(String token) throws Exception;

    //登录账号
    boolean login(@Param("email") String email, @Param("password") String password, @Param("token") String token) throws Exception;

    //退出账号
    boolean logout(String token) throws Exception;

    //获取公开用户信息
    PojoUserPublic getUserPublic(@Param("id") int id) throws Exception;

    //添加用户
    int addUser(@Param("email") String email, @Param("password") String password,
                @Param("name") String name, @Param("status") int status) throws Exception;

    //检查用户是否存在
    boolean findUser(@Param("email") String email);
}
