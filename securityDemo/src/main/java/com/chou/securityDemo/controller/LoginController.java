package com.chou.securityDemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

	@RequestMapping("/login")
	public String login(){
		return "login";
	}
}
