package com.chou.securityDemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity(/*debug = true*/)
@MapperScan(value = {"com.chou.securityDemo.mapper"})
public class SecurityDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityDemoApplication.class, args);
	}

}
