package com.benedu.DTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

@Data
public class RefreshStudentDTO{

	private String uuidStr;
	private String email;
	private String password;
	private long validDate;
	private List<StudentRole> auth;

	public RefreshStudentDTO(String email, String password, String auth,String uuidStr, String validDate) {};
	
	public RefreshStudentDTO(String email, Object credentials, Collection<? extends GrantedAuthority> authorities,
			long validDate, String uuidStr) {
		this.email = email;
		this.password = (String) credentials;
		this.validDate = validDate;
		this.uuidStr = uuidStr;
		ArrayList<StudentRole> resultAuth = new ArrayList<StudentRole>();
		String[] authList = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")).split(",");
		for(String roles : authList) {
			resultAuth.add(new StudentRole(email, roles));
		}
		this.auth = resultAuth;
	}

	public String getUuid() {
		return uuidStr;
	}
	public void setUuid(String uuidStr) {
		this.uuidStr = uuidStr;
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
	public long getValidDate() {
		return validDate;
	}
	public void setValidDate(long validDate) {
		this.validDate = validDate;
	}
	public List<StudentRole> getAuth() {
		return auth;
	}
	public void setAuth(List<StudentRole> auth) {
		this.auth = auth;
	}
	@Override
	public String toString() {
		return "RefreshStudentDTO [uuid=" + uuidStr + ", email=" + email + ", password=" + password + ", validDate="
				+ validDate + ", auth=" + auth.toString() + "]";
	}

	
	
}
