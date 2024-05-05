package com.chou.securityDemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chou.securityDemo.controller.request.RegisterRequest;
import com.chou.securityDemo.domain.dto.RegisterDTO;
import com.chou.securityDemo.domain.entity.User;

/**
 * @Author Chou
 * @Description TODO
 * @ClassName UserService
 * @Date 2023/12/30 20:09
 * @Version 1.0
 **/
public interface UserService extends IService<User> {
	/**
	 * 用户注册
	 * @param registerDTO
	 */
	void register(RegisterDTO registerDTO);
}
