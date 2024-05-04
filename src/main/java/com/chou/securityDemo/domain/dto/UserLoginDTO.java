package com.chou.securityDemo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Chou
 * @version 1.0
 * @description 用户登录传输对象
 * @className UserLoginDTO
 * @date 2024/5/4 23:41
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO implements Serializable {
	public static final long serialVersionUID = -3206357365207755028L;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 密码
	 */
	private String password;
}
