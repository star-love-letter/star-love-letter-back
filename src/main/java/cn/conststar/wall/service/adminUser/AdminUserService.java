package cn.conststar.wall.service.adminUser;

import cn.conststar.wall.pojo.model.UserDomain;

import java.util.List;

public interface AdminUserService {

    /**
     * 获取用户列表
     *
     * @param pageIndex 页码
     * @param pageSize  每页的数量
     * @param rankName  排序字段
     * @param rankType  排序类型 true升序 false降序
     * @return 用户列表
     */
    List<UserDomain> getUsers(int pageIndex, int pageSize, String rankName, boolean rankType) throws Exception;

    /**
     * 获取用户的数量
     *
     * @return 数量
     */
    int getUserCount() throws Exception;

    /**
     * 搜索用户
     *
     * @param keyword   关键词
     * @param pageIndex 页码
     * @param pageSize  每页的数量
     * @param rankName  排序字段
     * @param rankType  排序类型 true升序 false降序
     * @return 用户列表
     */
    List<UserDomain> getSearchUsers(String keyword, int pageIndex, int pageSize, String rankName, boolean rankType) throws Exception;

    /**
     * 搜索用户的数量
     *
     * @param keyword 关键词
     * @return 数量
     */
    int getSearchUserCount(String keyword) throws Exception;

    /**
     * 通过用户id获取用户
     *
     * @param userId 用户id
     * @return 用户
     */
    UserDomain getUserById(int userId) throws Exception;


    /**
     * 获取待审核用户的数量
     *
     * @return 数量
     */
    int getExamineCount() throws Exception;

    /**
     * 通过用户id删除用户
     *
     * @param userId 用户id
     */
    void deleteUser(int userId) throws Exception;


    /**
     * 更新用户
     *
     * @param user 用户实体
     */
    void updateUser(UserDomain user) throws Exception;
}
