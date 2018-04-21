package com.bq.wms.admin.handler;

import com.bq.wms.core.model.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 声明式服务调用
 * @author: 李帅伟
 * @date: 2018/4/21
 **/
@FeignClient("${service.provider.name}")
public interface UserHandler {

    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    long add(User user);

    @RequestMapping("/user/{id}")
    ResponseEntity<User> findById(@PathVariable("id") long id);

    @RequestMapping("/user/findAll")
    ResponseEntity<List<User>> findAll();
}
