package com.bq.wms.service.provider.service;

import com.bq.wms.core.model.User;
import com.bq.wms.dao.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: 李帅伟
 * @date: 2018/4/21
 **/
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Transactional(rollbackFor = Exception.class)
    public User add(User user) {
        userMapper.save(user);
        return user;
    }

    public User findById(long id) {
        return userMapper.findById(id);
    }

    public List<User> findAll() {
        return userMapper.findAll();
    }
}
