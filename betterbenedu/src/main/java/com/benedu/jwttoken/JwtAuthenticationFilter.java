package com.benedu.jwttoken;

import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


public class JwtAuthenticationFilter extends OncePerRequestFilter{

	private final JwtTokenProvider jwtTokenProvider;
	
	public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}
	
	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String token = resolveToken(request);
		if (token != null &&  jwtTokenProvider.vaildateToken(token)) {
			if(jwtTokenProvider.typeOfToken(token).equals("access")) {
				Authentication authentication = jwtTokenProvider.getAuthentication(token);
				System.out.println("호출됨");
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		chain.doFilter(request, response);
	}
	
	private String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
			try {
				return bearerToken.substring(7);
			}catch(StringIndexOutOfBoundsException e) {
				return null;
			}
		}
		return null;
	}

}
