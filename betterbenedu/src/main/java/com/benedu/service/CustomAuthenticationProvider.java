package com.benedu.service;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.benedu.DTO.CustomUserDetails;


public class CustomAuthenticationProvider implements AuthenticationProvider{

	private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		String username = (String)authentication.getPrincipal();
		String password = (String)authentication.getCredentials();
		
		CustomUserDetails user = (CustomUserDetails) customUserDetailsService.loadUserByUsername(username);
		System.out.println(user.getPassword());
		System.out.println(password);
		if(passwordEncoder.matches(password,user.getPassword())) {
			
			System.out.println(user.getAuthorities());
			
			return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
		}
		
		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return true;
	}

}
