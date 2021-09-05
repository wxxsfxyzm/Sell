package com.carlyu.service.impl;

import com.carlyu.dao.UserDAO;
import com.carlyu.entity.User;
import com.carlyu.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    UserDAO userDAO;

    @Override
    public User findByName(String username) {
        //List<User> user = userDAO.findAll();
        for (User singleUser : userDAO.findAll()) {
            // log.info(singleUser.toString());
            if (Objects.equals(singleUser.getUsername(), username)) {
                log.info("查找到了！");
                return singleUser;
            }
        }
        return null;
    }

    @Override
    public User findById(String userId) {
        //List<User> user = userDAO.findAll();
        for (User singleUser : userDAO.findAll()) {
            // log.info(singleUser.toString());
            if (Objects.equals(singleUser.getUserid(), userId)) {
                log.info("查找到了！");
                return singleUser;
            }
        }
        return null;
    }

    @Override
    public User save(User user) {
        return userDAO.save(user);
    }
}
