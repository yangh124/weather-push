package com.yh.weatherpush.component;

import cn.hutool.json.JSONUtil;
import com.yh.weatherpush.dto.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 当未登录或token失效时，返回JSON格式的结果
 * 
 * @author : yh
 * @date : 2022/3/16 21:34
 */
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
        throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println(JSONUtil.parse(Result.unauthorized(e.getMessage())));
        response.getWriter().flush();
    }
}
