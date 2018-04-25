package com.bq.wms.admin.shiro.service;

import com.bq.wms.admin.shiro.pojo.LoginAccount;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 李帅伟
 * @date: 2018/4/25
 **/
@Service
public class LoginService {

    /** 由于重点不在数据库,这里需要使用数据库的地方全部用map代替 **/
    /** 用户信息 **/
    private static Map<String, LoginAccount> users = new HashMap<>();

    static {
        // 创建一个用户
        LoginAccount account = new LoginAccount();
        account.setLoginName("test");
        account.setPassword("123456");
        account.setEnabled(true);
        account.setExpired(false);

        // 角色添加
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");
        account.setRoles(roles);

        List<String> permissions = new ArrayList<>();
        permissions.add("query");
        permissions.add("delete");

        account.setPermissions(permissions);
        users.put(account.getLoginName(), account);

        // 创建一个用户
        LoginAccount admin = new LoginAccount();
        admin.setLoginName("admin");
        admin.setPassword("123456");
        admin.setEnabled(true);
        admin.setExpired(false);

        // 角色添加
        roles = new ArrayList<>();
        roles.add("ROLE_ADMIN");
        admin.setRoles(roles);

        permissions = new ArrayList<>();
        permissions.add("query");
        permissions.add("delete");

        admin.setPermissions(permissions);

        users.put("admin", admin);

    }

    /**
     * 通过用户名获取用户权限集合
     *
     * @param loginName
     *            用户名
     * @return 用户的权限集合
     */
    public List<String> getPermissionsByLoginName(String loginName) {

        if (users.containsKey(loginName)) {
            return users.get(loginName).getPermissions();
        }
        return new ArrayList<>();
    }

    /**
     * 通过用户名获取用户信息
     *
     * @param loginName
     *            用户名
     * @return 用户信息
     */
    public LoginAccount getLoginAccountByLoginName(String loginName) {
        if (users.containsKey(loginName)) {
            return users.get(loginName);
        }
        return null;

    }
}
