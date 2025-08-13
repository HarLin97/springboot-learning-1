package com.github.lybgeek.interceptor


import org.springframework.stereotype.Component

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.util.Arrays

@Component
class HelloHandlerInterceptor extends BaseMappedInterceptor {

    @Override
    boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("uri:" + request.getRequestURI())
        return true

    }

    @Override
    String[] getIncludePatterns() {
        return ["/**"]
    }

    @Override
    String[] getExcludePatterns() {
        return new String[0]
    }
}
