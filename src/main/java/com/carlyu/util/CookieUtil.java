package com.carlyu.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public final class CookieUtil {
    public static String getCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        String key = null;
        if (null != cookies && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("sid")) {
                    key = cookie.getValue();
                    break;
                }
            }
        }
        return key;
    }
}