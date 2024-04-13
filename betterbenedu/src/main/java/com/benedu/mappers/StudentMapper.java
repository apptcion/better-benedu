package com.benedu.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.benedu.DTO.RefreshStudentDTO;
import com.benedu.DTO.StudentDTO;
import com.benedu.DTO.StudentRole;

@Mapper
public interface StudentMapper {
	public List<StudentDTO> getAllUser();
	
	public StudentDTO getOneUserByEmail(String email);
	
	public StudentDTO getOneUserByName(String username);
	
	public int isRegisteredUser(String email);
	
	public void registUserToStudent(StudentDTO dto);
	
	public void registUserToAuth(StudentRole role);
	
	public RefreshStudentDTO getUserForRefresh(String email);
	
	public void setRefreshToken(RefreshStudentDTO dto);
	public void updateRefreshToken(RefreshStudentDTO dto);
}
