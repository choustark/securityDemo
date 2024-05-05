package com.chou.securityDemo.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
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
@Schema(name = "RegisterRequest", description = "用户注册请求参数")
public class RegisterRequest implements Serializable {
	public static final long serialVersionUID = 3358327519827499237L;

	/**
	 * 用户名
	 */
	@Schema(name = "name", description = "用户名", type = "String", requiredMode = Schema.RequiredMode.REQUIRED, example = "admin")
	@NotBlank(message = "userName不能为空！")
	private String userName;

	/**
	 * 密码
	 */
	@Schema(name = "password",description = "密码",type = "String",requiredMode = Schema.RequiredMode.REQUIRED,example = "123456")
	@NotBlank(message = "密码不能为空！")
	private String password;

	/**
	 * 手机号
	 */
	@Schema(name = "phoneNumber",description = "手机号",type = "String",requiredMode = Schema.RequiredMode.AUTO,example = "12345678901")
	@Pattern(regexp = "^1(3\\d|4[5-9]|5[0-35-9]|6[567]|7[0-8]|8\\d|9[0-35-9])\\d{8}$",message = "手机号格式错误")
	private String phoneNumber;

	/**
	 * 邮箱
	 */
	@Schema(name = "email",description = "邮箱",type = "String",requiredMode = Schema.RequiredMode.AUTO,example = "12345678901@qq.com")
	@Email(message = "邮箱格式错误!")
	private String email;
}
