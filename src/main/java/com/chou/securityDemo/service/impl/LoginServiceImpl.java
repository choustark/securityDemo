package com.chou.securityDemo.service.impl;

import com.chou.securityDemo.controller.request.UserLoginRequest;
import com.chou.securityDemo.service.LoginService;
import com.chou.securityDemo.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author Chou
 * @Description TODO
 * @ClassName LoginServiceImpl
 * @Date 2024/3/17 23:23
 * @Version 1.0
 **/
@Service(value = "loginService")
public class LoginServiceImpl implements LoginService {

	@Resource
	private UserService userService;

	@Override
	public void login(UserLoginRequest userLogin) {

	}
}
