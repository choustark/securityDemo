package com.chou.securityDemo.inf.utils;

import cn.hutool.core.map.MapUtil;
import com.chou.securityDemo.domain.auth.JwtTokenInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * @Author Chou
 * @Description TODO
 * @ClassName JwtUtils
 * @Date 2023/12/31 18:00
 * @Version 1.0
 **/
@Slf4j
public class JwtUtils {

	//私钥
	private final static String SECRET = "secretKey";

	// 秘钥实例
	private final static SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

	// JWT有效期，默认设置为24小时
	private static final long EXPIRATION_TIME = 86400000;

	// 生成JWT
	public static String generateToken(JwtTokenInfo jwtTokenInfo) {

		Date now = new Date();
		// header 信息
		Map<String, Object> headerMap = MapUtil.<String, Object>builder().put("type", "JWT").put("alg", "HS256")
				.map();
		Map<String, Object> payloadMap = MapUtil.<String, Object>builder()
				.put("id", jwtTokenInfo.getUserId())
				.put("name", jwtTokenInfo.getUserName())
				.put("subject",jwtTokenInfo.getSubject())
				.map();
		// 声明信息
		return Jwts.builder()
				.setId(jwtTokenInfo.getUserId().toString())
				// 签发者
				.setIssuer("www.c-visa.com")
				// 签发时间
				.setIssuedAt(now)
				// 主题
				.setSubject(jwtTokenInfo.getSubject())
				// 头信息
				.setHeaderParams(headerMap)
				//.setPayload() json 加密字符串
				//载荷，声明信息
				.setClaims(payloadMap)
				// 设置失效时长，在此时间戳之后获得的 JWT 不得使用
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				//签名
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
	}
	// 解析JWT
	public static Claims parseToken(String token) {
		if (StringUtils.isBlank(token)){
			throw new RuntimeException("无效的token值");
		}
		JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
		return jwtParser.parseClaimsJws(token).getBody();
	}


	// 验证JWT是否有效
	public static boolean validateToken(String token) {
		// 校验是否过期
		if (StringUtils.isBlank(token)){
			throw new RuntimeException("无效的token值");
		}
		JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
		Date expiration = jwtParser.parseClaimsJws(token).getBody().getExpiration();
		if (expiration.after(new Date())){
			throw new RuntimeException("token过期请重新获取！");
		}
		// 校验是否被篡改
		String[] split = token.split(".");
		return false;
	}

	public static void main(String[] args) {
		JwtTokenInfo tokenInfo = JwtTokenInfo.builder()
				.subject(UUID.randomUUID().toString())
				.userId(2024022L)
				.userName("chou")
				.build();
		String token = generateToken(tokenInfo);
		log.info("generateToken jwt is >>>>> {}",token);
		Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
		String signature =claimsJws.getSignature();
		log.info("parseToken signature is >>>>> {}",signature);
		Claims claims = claimsJws.getBody();
		log.info("parseToken claims is >>>>> {}",claims);
		String issuer = claims.getIssuer();
		log.info("parseToken claims issuer is >>>>> {}",issuer);
		Date issuedAt = claims.getIssuedAt();
		log.info("parseToken claims issuedAt is >>>>> {}",issuedAt);
		JwsHeader jwsHeader = claimsJws.getHeader();
		log.info("parseToken header is >>>>> {}",jwsHeader);
		JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
	}
}
