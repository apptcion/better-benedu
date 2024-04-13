package com.benedu.jwttoken;

import java.util.UUID;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.benedu.DAO.StudentDAO;
import com.benedu.DTO.RefreshStudentDTO;

@Service
public class setRefreshTokenByAsync {

	
	@Async
	public void setRefreshToken(Authentication authentication, UUID uuid, long now, StudentDAO dao) {
		RefreshStudentDTO dto = new RefreshStudentDTO(
				authentication.getName()
				,authentication.getCredentials()
				,authentication.getAuthorities(),
				(now + (1000 * 86400)), uuid.toString()
				);
		try {
			dao.setRefreshToken(dto);
		}catch(DuplicateKeyException e) {
			dao.upDateRefreshToken(dto);
		}
	}
}
