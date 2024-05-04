package com.chou.securityDemo.controller;

import com.alibaba.fastjson.JSON;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author Chou
 * @Description TODO
 * @ClassName IndexController
 * @Date 2023/11/18 21:57
 * @Version 1.0
 **/
@Controller
@RequestMapping("index")
@Tag(name = "首页信息",description = "首页信息")
public class IndexController {

	@GetMapping("/authenticationinfo")
	@ResponseBody
	public String index(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return JSON.toJSONString(authentication);
	}

	@GetMapping("/indexHtml")
	@ResponseBody
	public String index1(){
		return "index.html";
	}
}
