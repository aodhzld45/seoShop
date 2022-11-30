package com.seofriends.controller;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.log4j.Log4j;

/**
 * Handles requests for the application home page.
 */
@Log4j
@Controller
public class HomeController {
	
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	
//	톰캣이 시작되면, "/" 기본주소는 실행된다.
	
	@GetMapping("/")
	public String home() {
		log.info("==home==");
		
		return "/user/home";
	}
	
//	@GetMapping("/index")
//	public String home() {
//		log.info("===========사용자 화면==========");
//		
//		return "/index";
//	}
	
	
	
	
	
	
	
}
