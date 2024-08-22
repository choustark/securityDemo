package com.chou.securityDemo.inf.utils;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.lang.generator.UUIDGenerator;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
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
	private final static String SECRET = "SECRETKEY24082221404712312312312";

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
				.signWith(SignatureAlgorithm.HS256, SECRET.getBytes())
				.compact();
	}

	// 解析JWT
	public static Claims parseToken(String jwt) {
		if (StringUtils.isBlank(jwt)){
			throw new RuntimeException("无效的token值");
		}
		JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(SECRET.getBytes()).build();
		return jwtParser.parseClaimsJws(jwt).getBody();
	}


	// 验证JWT是否有效
	public static boolean validateToken(String jwt) {
		// 校验是否过期
		if (StringUtils.isBlank(jwt)){
			throw new RuntimeException("无效的token值");
		}
		JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(SECRET.getBytes()).build();
		Date expiration = jwtParser.parseClaimsJws(jwt).getBody().getExpiration();
		if (expiration.before(new Date())){
			throw new RuntimeException("token过期请重新获取！");
		}
		// 校验是否被篡改
		try {
			jwtParser.parseClaimsJws(jwt);
			// 如果能成功解析且没有抛出异常，则说明签名有效，JWT未被篡改
			return true;
		} catch (Exception e) {
			// 若解析时出现异常，通常意味着签名无效或者内容已被篡改
			log.error("token验证失败，可能是被篡改或是无效的token", e);
			return false;
		}
	}

	public static void main(String[] args) {

		JwtTokenInfo tokenInfo = JwtTokenInfo.builder()
				.subject(UUID.fastUUID().toString(true))
				.userId(2024022L)
				.userName("chou")
				.build();
		String token = generateToken(tokenInfo);
		log.info("generateToken jwt is >>>>> {}",token);
		Claims claim = parseToken(token);
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> {}",claim);
		Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(SECRET.getBytes()).build().parseClaimsJws(token);
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
	}
}
