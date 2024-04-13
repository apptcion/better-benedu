package com.benedu.DTO;

import lombok.Data;

@Data
public class StudentRole {

	@Override
	public String toString() {
		return "StudentRole [email=" + email + ", role=" + role + "]";
	}
	
	public StudentRole(String email, String role) {
		this.email = email;
		this.role = role;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	private String email;
	private String role;
}
