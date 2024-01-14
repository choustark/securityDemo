package com.chou.securityDemo.inf.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

/**
 * @Author Chou
 * @Description TODO
 * @ClassName JwtUtils
 * @Date 2023/12/31 18:00
 * @Version 1.0
 **/
public class JwtUtils {
	private static final String SECRET_KEY = "your-secret-key"; // 更换为你的秘钥
	private static final long EXPIRATION_TIME = 86400000; // JWT有效期，默认设置为24小时

	// 生成JWT
	public static String generateToken(Map<String, Object> claims) {
		return Jwts.builder()
				.setClaims(claims)
				//.setSubject()
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY)
				.compact();
	}

	// 解析JWT
	public static Claims getClaims(String token) {
		return Jwts.parser()
				.setSigningKey(SECRET_KEY)
				.parseClaimsJws(token)
				.getBody();
	}

	// 验证JWT是否有效
	public static boolean validateToken(String token) {
		try {
			Claims claims = getClaims(token);
			return !claims.getExpiration().before(new Date());
		} catch (Exception e) {
			return false;
		}
	}
}
