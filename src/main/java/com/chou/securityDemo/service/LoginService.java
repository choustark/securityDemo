package com.chou.securityDemo.service;

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
	 * 登出
	 * @return
	 */
	Boolean logout();
}
