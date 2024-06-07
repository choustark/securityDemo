package com.chou.securityDemo.service.impl;

import com.chou.securityDemo.controller.request.UserLoginRequest;
import com.chou.securityDemo.domain.auth.JwtTokenInfo;
import com.chou.securityDemo.domain.auth.LoginUser;
import com.chou.securityDemo.inf.utils.JwtUtils;
import com.chou.securityDemo.service.LoginService;
import com.chou.securityDemo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @Author Chou
 * @Description TODO
 * @ClassName LoginServiceImpl
 * @Date 2024/3/17 23:23
 * @Version 1.0
 **/
@Slf4j
@Service(value = "loginService")
public class LoginServiceImpl implements LoginService {


	@Resource
	private AuthenticationManager authenticationManager;

	private final String  LOGIN_TOKEN_KEY = "AUTHENTICATION_TOKEN_%s";

	@Resource
	private UserService userService;

	@Resource
	private RedissonClient redissonClient;

	@Override
	public String login(UserLoginRequest userLogin) {
		//使用authenticationManager 对用户进行认证
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
				new UsernamePasswordAuthenticationToken(userLogin.getUserName(),userLogin.getPassword());
		Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		// 如过认证没有通过则给出对应的提示
		if (Objects.isNull(authenticate)){
			throw new RuntimeException("登录失败！");
		}
		// 如果认证通过了则生成了一个jwt,jwt存入ResponseResult中
		LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
		// 把完整用户信息存入Redis中，userId 作为key
		Long userId = loginUser.getUser().getId();
		String string = JwtUtils.generateToken(JwtTokenInfo.builder().userId(userId).build());
		redissonClient.getBucket(String.format(LOGIN_TOKEN_KEY,userId)).set(loginUser,24,TimeUnit.HOURS);
		return string;
	}
}
