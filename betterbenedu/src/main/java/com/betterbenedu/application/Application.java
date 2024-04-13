package com.betterbenedu.application;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.benedu.javaConfig.AppConfig;
import com.benedu.javaConfig.secConfig;

@EnableAsync
@SpringBootApplication(excludeName = "org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration")
@Import({AppConfig.class,secConfig.class})
@ComponentScan(basePackages = {"com.benedu.controller"
		,"com.benedu.DAO"
		,"com.benedu.DTO"
		//,"com.benedu.javaConfig"
		,"com.benedu.jwttoken"
		,"com.benedu.mappers"
		,"com.benedu.service"})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	
	 @Bean
	    public WebMvcConfigurer corsConfigurer() {
	        return new WebMvcConfigurer() {
	            @Override
	            public void addCorsMappings(CorsRegistry registry) {
	                registry.addMapping("/**").allowedOrigins("http://172.16.4.172:3000*");
	            }
	        };
	    }
}
