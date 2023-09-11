package com.windsor.traineerushbe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // 允许的来源（这里是 http://localhost:3000）
        config.addAllowedOrigin("http://localhost:3000");

        // 允许的 HTTP 方法
        config.addAllowedMethod("*");

        // 允许的请求标头
        config.addAllowedHeader("*");

        // 是否允许附带认证信息（例如，使用Cookie或HTTP验证）
        config.setAllowCredentials(true);

        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}

