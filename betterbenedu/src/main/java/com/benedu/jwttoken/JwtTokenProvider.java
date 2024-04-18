package com.benedu.jwttoken;

import java.io.UnsupportedEncodingException;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;


import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.benedu.DAO.StudentDAO;
import com.benedu.DTO.CustomUserDetails;
import com.benedu.DTO.RefreshStudentDTO;
import com.benedu.DTO.StudentDTO;
import com.benedu.DTO.TokenDTO;
import com.benedu.service.CustomUserDetailsService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SecurityException;
import io.jsonwebtoken.security.SignatureException;


@Service
public class JwtTokenProvider {
	
	private final Key key;
	private final long tokenValidityInMilliSecond; //1000
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private setRefreshTokenByAsync setRefreshTokenByAsync;
	
	public JwtTokenProvider(@Value("${jwt.secret}")String sercetkey,
			@Value("${jwt.tokenValidityInMilliSecond}")long tokenValidityInMilliSecond) throws UnsupportedEncodingException {
			
		byte[] keyByte = sercetkey.getBytes("ISO-8859-1");
		this.key  = new SecretKeySpec(keyByte, "HmacSHA256");
		this.tokenValidityInMilliSecond = tokenValidityInMilliSecond;
	}
	
	public TokenDTO generateAllToken(Authentication authentication, StudentDAO dao) {
		String authorities  = authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));
		long now = new Date().getTime();
		UUID uuid = UUID.randomUUID();
		String accessToken = generateAccessToken(authorities,authentication.getName(), uuid);
		String refreshToken = generateRefreshToken(authentication, uuid, dao);
		
		return new TokenDTO("Bearer",accessToken,refreshToken,uuid);
	}
	
	public String generateAccessToken(String authorities, String email, UUID uuid) {
		System.out.println(authorities);
		long now = new Date().getTime();
		return Jwts.builder()
				.setSubject(email)
				.claim("AUT", authorities)
				.claim("TYP", "access")
				.claim("UID",uuid)						// 30 * 60 ( 30분)
				.setExpiration(new Date(now + (tokenValidityInMilliSecond * 1800)))
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
	}
	

	public String generateRefreshToken(Authentication authentication, UUID uuid, StudentDAO dao) {
		long now = new Date().getTime();
		setRefreshTokenByAsync.setRefreshToken(authentication, uuid, now, dao);
		return Jwts.builder()
				.setSubject(authentication.getName())
				.claim("UID",uuid)
				.claim("TYP", "refresh") 				// 60 * 60 * 24 (1일)
				.setExpiration(new Date(now + (tokenValidityInMilliSecond * 86400)))
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
	}
	
	public Authentication getAuthentication(String token) {
		Claims claims = parserClaim(token);
		Collection<? extends GrantedAuthority> authorities = 
				Arrays.stream(claims.get("AUT").toString().split(","))
				.map(SimpleGrantedAuthority::new)
				.toList();
		
		System.out.println(Arrays.stream(claims.get("AUT").toString().split(","))
				.map(SimpleGrantedAuthority::new)
				.toList());
		CustomUserDetails user = (CustomUserDetails) customUserDetailsService.loadUserByUsername(claims.getSubject());
		return new UsernamePasswordAuthenticationToken(claims.getSubject(), user.getPassword(), authorities);
	}

	public String typeOfToken(String token) {
		Claims claims = parserClaim(token);
		return claims.get("TYP").toString();
	}
	
	public boolean vaildateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		} catch (SecurityException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
			return false;
		} catch (ExpiredJwtException e) {
			return false;
		}
		
	}

	public Claims parserClaim(String Token) {
		try {
			return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(Token).getBody();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
