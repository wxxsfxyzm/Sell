package com.carlyu.controller;

import com.carlyu.entity.User;
import com.carlyu.service.UserService;
import com.carlyu.util.ResultVOUtil;
import com.carlyu.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@CrossOrigin
@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {

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
     * @param userid   用户id(学号)
     * @param password 密码
     * @param request  Http请求
     * @param response Http响应
     * @return JSON
     */
    /*@PostMapping(value = "/login")
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
                info = "登录成功！";
                return ResultVOUtil.success(0, info);
            } else {
                info = "密码错误！";
                return ResultVOUtil.fail(-1, info);
            }
        } else {
            info = "没查找到该用户！";
            log.info(info);
        }

        log.info(info);
        //return info;
        return ResultVOUtil.fail(-2, info);
    }
*/
    @PostMapping(value = "/login")
    public ResultVO login(
            @RequestParam(value = "userid") Integer userid,
            // @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password,
            HttpServletRequest request,
            HttpServletResponse response) {
        String info = "登录逻辑";
        log.info(info +
                ": userid = " +
                userid +
                //", username = " +
                //username +
                ", password = " +
                password);
        //String key = CookieUtil.getCookie(request, "sid");
        //boolean addFlag = null == key || "".equals(key);

/*        if (null != key && !"".equals(key)) {
            String sid = RedisUtil.get(key);
            addFlag = null == sid || "".equals(sid);
        }*/
        User user = userService.findById(userid);
        // User user = userService.findByName(username);
        //if (user != null && addFlag) {
        if (user != null) {
            log.info(user.toString());
            //key = UUID.randomUUID().toString();

            //String ipAddress = request.getRemoteHost();
            //log.info("创建Session的IP: " + ipAddress);
            // 登录认证，认证成功后将用户信息放到session中
            if (Objects.equals(password, user.getPassword())) {
                //String userInfo = String.format("{username:%s,password:%s}", username, password);
                //request.getSession().setAttribute("userInfo", userInfo);

                //RedisUtil.set(key, userInfo, 30 * 60 * 1000);
                // 将Session的Key写入到用户浏览器cookie
                //Cookie cookie = new Cookie("sid", key);
                //response.addCookie(cookie);
                //log.info("sid->" + key);

                info = "登录成功！";
                return ResultVOUtil.success(0, info);
            } else {
                info = "密码错误！";
                return ResultVOUtil.fail(-1, info);
            }
        } else {
            info = "没查找到该用户！";
            log.info(info);
        }

        log.info(info);
        //return info;
        return ResultVOUtil.fail(-2, info);
    }

    /**
     * 注册逻辑
     *
     * @param userid   学号
     * @param username 用户名
     * @param password 密码
     * @param request  Http请求
     * @param response Http响应
     * @return JSON
     */
    @PostMapping("/register")
    public ResultVO register(
            @RequestParam(value = "userid") Integer userid,
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password,
            HttpServletRequest request,
            HttpServletResponse response) {
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
            return ResultVOUtil.success(0, info);
        } else { // 用户名重复
            info = "学号重复";
            return ResultVOUtil.success(1, info);
        }
    }

}
