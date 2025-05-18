package com.chou.securityDemo.inf.authentication;

import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @Author Chou
 * @Description 短信验证码认证提供者
 * @ClassName SmsCodeAuthenticationProvider
 * @Date 2025/05/16
 * @Version 1.0
 */
@Component
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService; // 或者一个专门根据手机号加载用户的服务

    @Resource
    private RedissonClient redissonClient;

    // 定义Redis中存储验证码的Key前缀，与发送验证码时保持一致
    private static final String SMS_CODE_REDIS_KEY_PREFIX = "sms_code:";

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsCodeAuthenticationToken authenticationToken = (SmsCodeAuthenticationToken) authentication;
        String phoneNumber = (String) authenticationToken.getPrincipal();
        String smsCode = (String) authenticationToken.getCredentials();

        // 1. 校验短信验证码
        String redisKey = SMS_CODE_REDIS_KEY_PREFIX + phoneNumber;
        String storedCode = (String) redissonClient.getBucket(redisKey).get();

        if (Objects.isNull(storedCode)) {
            throw new BadCredentialsException("验证码已过期或不存在");
        }
        if (!smsCode.equals(storedCode)) {
            throw new BadCredentialsException("验证码错误");
        }
        // 验证通过后，可以考虑删除验证码，防止重复使用
        redissonClient.getBucket(redisKey).delete();

        // 2. 根据手机号加载用户信息
        UserDetails userDetails;
        try {
            // 这在真实场景中通常不是最佳实践，应该明确区分用户名和手机号的查找逻辑
            userDetails = userDetailsService.loadUserByUsername(phoneNumber);
        } catch (UsernameNotFoundException e) {
            // 此处我们假设用户必须已存在 (例如通过密码注册过，或者有其他途径录入)
            throw new UsernameNotFoundException("用户不存在: " + phoneNumber);
        }
        if (Objects.isNull(userDetails)) {
            throw new UsernameNotFoundException("用户不存在: " + phoneNumber);
        }
        // 3. 构建已认证的 Authentication 对象
        SmsCodeAuthenticationToken authenticatedToken = new SmsCodeAuthenticationToken(
                userDetails, // principal 设置为 UserDetails 对象
                null,      // 认证成功后，凭证通常设为 null
                userDetails.getAuthorities() // 用户的权限
        );
        authenticatedToken.setDetails(authentication.getDetails());
        return authenticatedToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // 判断传入的 Authentication 是否是 SmsCodeAuthenticationToken 类型
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

