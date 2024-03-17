package com.chou.securityDemo.service;

import com.chou.securityDemo.domain.auth.UserLogin;

/**
 * @Author Chou
 * @Description TODO
 * @ClassName LoginService
 * @Date 2024/3/17 23:23
 * @Version 1.0
 **/
public interface LoginService {
	void login(UserLogin userLogin);
}
