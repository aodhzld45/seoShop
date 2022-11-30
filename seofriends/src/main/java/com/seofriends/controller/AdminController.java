package com.seofriends.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.seofriends.domain.AdminVO;
import com.seofriends.service.AdminService;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@RequestMapping("/admin/*")
@Controller
public class AdminController {

	@Autowired
	private AdminService service;
	
	//spring-security 암호화 라이브러리 DI 주입	
	@Setter(onMethod_ = {@Autowired})
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
//	관리자 로그인 페이지
	@GetMapping("/adLogin")
	public void adLogin() {
		log.info("adLogin 호출.");

	}
	
//	관리자 로그인 POST 데이터 전송
	@PostMapping("/adLoginPost")
	public String adLoginPost(AdminVO vo, RedirectAttributes rttr, HttpSession session ) throws Exception{
		log.info("로그인 정보 :" + vo);
		String url = "";
		//로그인 인증 작업
		AdminVO dbres = service.adlogin_ok(vo);
		
		if (dbres != null) { //아이디가 존재할 경우
			if (bCryptPasswordEncoder.matches(vo.getAdmin_pw(),dbres.getAdmin_pw())) { //비밀번호가 일치할 경우
				//로그인 인증성공
				session.setAttribute("adminstatus", dbres);
				url = "admin/admain";
			}else { 
				url = "admin/adLogin";
			}
				}else {
					url = "admin/adLogin";
				}
			return "redirect:/" + url;		
		}
	
//	관리자 메인페이지 
	@GetMapping("/admain")
	public String admain() {
		log.info("admain 호출.");
		return "admin/admain";
	}
	

//	관리자 로그아웃
	@GetMapping("/adlogout")
	public String adlogout(HttpSession session) {
		session.invalidate();
		return "redirect:/admin/admain";
	}
	
	
	
}		
		
		

