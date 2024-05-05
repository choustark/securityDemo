package com.chou.securityDemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chou.securityDemo.controller.request.RegisterRequest;
import com.chou.securityDemo.domain.dto.RegisterDTO;
import com.chou.securityDemo.domain.entity.User;
import com.chou.securityDemo.mapper.UserMapper;
import com.chou.securityDemo.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author Chou
 * @Description 用户service
 * @ClassName UserServiceImpl
 * @Date 2023/12/30 20:09
 * @Version 1.0
 **/
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

	@Resource
	private UserMapper userMapper;


	@Override
	public void register(RegisterDTO registerDTO) {
		// 校验用户名是否存在相同的用户名
		Integer alikeUserNameCount = userMapper.alikeUserNameCount(registerDTO.getUserName());
		if (alikeUserNameCount > 0){
			throw new RuntimeException("用户名已存在");
		}
		// 正则校验手机号，如果有
		// 正则校验邮箱，如果有
		// 生成密码
		// 入库

	}
}
