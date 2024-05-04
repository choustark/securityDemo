package com.chou.securityDemo.inf.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

/**
 * @author Chou
 * @className: SwaggerConfig
 * @description: swagger 配置类
 * @version: 1.0
 */
@Slf4j
@Configuration
public class SwaggerConfig {

	/**
	 * 根据 @Tag 上的排序，写入x-order
	 * @return the global open api customizer
	 */
	@Bean
	public GlobalOpenApiCustomizer orderGlobalOpenApiCustomizer() {
		return openApi -> {
			// 全局添加鉴权参数
			if (openApi.getPaths() != null) {
				openApi.getPaths().forEach((s, pathItem) -> {
					pathItem.readOperations().forEach(operation -> {
						operation.addSecurityItem(new SecurityRequirement().addList(HttpHeaders.AUTHORIZATION));
					});
				});
			}

		};
	}

	//@Bean
	public GroupedOpenApi userApi() {
		String[] paths = {"/**"};
		String[] packagedToMatch = {"com.xiaominfo.knife4j.demo.web"};
		return GroupedOpenApi.builder().group("用户模块").pathsToMatch(paths).addOperationCustomizer((operation, handlerMethod) -> {
			return operation.addParametersItem(new HeaderParameter().name("groupCode").example("测试").description("集团code").schema(new StringSchema()._default("BR").name("groupCode").description("集团code")));
		}).packagesToScan(packagedToMatch).build();
	}

	@Bean
	public OpenAPI userCenterOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("V视界")
						.version("V1.0.0")
						.description("SpringSecurity测试代码")
						.termsOfService("https://github.com/choustark")
						.license(new License().name("Apache 2.0").url("https://github.com/choustark")))
				//给每个请求统一添加请求头
				.addSecurityItem(new SecurityRequirement()
						.addList(HttpHeaders.AUTHORIZATION))
				.components(new Components()
						.addSecuritySchemes(HttpHeaders.AUTHORIZATION, new SecurityScheme()
								.name(HttpHeaders.AUTHORIZATION)
								.type(SecurityScheme.Type.HTTP)
								.scheme("jwt")));
	}

}
