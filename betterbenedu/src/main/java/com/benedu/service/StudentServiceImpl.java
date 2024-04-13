package com.benedu.service;

import java.util.List;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.benedu.DAO.StudentDAO;
import com.benedu.DTO.CustomUserDetails;
import com.benedu.DTO.RefreshStudentDTO;
import com.benedu.DTO.StudentDTO;
import com.benedu.DTO.TokenDTO;
import com.benedu.jwttoken.JwtTokenProvider;

import io.jsonwebtoken.Claims;



@Service
public class StudentServiceImpl implements StudentService{
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private StudentDAO dao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public Boolean isRegistered(String email) {
		if(dao.isRegisteredUser(email) != 0) 
			return true;
		else return false;
	}

	@Override
	public void registUser(StudentDTO student) {
		student.setPassword(passwordEncoder.encode(student.getPassword()));
		dao.registUser(student);
		
	}

	@Override
	public void removeUser(StudentDTO student) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modifyUser(StudentDTO student) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<StudentDTO> getAllUsers() {
		return dao.getAllUser();
		
	}

	@Override
	public StudentDTO getOneUserByEmail(String email) {
		System.out.println("StudentDTO getOneuserByEmail : 호출됨, dao로 내려감 또는 올라옴");
		return dao.getOneUserByEmail(email);
		
	}
	
	@Override
	public StudentDTO getOneUserByName(String username) {
		return dao.getOneUserByName(username);
	}
	
	@Override
	public ResponseEntity<TokenDTO> getUserForRefresh(String uuidStr){
		return null;
	}

	@Override
	@Transactional
	public ResponseEntity<TokenDTO> login(String email, String password){
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email,password);
		Authentication authentication = authenticate(authenticationToken);
		TokenDTO tokendto = jwtTokenProvider.generateAllToken(authentication,dao);

		SecurityContextHolder.getContext().setAuthentication(authentication);
		ResponseEntity<TokenDTO> result = new ResponseEntity<TokenDTO>(tokendto,HttpStatus.OK);
		return result;
	}
	
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		String username = (String)authentication.getPrincipal();
		String password = (String)authentication.getCredentials();
		
		CustomUserDetails user = (CustomUserDetails) customUserDetailsService.loadUserByUsername(username);
		if(passwordEncoder.matches(password,user.getPassword())) {
			return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
		}
		
		return null;
	}

	@Override
	public ResponseEntity<TokenDTO> getAccessWithRefresh(String Refresh) {
		
		System.out.println(Refresh);

		if(Refresh != null && jwtTokenProvider.vaildateToken(Refresh)) {
		
			Claims claims = jwtTokenProvider.parserClaim(Refresh);
			
			RefreshStudentDTO dto = dao.getUserForRefresh(claims.getSubject());
			//String authorities = "";
			//dto.getAuth().forEach( roleDTO -> {
		//		authorities+=roleDTO.getRole();
		//		authorities+=",";
		//	});

			StringBuilder authorities = new StringBuilder();
			dto.getAuth().forEach(roleDTO -> {
				authorities.append(roleDTO.getRole());
				authorities.append(",");
			});
			String newToken = jwtTokenProvider.generateAccessToken(authorities.toString(), dto.getEmail(), UUID.fromString(dto.getUuid()));
			
			return new ResponseEntity<TokenDTO>(new TokenDTO(newToken),HttpStatus.OK);
		}
		return null;
	}
	
}
