package com.chou.securityDemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chou.securityDemo.domain.entity.User;
import com.chou.securityDemo.mapper.UserMapper;
import com.chou.securityDemo.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @Author Chou
 * @Description 用户service
 * @ClassName UserServiceImpl
 * @Date 2023/12/30 20:09
 * @Version 1.0
 **/
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
