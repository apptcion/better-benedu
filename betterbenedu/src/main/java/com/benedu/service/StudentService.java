package com.benedu.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.http.ResponseEntity;

import com.benedu.DTO.StudentDTO;
import com.benedu.DTO.TokenDTO;

public interface StudentService {

	public Boolean isRegistered(String email);
	
	public void registUser(StudentDTO student);
	
	public void removeUser(StudentDTO student);
	
	public void modifyUser(StudentDTO student);
	
	public List<StudentDTO> getAllUsers();
	
	public StudentDTO getOneUserByEmail(String email);
	
	public StudentDTO getOneUserByName(String name);
	
	public ResponseEntity<TokenDTO> login(String email, String password);
	
	public ResponseEntity<TokenDTO> getUserForRefresh(String uuidStr);
	
	public ResponseEntity<TokenDTO> getAccessWithRefresh(String Refresh);
}
