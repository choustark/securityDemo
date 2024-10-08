package com.chou.securityDemo.inf.config;

import com.chou.securityDemo.inf.common.exception.CustomizeAuthenticationEntryPoint;
import com.chou.securityDemo.inf.filter.JwtAuthenticationTokenFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author Chou
 * @Description TODO
 * @ClassName SecurityConfig
 * @Date 2023/9/17 0:05
 * @Version 1.0
 **/
@Slf4j
@Configuration
// 开启springsecurity 之后会注册大量的servlet filter
@EnableWebSecurity(/*debug = true*/)
public class SecurityConfig {

	@Resource
	private CustomizeAuthenticationEntryPoint customizeAuthenticationEntryPoint;

	@Resource
	private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

	/**
	 * 配置security的
	 *
	 * @param http 的请求信息
	 * @return
	 */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests((auth) -> {
			try {
				auth.antMatchers("/static/**").permitAll()
						// swaggerUi 放行资源
						.antMatchers("doc.html",
								"doc.html/**",
								"webjars/**",
								"/v2/**",
								"/swagger-resources",
								"/swagger-resources/**",
								"/swagger-ui.html",
								"/swagger-ui.html/**",
								"/swagger-ui/**").permitAll()
						// 登录、注册放行资源
						.antMatchers("/security/unify/register").permitAll()
						.antMatchers("/security/unify/login").permitAll()
						//.anyRequest().authenticated()
						.antMatchers("/").authenticated()
						.and()
						//不生成session 保持无状态
						.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
						.and().formLogin()
						// 异常处理
						.and()
						.exceptionHandling()
						.authenticationEntryPoint(customizeAuthenticationEntryPoint)
						.and()
						.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
						.csrf().disable();
						// 登录页面配置
						//.loginPage("/login.html")
						//.loginProcessingUrl("/login")
						//.defaultSuccessUrl("/index")
						//.failureUrl("/loginFail.html")
						//.permitAll()
						/*.successHandler(new AuthenticationSuccessHandler() {
							// 登录成功的回调
							@Override
							public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

							}
						})
						.failureHandler(new AuthenticationFailureHandler() {
							// 登录失败的回调
							@Override
							public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

							}
						})*/

			} catch (Exception e) {
				log.error("SecurityConfig occur error >>>>", e);
			}
		});
		return http.build();
	}

	/**
	 * 密码加密器
	 * @return
	 */
	@Bean
	public PasswordEncoder bCryptPassword(){
		return new BCryptPasswordEncoder();
	}


	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

}
