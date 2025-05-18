package com.chou.securityDemo.service.impl;

import com.chou.securityDemo.controller.request.SmsLoginRequest;
import com.chou.securityDemo.controller.request.UserLoginRequest;
import com.chou.securityDemo.domain.auth.JwtTokenInfo;
import com.chou.securityDemo.domain.auth.LoginUser;
import com.chou.securityDemo.inf.authentication.SmsCodeAuthenticationToken;
import com.chou.securityDemo.inf.utils.JwtUtils;
import com.chou.securityDemo.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Random;
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

	private final String SMS_CODE_REDIS_KEY_PREFIX = "sms_code:";

	// 短信验证码有效期5分钟
	private final Long SMS_CODE_EXPIRATION_MINUTES = 5L;

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


	@Override
	public String smsLogin(SmsLoginRequest smsLoginRequest) {
		// 1. 使用 AuthenticationManager 和 SmsCodeAuthenticationProvider 进行认证
		SmsCodeAuthenticationToken smsCodeAuthenticationToken = new SmsCodeAuthenticationToken(smsLoginRequest.getPhoneNumber(), smsLoginRequest.getSmsCode());
		Authentication authenticate = authenticationManager.authenticate(smsCodeAuthenticationToken);

		if (Objects.isNull(authenticate) || !authenticate.isAuthenticated()) {
			throw new RuntimeException("手机验证码登录失败！");
		}

		// 2. 认证成功，获取 LoginUser
		LoginUser loginUser = (LoginUser) authenticate.getPrincipal();

		// 3. 生成JWT
		Long userId = loginUser.getUser().getId();
		String token = JwtUtils.generateToken(JwtTokenInfo.builder().userId(userId).build());

		// 4. 将 LoginUser 存入 Redis (与密码登录逻辑保持一致)
		redissonClient.getBucket(String.format(LOGIN_TOKEN_KEY, userId)).set(loginUser, 24, TimeUnit.HOURS);
		return token;
	}


	@Override
	public String sendSmsCode(String phoneNumber) {
		// 1. 生成6位随机数字验证码
		String smsCode = String.format("%06d", new Random().nextInt(999999));
		log.info("向手机号: {} 发送验证码: {}", phoneNumber, smsCode); // 模拟发送

		// 2. 将验证码存入 Redis，设置有效期
		String redisKey = SMS_CODE_REDIS_KEY_PREFIX + phoneNumber;
		redissonClient.getBucket(redisKey).set(smsCode, SMS_CODE_EXPIRATION_MINUTES, TimeUnit.MINUTES);

		return "短信验证码已发送，请注意查收。"; // 实际场景中不应返回验证码本身
	}

	@Override
	public Boolean logout() {
		UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		LoginUser loginUser = (LoginUser) authentication.getPrincipal();
		Long id = loginUser.getUser().getId();
		// 删除redis中的key值
		return redissonClient.getBucket(String.format(LOGIN_TOKEN_KEY, id)).delete();
	}
}
