package com.chou.securityDemo.service;

import com.chou.securityDemo.controller.request.SmsLoginRequest;
import com.chou.securityDemo.controller.request.UserLoginRequest;

/**
 *  登录逻辑
 * @author  Chou
 * @since 2024/3/17 23:23
 **/
public interface LoginService {
	/**
	 * 登录
	 * @param userLogin 登录信息
	 */
	String login(UserLoginRequest userLogin);

	/**
	 * 发送短信验证码 (模拟)
	 * @param phoneNumber 手机号码
	 * @return 操作结果
	 */
	String sendSmsCode(String phoneNumber);

	/**
	 * 手机验证码登录
	 * @param smsLoginRequest 手机验证码登录请求
	 * @return token
	 */
	String smsLogin(SmsLoginRequest smsLoginRequest);

	/**
	 * 登出
	 * @return
	 */
	Boolean logout();
}
