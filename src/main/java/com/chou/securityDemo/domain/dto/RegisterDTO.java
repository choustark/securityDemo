package com.chou.securityDemo.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author Chou
 * @version 1.0
 * @description 用户注册dto
 * @className RegisterDTO
 * @date 2024/5/2 23:53
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO implements Serializable {
	public static final long serialVersionUID = 3358327519827499237L;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 手机号
	 */
	private String phoneNumber;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 性别
	 */
	private Integer gender;
}
