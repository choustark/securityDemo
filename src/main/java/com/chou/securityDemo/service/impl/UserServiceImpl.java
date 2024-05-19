package com.chou.securityDemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chou.securityDemo.controller.request.RegisterRequest;
import com.chou.securityDemo.domain.dto.RegisterDTO;
import com.chou.securityDemo.domain.entity.User;
import com.chou.securityDemo.inf.enums.GenderTypeEnum;
import com.chou.securityDemo.mapper.UserMapper;
import com.chou.securityDemo.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Objects;

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

	@Resource
	private PasswordEncoder passwordEncoder;


	@Override
	public void register(RegisterDTO registerDTO) {
		// 校验用户名是否存在相同的用户名
		Integer alikeUserNameCount = userMapper.alikeUserNameCount(registerDTO.getUserName());
		if (alikeUserNameCount > 0){
			throw new RuntimeException("用户名已存在");
		}
		// 生成密码
		String dbPassword = passwordEncoder.encode(registerDTO.getPassword());
		// 入库
		User user = User.builder()
				.email(registerDTO.getEmail())
				.name(registerDTO.getUserName())
				.password(dbPassword).status(0)
				.gender(Objects.isNull(registerDTO.getGender()) ? GenderTypeEnum.UN_KNOW.getValue() : registerDTO.getGender())
				.userNo("")
				.isDel(0)
				.build();
		userMapper.insert(user);

	}
}
