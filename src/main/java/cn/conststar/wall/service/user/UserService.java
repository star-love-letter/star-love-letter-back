package cn.conststar.wall.service.user;

import cn.conststar.wall.pojo.dto.UserInfoDto;
import cn.conststar.wall.pojo.dto.UserInfoPublicDto;
import cn.conststar.wall.pojo.model.UserDomain;

public interface UserService {

    /**
     * 在本地读取用户实体 同时验证用户是否登录
     *
     * @return 用户实体
     * @throws Exception 未登录抛出异常
     */
    UserDomain getUserBySubject() throws Exception;

    /**
     * 在本地读取用户信息
     *
     * @return 用户信息
     * @throws Exception 未登录抛出异常
     */
    UserInfoDto getUserInfoBySubject() throws Exception;

    /**
     * 通过id或者邮箱获取用户实体
     *
     * @param userStr id或者邮箱
     * @return 用户实体
     */
    UserDomain getUserDomain(String userStr) throws Exception;

    /**
     * 登录微信用户
     *
     * @param code 获取的code
     * @return
     */
    UserInfoDto loginByWeChatCode(String code) throws Exception;

    /**
     * 获取用户公开信息
     *
     * @param id 用户id
     * @return 用户公开信息
     */
    UserInfoPublicDto getUserInfoPublic(int id) throws Exception;

    /**
     * 登录用户
     *
     * @param userStr  id或者邮箱
     * @param password 密码
     * @return 用户信息
     */
    UserInfoDto login(String userStr, String password) throws Exception;

    /**
     * 退出登录
     */
    void logout() throws Exception;


    /**
     * 通过邮箱验证码添加用户
     *
     * @param user      用户
     * @param emailCode 邮箱验证码
     */
    void addUserByEmailCode(UserDomain user, String emailCode) throws Exception;


    /**
     * 通过微信添加用户
     *
     * @param user 用户
     */
    void addUserByWeChat(UserDomain user) throws Exception;

    /**
     * 通过微信Code添加用户
     *
     * @param user 用户
     * @param code 微信code
     */
    void addUserByWeChatCode(UserDomain user, String code) throws Exception;

    /**
     * 绑定新邮箱
     *
     * @param email     绑定的新邮箱
     * @param emailCode 邮箱验证码
     */
    void updateBindEmailCode(String email, String emailCode) throws Exception;

    /**
     * 绑定新微信
     *
     * @param code 微信code
     */
    void updateBindWeChatByCode(String code) throws Exception;

    /**
     * 是否通过微信注册过
     *
     * @param code 微信code
     * @return 是否
     */
    boolean isFindUserByWeChatCode(String code) throws Exception;

    /**
     * 通过帖子获取用户公开信息
     *
     * @param tableId 帖子id
     * @return 用户公开信息
     */
    UserInfoPublicDto getUserInfoPublicByTable(int tableId) throws Exception;

    /**
     * 通过评论获取用户公开信息
     *
     * @param commentId 评论id
     * @return 用户公开信息
     */
    UserInfoPublicDto getUserInfoPublicByComment(int commentId) throws Exception;

    /**
     * 获取旋转验证码
     *
     * @param email 邮箱
     */
    String getRotationVerifyCode(String email) throws Exception;

    /**
     * 获取邮箱验证码
     *
     * @param email 邮箱
     * @param angle 旋转验证码的角度
     */
    void getEmailVerifyCode(String email, int angle) throws Exception;


    /**
     * 通过邮箱验证码修改密码
     *
     * @param email     邮箱
     * @param password  新密码
     * @param emailCode 邮箱验证码
     */
    void updatePasswordByEmailCode(String email, String password, String emailCode) throws Exception;

    /**
     * 修改头像
     *
     * @param avatar 头像
     */
    void updateAvatar(String avatar) throws Exception;
}
