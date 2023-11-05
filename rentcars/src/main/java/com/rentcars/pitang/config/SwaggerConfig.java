package com.rentcars.pitang.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {
	
	@Value("${portal.swagger.path}")
	private String swaggerPath;

//	private static final String AUTHORIZATION_KEY = "Authorization";

//	@Value("${info.app.version}")
//	private String version;
//
//	@Value("${springdoc.info.description}")
//	private String description;
//
//	@Value("${springdoc.info.title}")
//	private String title;

//	@Bean
//	public OpenAPI infoApi() {
//
//		Info info = new Info();
//		info.setTitle("Pitang");
//
//		SecurityRequirement securityItem = new SecurityRequirement();
//		securityItem.addList(AUTHORIZATION_KEY);
//
//		Components components = new Components();
//		components.addSecuritySchemes(AUTHORIZATION_KEY, new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT"));
//
//		return new OpenAPI().components(components)
//				.addSecurityItem(securityItem)
//				.info(info.description("Desafio pitang.").version("1.0.0"));
//	}

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI().addSecurityItem(new SecurityRequirement().
	            addList("Bearer Authentication"))
		        .components(new Components().addSecuritySchemes
		            ("Bearer Authorization", createAPIKeyScheme()))
				.info(new Info().title("Pitang").version("1.0.0").description(
                        "Desafio pitang.")
						.license(new License().name("Documentação").url(swaggerPath)));
	}
	
	private SecurityScheme createAPIKeyScheme() {
	    return new SecurityScheme().type(SecurityScheme.Type.HTTP)
	        .bearerFormat("JWT")
	        .scheme("Bearer");
	}
}
