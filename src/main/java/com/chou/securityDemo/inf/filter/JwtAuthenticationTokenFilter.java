package com.chou.securityDemo.inf.filter;

import com.chou.securityDemo.domain.auth.LoginUser;
import com.chou.securityDemo.inf.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RedissonClient;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * jwt 认证过滤器
 * @author by Axel
 * @since 2024/6/7 下午7:21
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource
    private RedissonClient redissonClient;

    // key值
    private final String  LOGIN_TOKEN_KEY = "AUTHENTICATION_TOKEN_%s";


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
        String claimsId = claims.getId();
        //从redis中获取用户信息
        String key = String.format(LOGIN_TOKEN_KEY, claimsId);
        LoginUser loginUser = (LoginUser) redissonClient.getBucket(key).get();
        if (Objects.isNull(loginUser)){
            throw new RuntimeException("用户未登录!");
        }
        //存入securityContextHolder TODO 获取用户当前的权限信息
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //放行
        filterChain.doFilter(request,response);
    }
}
