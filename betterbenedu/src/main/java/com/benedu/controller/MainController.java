package com.benedu.controller;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.benedu.DTO.StudentDTO;
import com.benedu.DTO.TokenDTO;
import com.benedu.DTO.loginUserDTO;
import com.benedu.service.StudentServiceImpl;

@Controller
@CrossOrigin(origins = "*")
public class MainController {
	
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	
	@Autowired
	private StudentServiceImpl StudentService;
	
	@RequestMapping("/")
	public String main() {
		logger.info("test Main");
		return "mainTest.html";
	}
	
	@RequestMapping("/add")
	public String add() {
		// 실제 배포시 없애야함!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		return "add.html";
	}
	
	@RequestMapping("/add_proc")
	public String add_proc(@RequestHeader Map<String, String>  user) {
		logger.info(user.toString());
		StudentService.registUser(new StudentDTO(user.get("id"), user.get("password")));
		return "index.html";
	}
	
	@RequestMapping("/login")
	public String login() {
		logger.info("mapping 완료");
		return "login.html";
	}
	
	@PostMapping("/login_proc")
	public ResponseEntity<?> login_proc(@RequestBody loginUserDTO param) throws InterruptedException, ExecutionException, Exception {
		System.out.println("login_proc 호출됨 : " + param.getUsername() + ", " + param.getPassword());
		
		ResponseEntity<TokenDTO> result = StudentService.login(param.getUsername(),param.getPassword());
		return result;
	}

	@RequestMapping("/newAccessToken")
	public @ResponseBody ResponseEntity<TokenDTO> newAccessToken(@RequestHeader Map<String, String> Refresh){
		return StudentService.getAccessWithRefresh(Refresh.get("refresh"));
	}
	
	@RequestMapping("/imgs/{imgName}")
	public ResponseEntity<byte[]> showImg(@PathVariable("imgName") String imgname){
		File file = new File(imgname);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "image/png");
		
		try {
			return new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(file),headers,HttpStatus.OK);
		} catch (IOException e) {
			return null;
		}
	}
	
}
