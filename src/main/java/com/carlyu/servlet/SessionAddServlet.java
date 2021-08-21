package com.carlyu.servlet;

import com.carlyu.util.CookieUtil;
import com.carlyu.util.RedisUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

@WebServlet(name = "sessionAddServlet", urlPatterns = "/session/add")
public class SessionAddServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String age = req.getParameter("age");
        System.out.printf("username->%s,age->%s%n", username, age);
        String key = CookieUtil.getCookie(req, "sid");
        boolean addFlag = null == key || "".equals(key);
        if (null != key && !"".equals(key)) {
            String sid = RedisUtil.get(key);
            addFlag = null == sid || "".equals(sid);
        }
        if (addFlag) {
            key = UUID.randomUUID().toString();
            String ip = req.getRemoteHost();
            System.out.println("创建Session的IP：" + ip);
            String userInfo = String.format("{username:%s,age:%s}", username, age);
            // 将要保存到session中的数据写入Redis，有效期30分钟
            RedisUtil.set(key, userInfo, 30 * 60 * 1000);
            // 将Session的Key写入到用户浏览器cookie
            Cookie cookie = new Cookie("sid", key);
            resp.addCookie(cookie);
            System.out.println("sid->" + key);
        }
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(String.format("{\"code\":0,\"msg\":\"%s\"}", addFlag ? "Session创建成功！" : "Session已存在！"));
        printWriter.flush();
        printWriter.close();
    }
}