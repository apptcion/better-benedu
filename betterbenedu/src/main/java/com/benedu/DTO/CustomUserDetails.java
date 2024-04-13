package com.benedu.DTO;

import java.util.ArrayList;
import java.util.Collection;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails{
	
	private static final long serialVersionUID = 1L;
	
	private String email;
	private String password;
	private List<StudentRole> auth;
	
	public CustomUserDetails(StudentDTO dto) {
		this.email = (dto.getEmail());
		this.password = (dto.getPassword());
		this.auth = (dto.getRoles());
	}
	
	
	@Override
	public String toString() {
		return "CustomUserDetails [email=" + email + ", password=" + password + ", auth=" + auth.toString() + "]";
	}

	public String getAuth() {
		return auth.toString();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		ArrayList<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
		for(StudentRole role : auth) {
		      auths.add(new GrantedAuthority() {

				@Override
				public String getAuthority() {
					return role.getRole();
				}
		    	  
		      });
		}
		return auths;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
