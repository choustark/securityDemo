package com.chou.securityDemo.domain.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author Chou
 * @Description TODO
 * @ClassName TokenInfo
 * @Date 2024/2/15 11:01
 * @Version 1.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtTokenInfo implements Serializable {
	public static final long serialVersionUID = -5205067940238953414L;

	private Long userId;
	private String userName;
	private String subject;
}
