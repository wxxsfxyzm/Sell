package com.carlyu.servlet;

import com.carlyu.util.CookieUtil;
import com.carlyu.util.RedisUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "getSessionServlet", urlPatterns = "/session/get")
public class GetSessionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String key = CookieUtil.getCookie(req, "sid");
        String userInfo = null;
        if (null != key) {
            userInfo = RedisUtil.get(key);
            System.out.println(userInfo);
        }
        userInfo = userInfo == null ? "No Session info." : userInfo;
        userInfo += "\nIP:" + req.getRemoteHost();
        PrintWriter pw = resp.getWriter();
        pw.write(userInfo);
        pw.flush();
        pw.close();
    }
}