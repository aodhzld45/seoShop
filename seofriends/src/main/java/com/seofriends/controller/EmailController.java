package com.seofriends.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seofriends.dto.EmailDTO;
import com.seofriends.service.EmailService;

import lombok.extern.log4j.Log4j;

@Log4j
@RequestMapping("/email/*")
@RestController //ResponseBody + controller
public class EmailController {
	
	@Autowired
	private EmailService service;
	
	@GetMapping("/send") //EmailDTO dto = new EmailDTO(); 스프링에서 자동 객체 생성
	public ResponseEntity<String> send(EmailDTO dto, HttpSession session){
		
		ResponseEntity<String> entity = null;
		
//		인증 코드 생성
		String authCode = "";
		for (int i = 0; i < 6; i++) {
			authCode += String.valueOf((int)(Math.random()* 10)); //0~9범위의 값.
		}
		
		session.setAttribute("authCode", authCode); //세션으로 인증코드 정보를 서버측 메모리에 생성.
		
		log.info("인증코드 = " + authCode);
//		메일보내기 기능
		try {
			service.sendMail(dto, authCode); //메일 기본정보(받는사람, 보내는사람 등등), 인증코드
			entity = new ResponseEntity<String>("success", HttpStatus.OK);
		} catch (Exception ex) {
			entity = new ResponseEntity<String>("fail", HttpStatus.BAD_REQUEST);
		}
		
		return entity;
		
	}
	
}
