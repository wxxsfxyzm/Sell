package com.carlyu.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@CrossOrigin
@Controller
@EnableRedisHttpSession
@RequestMapping("/seller")
@Slf4j
public class LogoutController {
    /**
     * 登出操作
     *
     * @param request
     * @return
     */
    @GetMapping(value = "/logout")
    public ModelAndView logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Map<String, Object> map)
            throws IOException {
        String info = "登出操作";
        log.info(info);

/*        String key = CookieUtil.getCookie(request, "sid");
        log.info("key: " + key);
        boolean delFlag = null == key || "".equals(key);

        if (null != key && !"".equals(key)) {
            String sid = RedisUtil.get(key);
            delFlag = null == sid || "".equals(sid);
        }

        if (delFlag) {
            RedisUtil.delkey(key);
            Cookie cookie = new Cookie("sid", key);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }*/

        HttpSession session = request.getSession();
        log.info("用户信息: " + session.getAttribute("userInfo"));
        // 将用户信息从session中删除
        session.removeAttribute("userInfo");

        Object userInfo = session.getAttribute("userInfo");
        if (userInfo == null) {
            info = "登出成功";
        } else {
            info = "登出失败";
            map.put("msg", info);
            map.put("url", "sell/seller/order/list");
            return new ModelAndView("common/logout_error", map);
        }
        log.info(info);

        map.put("msg", info);
        map.put("url", "/admin");

        // response.sendRedirect("http://192.168.31.100:8081");
        return new ModelAndView("common/logout_success", map);

    }
}
