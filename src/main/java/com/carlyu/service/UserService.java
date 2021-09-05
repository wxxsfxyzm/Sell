package com.carlyu.service;

import com.carlyu.entity.User;

public interface UserService {
    /**
     * 根据用户名查找用户
     */
    User findByName(String name);

    /**
     * 根据用户id（学号）查找用户
     */
    User findById(Integer userId);

    /**
     * 用户注册入库
     */
    User save(User user);
}
