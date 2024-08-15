package com.chou.securityDemo.inf.common.exception;

import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONUtil;
import com.chou.securityDemo.inf.common.response.ResponseResult;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * springSecurity 异常处理
 * @author by Axel
 * @since 2024/6/6 下午11:41
 */
@Component
public class CustomizeAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(JSONUtil.toJsonStr(ResponseResult.fail(), JSONConfig.create().setIgnoreNullValue(false)));
    }
}
