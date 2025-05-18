package com.chou.securityDemo.inf.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.util.Collection;

/**
 * @Author Chou
 * @Description 短信验证码认证Token
 * @ClassName SmsCodeAuthenticationToken
 * @Date 2025/05/16
 * @Version 1.0
 */
public class SmsCodeAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private final Object principal; // 通常是手机号
    private Object credentials; // 通常是短信验证码，认证成功后可擦除

    /**
     * 未认证的构造函数，通常在尝试认证时使用。
     * @param principal 手机号码
     * @param credentials 短信验证码
     */
    public SmsCodeAuthenticationToken(Object principal, Object credentials) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(false);
    }

    /**
     * 已认证的构造函数，通常在认证成功后，由 AuthenticationProvider 构建并返回。
     * @param principal 认证后的用户信息 (例如 LoginUser)
     * @param credentials 通常为 null
     * @param authorities 授予的权限
     */
    public SmsCodeAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true); // must use super, as we override
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        credentials = null;
    }
}

