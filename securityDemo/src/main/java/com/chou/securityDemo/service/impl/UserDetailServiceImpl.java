package com.chou.securityDemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.chou.securityDemo.domain.entity.LoginUser;
import com.chou.securityDemo.domain.entity.User;
import com.chou.securityDemo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @Author Chou
 * @Description security UserDetailsService 实现
 * @ClassName UserDetailServiceImpl
 * @Date 2023/12/31 19:36
 * @Version 1.0
 **/
@Service
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 查询用户信息
		LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
		lqw.eq(User::getName,username);
		User user = userMapper.selectOne(lqw);
		if (Objects.isNull(user)){
			throw new RuntimeException("用户名或者密码错误！");
		}
		// 查询对应的权限信息
		return new LoginUser(user);
	}
}
