package com.bq.wms.core.model;

import lombok.Data;

import java.util.Date;

/**
 * 用户信息
 * @author: 李帅伟
 * @date: 2018/4/21
 **/
@Data
public class User {

    private long id;
    private String name;        //用户名
    private long telephone;     //手机号
    private String rule;        //角色
    private String mark;        //备注
    private Date createTime;    //创建时间
}
