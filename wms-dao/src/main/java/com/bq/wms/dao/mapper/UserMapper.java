package com.bq.wms.dao.mapper;

import com.bq.wms.core.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: 李帅伟
 * @date: 2018/4/21
 **/
public interface UserMapper {

    long save(User user);
    User findById(@Param("id")long id);
    List<User> findAll();
}
