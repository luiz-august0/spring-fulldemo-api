package com.springfulldemo.api.utils;

import jakarta.servlet.http.HttpServletRequest;

public abstract class TokenUtil {
    public static String getTokenFromRequest(HttpServletRequest request) {
        String sessionHeader = request.getHeader("Authorization");
        if (Utils.isEmpty(sessionHeader)) return null;

        return sessionHeader.replace("Bearer", "").trim();
    }
}
