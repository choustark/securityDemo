package com.chou.securityDemo.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author Chou
 * @Description 手机验证码登录请求
 * @ClassName SmsLoginRequest
 * @Date 2025/05/16
 * @Version 1.0
 */
@Data
@Schema(description = "手机验证码登录请求")
public class SmsLoginRequest {

    @NotBlank(message = "手机号码不能为空")
    @Schema(description = "手机号码", required = true, example = "13800138000")
    private String phoneNumber;

    @NotBlank(message = "短信验证码不能为空")
    @Schema(description = "短信验证码", required = true, example = "123456")
    private String smsCode;
}

