package com.ncl.team3.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *This class configures and sets up configuration information about Shiro.
 * Firstly it defines the access paths that need to be filtered and intercepted by Shiro, such as user orders and user information access,
 * which are interfaces that require personal information to be verified before they can be accessed.
 * The second defines the dependencies that need to be taken over by SpringBoot and automatically injected into Shiro.
 * For example,DefaultWebSecurityManager and HashedCredentialsMatcher.
 * @author Lei Chen
 * @version 1.0
 * @StudentNumber: 200936497
 * @date 2022/04/06 13:39:54
 */
@Configuration
public class ShiroConfig {

    @Bean(name = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("manager") DefaultWebSecurityManager manager){
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        //Setup Security Manager
        factoryBean.setSecurityManager(manager);
        //添加Shiro内置过滤器
        //Add Shiro's built-in filters
        Map<String,String> filterMap = new LinkedHashMap<>();

        ////添加拦截路径和需要的权限
        filterMap.put("/order/*","perms[user:active]");
        filterMap.put("/tripPlan/*","perms[user:active]");
        filterMap.put("/user/editUserInfo","perms[user:active]");
        filterMap.put("/user/changePasswordByOldPassword","perms[user:active]");
        filterMap.put("/user/getUserInfo","perms[user:active]");
        factoryBean.setFilterChainDefinitionMap(filterMap);

        return factoryBean;
    }


    @Bean(name = "manager")
    public DefaultWebSecurityManager manager(@Qualifier("myRealm") MyRealm myRealm){
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(myRealm);
        return manager;
    }

    @Bean(name = "myRealm")
    public MyRealm myRealm(){
        MyRealm myShiroRealm = new MyRealm();
        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myShiroRealm;
    }

    @Bean(name = "hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        // 使用md5 算法进行加密
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        // 设置散列次数： 意为加密几次
        hashedCredentialsMatcher.setHashIterations(1024);
        return hashedCredentialsMatcher;
    }




}
