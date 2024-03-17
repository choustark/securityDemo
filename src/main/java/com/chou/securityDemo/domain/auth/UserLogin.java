package com.chou.securityDemo.domain.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author Chou
 * @Description TODO
 * @ClassName UserLongin
 * @Date 2024/3/17 23:18
 * @Version 1.0
 **/

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLogin implements Serializable {
	public static final long serialVersionUID = -9079057098699548888L;
}
