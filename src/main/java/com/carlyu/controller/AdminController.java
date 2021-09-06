package com.carlyu.controller;

import com.carlyu.entity.User;
import com.carlyu.service.UserService;
import com.carlyu.util.ResultVOUtil;
import com.carlyu.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@CrossOrigin
@RestController
@RequestMapping("/user")
@Slf4j
public class AdminController {
    @Autowired
    private UserService userService;

    /**
     * 登录逻辑
     *
     * @param userid   用户id(学号)
     * @param password 密码
     * @return JSON
     */

    @PostMapping(value = "/login")
    public ResultVO login(
            @RequestParam(value = "userid") String userid,
            @RequestParam(value = "password") String password) {
        if (Objects.equals(userid, "195030320")) // 这是一个彩蛋
            return ResultVOUtil.fail(-1, "牛马，你不配进入");
        String info = "登录逻辑";
        log.info(info +
                ": userid = " +
                userid +
                ", password = " +
                password);

        User user = userService.findById(userid);

        if (user != null) {
            log.info(user.toString());
            if (Objects.equals(password, user.getPassword())) {
                info = "登录成功！";
                return ResultVOUtil.success(info, user);
            } else {
                info = "密码错误！";
                return ResultVOUtil.fail(-1, info);
            }
        } else {
            info = "没查找到该用户！";
            log.info(info);
        }

        log.info(info);
        return ResultVOUtil.fail(-2, info);
    }

    /**
     * 注册逻辑
     *
     * @param userid   学号
     * @param username 用户名
     * @param password 密码
     * @return JSON
     */
    @PostMapping("/register")
    public ResultVO register(
            @RequestParam(value = "userid") String userid,
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password) {
        String info = "注册逻辑";
        log.info(info +
                ": userid = " +
                userid +
                ", username = " +
                username +
                ", password = " +
                password);

        if (userService.findById(userid) == null) {// 用户名未重复，放行
            User user = new User(userid, username, password);
            log.info(user.toString());
            userService.save(user);
            log.info(userService.findById(userid).toString());
            info = "注册成功";
            return ResultVOUtil.success(userService.findById(userid));
        } else { // 用户名重复
            info = "学号重复";
            return ResultVOUtil.success(1, info);
        }
    }

}
