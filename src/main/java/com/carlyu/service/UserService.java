package com.carlyu.service;

import com.carlyu.entity.User;

public interface UserService {
    /**
     * 根据用户名查找用户
     */
    User findByName(String name);
}
