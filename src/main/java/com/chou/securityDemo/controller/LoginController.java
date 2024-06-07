package com.chou.securityDemo.controller;

import cn.hutool.core.bean.BeanUtil;
import com.chou.securityDemo.controller.request.UserLoginRequest;
import com.chou.securityDemo.controller.request.RegisterRequest;
import com.chou.securityDemo.domain.dto.RegisterDTO;
import com.chou.securityDemo.inf.common.response.ResponseResult;
import com.chou.securityDemo.service.LoginService;
import com.chou.securityDemo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
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
	@Operation(summary = "登录接口",description = "普通的登录接口")
	public ResponseResult<String> login(@RequestBody UserLoginRequest userLoginRequest){
		String login = loginService.login(userLoginRequest);
		return ResponseResult.success(login,"登录成功");
	}

	/**
	 * 用户注册
	 * @param registerRequest
	 */
	@PostMapping("/register")
	@Operation(summary = "注册接口",description = "普通的注册接口")
	public ResponseResult<String> register(@RequestBody RegisterRequest registerRequest){
		RegisterDTO registerDTO = BeanUtil.copyProperties(registerRequest, RegisterDTO.class);
		userService.register(registerDTO);
		return ResponseResult.success(null,"注册成功");
	}
}
