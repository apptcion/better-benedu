package com.benedu.DAO;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.benedu.DTO.RefreshStudentDTO;
import com.benedu.DTO.StudentDTO;
import com.benedu.DTO.StudentRole;
import com.benedu.mappers.StudentMapper;


@Service
public class StudentDAO{

	@Autowired
	private StudentMapper mapper;
	
	public int isRegisteredUser(String email) {
		return mapper.isRegisteredUser(email);
	}
	
	public StudentDTO getOneUserByEmail(String email) {
		return mapper.getOneUserByEmail(email);
	}
	
	public StudentDTO getOneUserByName(String username) {
		return mapper.getOneUserByName(username);
	}
	
	public List<StudentDTO> getAllUser() {
		return mapper.getAllUser();
	}
	
	public void registUser(StudentDTO dto) {
		mapper.registUserToStudent(dto);
		for(StudentRole role : dto.getRoles()) {
			mapper.registUserToAuth(role);
		}
	}
	
	public RefreshStudentDTO getUserForRefresh(String email) {
		return mapper.getUserForRefresh(email);
		
	}
	
	public void setRefreshToken(RefreshStudentDTO dto) {		
		mapper.setRefreshToken(dto);
		
	}
	
	public void upDateRefreshToken(RefreshStudentDTO dto) {
//		Map<String, String> map = new HashMap();
//		map.put("email", dto.getEmail());
//		map.put("validDate", Long.toString(dto.getValidDate()));
//		map.put("uuid", dto.getUuid());	
//		
		mapper.updateRefreshToken(dto);
		
	}
}
