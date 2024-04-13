package com.benedu.javaConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class corsFilterConfig {

	
    /*@Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        CorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        ((UrlBasedCorsConfigurationSource) source).registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }*/
} 
