package com.bq.wms.admin.controller;

import com.bq.wms.admin.handler.UserHandler;
import com.bq.wms.core.model.User;
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
    private UserHandler userHandler;

    @PostMapping("/add")
    public long add(User user) {
        return userHandler.add(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable("id") long id) {
        return userHandler.findById(id);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<User>> findAll() {
        return userHandler.findAll();
    }
}
