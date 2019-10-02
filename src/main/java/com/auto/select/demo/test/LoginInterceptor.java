package com.auto.select.demo.test;


import com.alibaba.fastjson.JSON;
import com.auto.select.demo.Contoller.AlgorithmController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Component
public class LoginInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String, Object> modelMap = new HashMap<>();
        if (404 == response.getStatus()) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "404:UrlError: Target url not defined!");
            sendError(response, modelMap);
            return false;
        }
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");

        logger.info("喵喵喵："  + user);
        if (user == null) {
            // 未登录
            modelMap.put("success", false);
            modelMap.put("errMsg", "未登录账户，请先进行登录");
            sendError(response, modelMap);
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    private void sendError(HttpServletResponse response, Map<String, Object> modelMap) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        response.getWriter().write(JSON.toJSONString(modelMap));
    }
}
