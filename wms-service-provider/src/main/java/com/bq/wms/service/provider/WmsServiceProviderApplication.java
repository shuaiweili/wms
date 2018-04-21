package com.bq.wms.service.provider;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 服务提供者
 * @author: 李帅伟
 * @date: 2018/4/21
 **/

@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.bq.wms.dao.mapper")
public class WmsServiceProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(WmsServiceProviderApplication.class, args);
    }
}
