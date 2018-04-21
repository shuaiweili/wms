package com.bq.wms.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * 后台管理服务
 * @author: 李帅伟
 * @date: 2018/4/21
 **/
@EnableZuulProxy
@EnableFeignClients
@SpringBootApplication
public class WmsAdminApplication {


    public static void main(String[] args) {
        SpringApplication.run(WmsAdminApplication.class, args);
    }

}
