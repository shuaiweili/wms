package com.bq.wms.service.provider.controller;

import com.bq.wms.core.model.User;
import com.bq.wms.service.provider.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: 李帅伟
 * @date: 2018/4/21
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public long add(User user) {
        return userService.add(user).getId();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable("id") long id) {
        return ResponseEntity.ok().body(userService.findById(id));
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok().body(userService.findAll());
    }
}
