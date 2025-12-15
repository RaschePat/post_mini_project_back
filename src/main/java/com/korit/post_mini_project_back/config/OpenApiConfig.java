package com.korit.post_mini_project_back.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPIConfig() {
        OpenAPI openAPI = new OpenAPI();

        // 1. API 문서 기본 정보
        Info info = new Info();
        info.title("소셜 게시판 미니 프로젝트");
        info.version("1.0");
        info.description("소셜 게시판 미니 프로젝트");

        // 2. Security 요구사항 (Swagger UI에 자물쇠 아이콘 생성)
        SecurityRequirement securityRequirement = new SecurityRequirement();
        securityRequirement.addList("Bearer Authentication");

        // 3. Security 스키마 (JWT 토큰 입력 방식 정의)
        SecurityScheme securityScheme = new SecurityScheme();
        securityScheme.name("Bearer Authentication");
        securityScheme.type(SecurityScheme.Type.HTTP);  // HTTP 인증
        securityScheme.scheme("bearer");                // Bearer 방식
        securityScheme.bearerFormat("JWT");             // JWT 토큰 형식

        // 4. Components에 등록
        Components components = new Components();
        components.addSecuritySchemes("Bearer Authentication", securityScheme);

        return openAPI
                .info(info)
                .addSecurityItem(securityRequirement)
                .components(components);
    }
}