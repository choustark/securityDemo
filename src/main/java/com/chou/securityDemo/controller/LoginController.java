package com.chou.securityDemo.controller;

import cn.hutool.core.bean.BeanUtil;
import com.chou.securityDemo.controller.request.RegisterRequest;
import com.chou.securityDemo.controller.request.SmsLoginRequest;
import com.chou.securityDemo.controller.request.UserLoginRequest;
import com.chou.securityDemo.domain.dto.RegisterDTO;
import com.chou.securityDemo.inf.common.response.ResponseResult;
import com.chou.securityDemo.service.LoginService;
import com.chou.securityDemo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

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
		return ResponseResult.success(login);
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
		return ResponseResult.success("注册成功");
	}


	/**
	 * 发送短信验证码
	 * @param phoneNumber 手机号码
	 * @return 操作结果
	 */
	@PostMapping("/send-sms-code")
	@Operation(summary = "发送短信验证码接口", description = "向指定手机号发送登录或验证用的短信验证码")
	@Parameter(name = "phoneNumber", description = "手机号码", required = true)
	public ResponseResult<String> sendSmsCode(@RequestParam @NotBlank String phoneNumber) {
		String result = loginService.sendSmsCode(phoneNumber);
		return ResponseResult.success(result);
	}

	/**
	 * 手机验证码登录
	 * @param smsLoginRequest 手机验证码登录请求体
	 * @return token
	 */
	@PostMapping("/sms-login")
	@Operation(summary = "手机验证码登录接口", description = "使用手机号和短信验证码进行登录")
	public ResponseResult<String> smsLogin(@RequestBody SmsLoginRequest smsLoginRequest) {
		String token = loginService.smsLogin(smsLoginRequest);
		return ResponseResult.success(token);
	}

	@PostMapping("/logout")
	@Operation(summary = "登出接口",description = "普通的的登出接口")
	public ResponseResult<Boolean> logout(){
		Boolean isLogout = loginService.logout();
		return ResponseResult.success(isLogout);
	}
}
