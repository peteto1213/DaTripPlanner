package com.ncl.team3.config;

import com.ncl.team3.mappers.UserMapper;
import com.ncl.team3.models.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;

import java.util.Optional;

/**
 * This class inherits from the AuthorizingRealm class of the Shiro framework.
 * Through this class is mainly for access to user authentication, authorization and access control
 * @author Lei Chen
 * @version 1.0
 * @StudentNumber: 200936497
 * @date 2022/04/06 13:44:14
 */
@Slf4j
public class MyRealm extends AuthorizingRealm {
    @Autowired
    UserMapper userMapper;
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info("Invoking the authorisation process。");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        User user = (User) principals.getPrimaryPrincipal();
        log.info("user:"+user.getStatus());
        info.addStringPermission("user:"+user.getStatus());
        return info;
    }
    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        log.info("Execute user authentication operations.");
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        Optional<User> result = userMapper.findById(userToken.getUsername());
        if (!result.isPresent()){
            return null;
        }
        User user = result.get();
        // parameter detail : entity of user, original password , salt value , realName;
        return new SimpleAuthenticationInfo(user,user.getPassword(), ByteSource.Util.bytes(user.getEmail()),getName());
    }
}
