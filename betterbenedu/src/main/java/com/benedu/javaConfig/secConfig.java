package com.benedu.javaConfig;

import java.util.Collections;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.benedu.jwttoken.JwtAuthenticationFilter;
import com.benedu.jwttoken.JwtTokenProvider;
import com.benedu.service.CustomAuthenticationProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class secConfig{

	@Autowired
	private UserDetailsService customUserDetailsService;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	
    
	@Bean 
	 public SecurityFilterChain securityfilterChain(HttpSecurity http) throws Exception {
		http
			.httpBasic(HttpBasicConfigurer::disable)
		.authorizeHttpRequests(
				authz -> authz
				.requestMatchers("/login*")	// 로그인 페이지는
					.permitAll()			// 모두에게 허용된다
				.requestMatchers("/add_data").hasAuthority("ROLE_ADMIN")// ADMIN에게만 허용된다
		.requestMatchers("/add").permitAll()
		.requestMatchers("/*").permitAll())
		.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.userDetailsService(customUserDetailsService) 
		.authenticationProvider(customAuthenticationProvider());
		//.formLogin(
		//		form -> form
	//			.loginPage("/login")
	//			.defaultSuccessUrl("/"));
		http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
			http
			 .csrf(AbstractHttpConfigurer::disable)
             .sessionManagement((sessionManagement) ->
                     sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
             )
             .authorizeHttpRequests((authorizeRequests) ->
                     authorizeRequests.anyRequest().permitAll()
             )
			.cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource()));
			
	    return http.build();
	}
	
    CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedHeaders(Collections.singletonList("*"));
            config.setAllowedMethods(Collections.singletonList("*"));
            config.setAllowedOriginPatterns(Collections.singletonList("http://localhost:3000")); // ⭐️ 허용할 origin
            config.setAllowCredentials(true);
            return config;
        };
    }
	

    
    @Bean
    public AuthenticationProvider customAuthenticationProvider() {
    	return new CustomAuthenticationProvider();
    }
}
