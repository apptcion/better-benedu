package com.benedu.controller;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.benedu.DTO.TokenDTO;

@Controller("/login*")
public class LoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@RequestMapping("jwt")
	public TokenDTO loginJwt() {
		return null;
	}
	
	@RequestMapping("google")
	public void loginGoogle() {
		//TODO
	}
	
	@RequestMapping("/dimigoin")
	public void loginDimigoIn(String token) throws IOException {
		logger.info(token);
		
		Document doc = Jsoup.connect("https://auth.dimigo.net/oauth/public").get();
		Elements public_key = doc.getElementsByAttribute("pre");
		System.out.println(public_key);
		System.out.println(doc);
	}
}
	