package com.chou.securityDemo.controller;

import com.chou.securityDemo.controller.request.UserLoginRequest;
import com.chou.securityDemo.controller.request.RegisterRequest;
import com.chou.securityDemo.service.LoginService;
import com.chou.securityDemo.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author Chou
 * @Description 统一登录接口
 * @ClassName LoginController
 * @Date 2023/11/18 22:20
 * @Version 1.0
 **/

@RestController
@RequestMapping("/unify")
@Tag(name = "统一登录接口",description = "统一登录接口")
public class LoginController {

	@Resource
	private UserService userService;

	@Resource
	private LoginService loginService;

	/**
	 * 登录
	 * @param userLoginRequest
	 * @return
	 */
	@PostMapping("/login")
	public String login(@RequestBody UserLoginRequest userLoginRequest){
		loginService.login(userLoginRequest);
		return null;
	}

	/**
	 * 用户注册
	 * @param registerRequest
	 */
	@PostMapping("/register")
	public void register(@RequestBody RegisterRequest registerRequest){

	}
}
