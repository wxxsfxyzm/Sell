package com.carlyu.controller;

import com.carlyu.dao.UserDAO;
import com.carlyu.entity.User;
import com.carlyu.service.UserService;
import com.carlyu.util.ResultVOUtil;
import com.carlyu.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@CrossOrigin
@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/test01")
    public String test01() {
        String info = "测试01";
        log.info(info);
        return info;
    }

    @GetMapping(value = "/test00")
    public String test02() {
        String info = "test02";
        log.info(info);
        return info;
    }

    /**
     * 登录逻辑
     *
     * @param username 用户名
     * @param password 用户密码
     * @param request
     * @return
     */
    @PostMapping(value = "/login")
    public ResultVO login(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password,
            HttpServletRequest request) {
        String info = "登录逻辑";
        log.info(info);

        User user = userService.findByName(username);
        if (user != null) {
            log.info(user.toString());
            // 登录认证，认证成功后将用户信息放到session中
            if (Objects.equals(password, user.getPassword())) {
                request.getSession().setAttribute("userInfo", username + " - " + password);
                info = "登录成功";
                return ResultVOUtil.success(0, info);
            } else {
                info = "登录失败";
                return ResultVOUtil.failLogin(-1, info);
            }
        } else
            log.info("没查找到！");


        log.info(info);
        //return info;
        return ResultVOUtil.failLogin(-2, info);
    }

    /**
     * 登出操作
     *
     * @param request
     * @return
     */
    @GetMapping(value = "/logout")
    public String logout(HttpServletRequest request) {
        String info = "登出操作";
        log.info(info);
        HttpSession session = request.getSession();

        // 将用户信息从session中删除
        session.removeAttribute("userInfo");

        Object userInfo = session.getAttribute("userInfo");
        if (userInfo == null) {
            info = "登出成功";
        } else {
            info = "登出失败";
        }
        log.info(info);

        return info;

    }

}
