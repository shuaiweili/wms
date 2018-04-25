package com.bq.wms.admin.config.shiro;

import com.bq.wms.admin.config.shiro.cache.RedisCacheManager;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro权限管理配置
 * @author: 李帅伟
 * @date: 2018/4/25
 **/
@Configuration
public class ShiroConfig {

    /**
     * ehcache缓存方案
     */
    @Bean
    public CacheManager shiroEhCacheManager() {
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
        return cacheManager;
    }

    /**
     * redis缓存方案
     */
    @Bean
    public CacheManager shiroRedisCacheManager() {
        return new RedisCacheManager();
    }

    /****
     * 自定义Real
     */
    @Bean
    public JDBCShiroRealm jdbcShiroRealm() {
        JDBCShiroRealm realm = new JDBCShiroRealm();
        // 根据情况使用缓存器
//        realm.setCacheManager(shiroRedisCacheManager());//shiroEhCacheManager()
        return realm;
    }

    /***
     * 安全管理配置
     */
    @Bean
    public SecurityManager defaultWebSecurityManager() {
        // DefaultSecurityManager defaultSecurityManager = new
        // DefaultSecurityManager();
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();// 注意:！！！初始化成这个将会报错java.lang.IllegalArgumentException:
        // SessionContext must be an HTTP compatible
        // implementation.：模块化本地测试shiro的一些总结
        // 配置
        securityManager.setRealm(jdbcShiroRealm());
        // 注意这里必须配置securityManager
        SecurityUtils.setSecurityManager(securityManager);
        // 根据情况选择缓存器
        securityManager.setCacheManager(shiroEhCacheManager());//shiroEhCacheManager()

//        securityManager.setSessionManager();
//        securityManager.setRememberMeManager();
        return securityManager;
    }

    /**
     * 配置shiro的拦截器链工厂
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        // 配置安全管理(必须)
        filterFactoryBean.setSecurityManager(defaultWebSecurityManager());
        // 配置登陆的地址
        filterFactoryBean.setLoginUrl("/userNoLogin.do");// 未登录时候跳转URL,如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        filterFactoryBean.setSuccessUrl("/welcome.do");// 成功后欢迎页面
        filterFactoryBean.setUnauthorizedUrl("/403.do");// 未认证页面

        // 配置拦截地址和拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();// 必须使用LinkedHashMap,因为拦截有先后顺序
        // authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问

        filterChainDefinitionMap.put("/userNoLogin.do*", "anon");// 未登录跳转页面不设权限认证
        filterChainDefinitionMap.put("/login.do*", "anon");// 登录接口不设置权限认真
        filterChainDefinitionMap.put("/logout.do*", "anon");// 登出不需要认证

        // 以下配置同样可以通过注解
        // @RequiresPermissions("user:edit")来配置访问权限和角色注解@RequiresRoles(value={"ROLE_USER"})方式定义
        // 权限配置示例,这里的配置理论来自数据库查询
        filterChainDefinitionMap.put("/user/**", "roles[ROLE_USER],perms[query]");// /user/下面的需要ROLE_USER角色或者query权限才能访问
        filterChainDefinitionMap.put("/admin/**", "roles[ROLE_ADMIN]");// /admin/下面的所有需要ROLE_ADMIN的角色才能访问

        // 剩下的其他资源地址全部需要用户认证后才能访问
        filterChainDefinitionMap.put("/**", "anon");
//        filterChainDefinitionMap.put("/**", "authc");
        filterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        // 全部配置
        // anon org.apache.shiro.web.filter.authc.AnonymousFilter 匿名访问
        //
        // authc org.apache.shiro.web.filter.authc.FormAuthenticationFilter
        // 需要登录,不需要权限和角色可访问
        //
        // authcBasic
        // org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter
        //
        // perms
        // org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter
        // 需要给定的权限值才能访问
        //
        // port org.apache.shiro.web.filter.authz.PortFilter
        //
        // rest org.apache.shiro.web.filter.authz.HttpMethodPermissionFilter
        //
        // roles org.apache.shiro.web.filter.authz.RolesAuthorizationFilter
        // 需要给定的角色才能访问
        //
        // ssl org.apache.shiro.web.filter.authz.SslFilter
        //
        // user org.apache.shiro.web.filter.authc.UserFilter
        //
        // logout org.apache.shiro.web.filter.authc.LogoutFilter
        return filterFactoryBean;
    }
}
