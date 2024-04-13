package com.benedu.DTO;

import java.util.ArrayList;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.benedu.controller.MainController;

import lombok.Data;

@Data
public class StudentDTO {

	private static final Logger logger = LoggerFactory.getLogger(StudentDTO.class);
	
	@Override
	public String toString() {
		return "StudentDTO [email=" + email + ", password=" + password + ", roles=" + roles + "]";
	}
	
	private String email;
	private String password;
	private List<StudentRole> roles;

	public StudentDTO(String email, String password) {
		this.email = email;
		this.password = password;
		ArrayList<StudentRole> roles = new ArrayList<StudentRole>();
		roles.add(new StudentRole(email, "ROLE_MEMBER"));
		this.roles = roles;
	}
	
	public StudentDTO(String email, String password, List<StudentRole> roles) {
		this.email = email;
		this.password = password;
		this.roles = roles;
	}
	
	public StudentDTO(String email, String password, String rolesStr) {
		this.email = email;
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<StudentRole> getRoles() {
		return roles;
	}

	public void setRoles(List<StudentRole> roles) {
		this.roles = roles;
	}
}
