package com.bq.wms.admin.config.shiro;

import com.bq.wms.admin.shiro.pojo.LoginAccount;
import com.bq.wms.admin.shiro.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: 李帅伟
 * @date: 2018/4/25
 **/

@Component("JDBCShiroRealm")
@Slf4j
public class JDBCShiroRealm extends AuthorizingRealm {



    /*** 用户业务处理类,用来查询数据库中用户相关信息 ***/
    @Autowired
    private LoginService loginService;

    /***
     * 获取用户授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("##################执行Shiro权限认证##################");
        // 获取用户名
        String loginName = (String) principalCollection.fromRealm(getName()).iterator().next();
        // 判断用户名是否存在
        if (StringUtils.isEmpty(loginName)) {
            return null;
        }
        // 查询登录用户信息
        LoginAccount account = loginService.getLoginAccountByLoginName(loginName);
        if (account == null) {
            log.warn("用户[" + loginName + "]信息不存在");
            return null;
        }
        // 创建一个授权对象
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        // 进行权限设置
        List<String> permissions = account.getPermissions();
        if (permissions != null && !permissions.isEmpty()) {
            info.addStringPermissions(permissions);
        }
        // 角色设置
        List<String> roles = account.getRoles();
        if (roles != null) {
            info.addRoles(roles);
        }

        return info;
    }

    /**
     * 获取用户认证信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        log.debug("##################执行Shiro登陆认证##################");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        // 通过表单接收的用户名
        String loginName = token.getUsername();
        if (loginName != null && !"".equals(loginName)) {
            // 模拟数据库查询用户信息
            LoginAccount account = loginService.getLoginAccountByLoginName(loginName);

            if (account != null) {
                // 登陆的主要信息: 可以是一个实体类的对象, 但该实体类的对象一定是根据 token 的 username 查询得到的.
                Object principal = token.getPrincipal();
                // 创建shiro的用户认证对象
                // 注意该对象的密码将会传递至后续步骤与前面登陆的subject的密码进行比对。
                SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(principal,
                        account.getPassword(), getName());

                return authenticationInfo;
            }
        }
        return null;
    }
}
