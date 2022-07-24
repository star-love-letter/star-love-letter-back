package cn.conststar.wall.config;

import cn.conststar.wall.constant.StateCodeConstant;
import cn.conststar.wall.pojo.model.UserDomain;
import cn.conststar.wall.service.user.impl.UserServiceImpl;
import cn.conststar.wall.utils.HashUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserServiceImpl serviceUser;

    //  授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        //设置权限
        Subject subject = SecurityUtils.getSubject();
        UserDomain user = (UserDomain) subject.getPrincipal();

        info.addRole(user.getRoleName());
        return info;
    }

    // 用户认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        UserDomain user = null;
        try {
            user = serviceUser.getUserDomain(userToken.getUsername());
        } catch (Exception e) {
        }

        if (user == null) {
            throw new UnknownAccountException(); // 没找到帐号
        } else if (user.getState() == StateCodeConstant.USER_LOCK) {
            throw new LockedAccountException(); // 帐号被锁定
        }
        return new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(user.getSalt()), getName());
    }

    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName(HashUtils.HASH_ALGORITHM_NAME);
        matcher.setHashIterations(HashUtils.HASH_ITERATIONS);
        super.setCredentialsMatcher(matcher);
    }
}
