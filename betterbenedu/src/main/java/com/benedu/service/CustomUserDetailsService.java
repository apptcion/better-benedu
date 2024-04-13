package com.benedu.service;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.benedu.DAO.StudentDAO;
import com.benedu.DTO.CustomUserDetails;
import com.benedu.controller.MainController;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
	
	@Autowired
	private StudentDAO dao;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
			CustomUserDetails user;
		try {
			user = new CustomUserDetails(dao.getOneUserByEmail(email));
			return user;
		}catch(NullPointerException e){
			throw new UsernameNotFoundException("Not Found Email");
		}
	}

}
