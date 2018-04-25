package com.bq.wms.admin.shiro.pojo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author: 李帅伟
 * @date: 2018/4/25
 **/
@Data
public class LoginAccount {

    /** 用户名 */
    String loginName;
    List<String> roles;// 测试用
    List<String> permissions;// 测试用直接放用户登录对象里面
    /** 用户密码 **/
    String password;
    boolean enabled;
    Date createDate;
    boolean isExpired;
}
