package com.bq.wms.service;

import com.bq.wms.core.enum1.Role;
import com.bq.wms.core.model.User;
import com.bq.wms.service.provider.WmsServiceProviderApplication;
import com.bq.wms.service.provider.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author: 李帅伟
 * @date: 2018/4/21
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WmsServiceProviderApplication.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void add() {
        User user = new User();
        user.setName("王五");
        user.setRule(Role.ADMIN.name());
        User user1 = userService.add(user);
        Assert.assertTrue(user1.getName().equals(user.getName()));
    }

    @Test
    public void findAll() {
        List<User> users = userService.findAll();
        Assert.assertTrue(users.size() > 0);
    }
}
