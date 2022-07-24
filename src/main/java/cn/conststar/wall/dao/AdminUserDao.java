package cn.conststar.wall.dao;

import cn.conststar.wall.pojo.model.UserDomain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdminUserDao {

    List<UserDomain> getUsers(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize,
                              @Param("rankName") String rankName, @Param("rankType") boolean rankType) throws Exception;

    int getUserCount() throws Exception;

    List<UserDomain> getSearchUsers(@Param("keyword") String keyword,
                                    @Param("startIndex") int startIndex, @Param("pageSize") int pageSize,
                                    @Param("rankName") String rankName, @Param("rankType") boolean rankType) throws Exception;

    int getSearchUserCount(@Param("keyword") String keyword) throws Exception;

    UserDomain getUserById(@Param("id") int id) throws Exception;

    void updateUser(UserDomain user) throws Exception;

    void deleteUser(@Param("id") int id) throws Exception;
}
