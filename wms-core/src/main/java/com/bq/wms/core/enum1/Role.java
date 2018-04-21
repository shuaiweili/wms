package com.bq.wms.core.enum1;

/**
 * 用户角色
 * @author: 李帅伟
 * @date: 2018/4/21
 **/
public enum Role {

    ADMIN("超级管理员"),
    COMMON("普通用户");

    String name;

    Role(String name) {
        this.name = name;
    }

}
