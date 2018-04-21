package com.bq.wms.registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 服务注册中心
 * @author: 李帅伟
 * @date: 2018/4/21
 **/

@EnableEurekaServer
@SpringBootApplication
public class WmsRegistryApplication {

    public static void main(String[] args) {
        SpringApplication.run(WmsRegistryApplication.class, args);
    }
}
