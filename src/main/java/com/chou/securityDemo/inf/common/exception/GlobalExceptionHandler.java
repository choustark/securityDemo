package com.chou.securityDemo.inf.common.exception;

import com.chou.securityDemo.inf.common.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Chou
 * @version 1.0
 * @description 统一异常处理类
 * @className GlobalExceptionHandler
 * @date 2024/5/5 19:29
 **/
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public ResponseResult<Object> methodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
		Map<String, String> result = new HashMap<>();
		BindingResult bindingResult = e.getBindingResult();
		log.error("request [ {} ] {} of param occur error !", request.getMethod(), request.getRequestURL());
		for (ObjectError objectError : bindingResult.getAllErrors()) {
			FieldError fieldError = (FieldError) objectError;
			log.error("param {} = {} validate error ：{}", fieldError.getField(), fieldError.getRejectedValue(), fieldError.getDefaultMessage());
			result.put(fieldError.getField(), fieldError.getDefaultMessage());
		}
		// 一般项目都会有自己定义的公共返回实体类，这里直接使用现成的 ResponseEntity 进行返回，同时设置 Http 状态码为 400
		return ResponseResult.fail(result);
	}

	@ExceptionHandler(InternalAuthenticationServiceException.class)
	@ResponseBody
	public ResponseResult<Object> methodArgumentNotValidException(InternalAuthenticationServiceException e, HttpServletRequest request) {
		return ResponseResult.fail(e.getMessage());
	}
}
