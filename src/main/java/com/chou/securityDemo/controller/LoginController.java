package com.chou.securityDemo.controller;

import com.chou.securityDemo.domain.auth.UserLogin;
import com.chou.securityDemo.service.LoginService;
import com.chou.securityDemo.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author Chou
 * @Description TODO
 * @ClassName LoginController
 * @Date 2023/11/18 22:20
 * @Version 1.0
 **/
@RestController
@RequestMapping
public class LoginController {

	@Resource
	private UserService userService;

	@Resource
	private LoginService loginService;

	@PostMapping("/login")
	public String login(@RequestBody UserLogin userLogin){
		loginService.login(userLogin);
		return null;
	}

	@PostMapping("/register")
	public void register(){

	}
}
