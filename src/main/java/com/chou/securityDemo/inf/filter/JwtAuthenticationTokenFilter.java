package com.chou.securityDemo.inf.filter;

import com.chou.securityDemo.inf.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * jwt 认证过滤器
 * @author by Axel
 * @since 2024/6/7 下午7:21
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token = request.getHeader("Token");
        if (StringUtils.isBlank(token)){
            // 放行
            filterChain.doFilter(request,response);
            return;
        }
        //解析token
        if (!JwtUtils.validateToken(token)) {
            // 校验token
            throw new RuntimeException("token非法！");
        }
        Claims claims = JwtUtils.parseToken(token);

        //从redis中获取用户信息
        //存入securityContextHolder
        //放行
    }
}
