package com.seofriends.controller;


import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.seofriends.domain.MemberVO;
import com.seofriends.dto.EmailDTO;
import com.seofriends.dto.LoginDTO;
import com.seofriends.service.EmailService;
import com.seofriends.service.MemberService;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@RequestMapping("/member/*")
@Controller
public class MemberController {
	
	//spring-security 암호화 라이브러리 DI 주입	
	@Setter(onMethod_ = {@Autowired})
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Setter(onMethod_ = {@Autowired})
	private MemberService service;
	
	@Setter(onMethod_ = {@Autowired})
	private EmailService mailservice;
	
	//회원가입 폼 매핑
	@GetMapping("/join")
	public void join() {
		log.info("== 회원가입 폼 ==");
		
	}
	
	//회원가입 데이터 전송 매핑
	@PostMapping("/join") //MemberVO = MemberVO(). 스프링이 객체생성을 자동으로 해줌.
	public String join(MemberVO vo, RedirectAttributes rttr) throws Exception {
		
		String cryptEncoderPW = bCryptPasswordEncoder.encode(vo.getMem_pw());
		
		log.info("일반 평문 텍스트 비밀번호 : " + vo.getMem_pw());
		log.info("암호화된  비밀번호 : " + cryptEncoderPW);
		log.info("암호화된  비밀번호 길이 :" + cryptEncoderPW.length());
		
		vo.setMem_pw(cryptEncoderPW);
		
		if (vo.getMem_accept_e().equals("on")) {
			vo.setMem_accept_e("Y");
		}
		log.info(vo); //vo.toString()이 생략됨.
		
		service.join(vo);
		
		return "/member/login"; //회원가입 후 로그인 주소로 이동.
	}
	
	//아이디 중복체크
	@ResponseBody
	@GetMapping("/idCheck")
	public ResponseEntity<String> idCheck(@RequestParam("mem_id")  String mem_id){
		ResponseEntity<String> entity = null;
		
		//아이디 존재 여부 작업.
		String isuserID = "";
		
		if (service.IdCheck(mem_id) != null) {
			isuserID = "N"; // 아이디가 존재하여, 사용이 불가능
		}else {
			isuserID = "Y"; // 아이디가 존재하지 않아서, 사용 가능.
		}
		
		entity = new ResponseEntity<String>(isuserID, HttpStatus.OK);
		
		return entity;
		
	}
	
	//이메일 인증 확인작업.
	@PostMapping("/confirmAuthCode")
	@ResponseBody
	public ResponseEntity<String> confirmAuthCode(String uAuthCode, HttpSession session){
		ResponseEntity<String> entity = null;
		
		String authCode = (String) session.getAttribute("authCode");
		log.info(authCode);
		
		if (authCode.equals(authCode)) {
			
			entity = new ResponseEntity<String>("success", HttpStatus.OK);
			session.removeAttribute("authCode");
		}else {
			entity = new ResponseEntity<String>("fail", HttpStatus.OK);

		}
		
		return entity;
		
	}
	
	//로그인 폼 매핑
	@GetMapping("/login")
	private void login() {
		log.info("== 로그인 폼 ==");

	}
	
	//로그인 데이터 전송 매핑
	@PostMapping("/loginPost")
	public String login_Ok(LoginDTO dto, RedirectAttributes rttr, HttpSession session) throws Exception{
//		RedirectAttributes 인터페이스 주요메서드 설명.
//		1)rttr.addFlashAttribute(attributeName, attributeValue) : 페이지 이동주소의 jsp에서 참조할 경우 
//		2)rttr.addAttribute(attributeName, attributeValue) : 리다이렉트 주소에 파라미터로 사용. /member/login?파라미터1=값&파라미터2=값
		/*
		 *	rttr.addAttribute("pageNum", pageNum);
		 *	rttr.addAttribute("amount", amount);
		 *
		 * */
		
		log.info("로그인정보" + dto);
		
//		로그인 인증 작업.
		MemberVO vo = service.login_ok(dto);
		
		String url = "";
		String msg = "";
		
		if (vo != null) { //아이디가 존재하는 경우.
			// 사용자가 입력한 평문텍스트(일반비번)과 DB에 저장된 암호화된 비밀번호를 비교작업.
			
			//1)비밀번호가 일치되는 경우
			String passwd = dto.getMem_pw(); //사용자가 입력한 비밀번호
			String db_passwd = vo.getMem_pw(); // DB에서 가져온 비밀번호.
			
			if (bCryptPasswordEncoder.matches(passwd, db_passwd)) {
				url = "/"; //메인 페이지 주소.
				session.setAttribute("loginStatus", vo);
				
				String dest = (String) session.getAttribute("dest");
				url = (dest != null) ? dest : "/";
				
				msg = "loginSuccess";
				
			}else {
				//2)비밀번호가 일치되지 않는 경우
				url = "/member/login";
				msg = "passwdFailure";
			}
			
		}else {//아이디가 존재하지 않는 경우.
			url = "/member/login"; //로그인 폼 주소.
			msg = "idFailure";
		}
		
		rttr.addFlashAttribute("msg", msg); //이동하는 주소의 jsp에서 참조함.
		return "redirect:" + url;
	}
	
	
	//로그아웃
	@GetMapping("/member/logout")
	public String logout(HttpSession httpSession, RedirectAttributes rttr)  {
		httpSession.invalidate();
		rttr.addFlashAttribute("msg", "logout");
		return "redirect:/";
	}
	
	//아이디 및 비밀번호 찾기 폼
	@GetMapping("/usersearch")
	public void usersearch() {
		
	}
	
	//아이디 찾기	
	@PostMapping("/searchid")
	public String searchid(@RequestParam("mem_name") String mem_name, @RequestParam("mem_email") String mem_email, Model model, RedirectAttributes rttr ) {
	
		log.info("이름 : " + mem_name);
		log.info("이메일 : " + mem_email);
		
		String mem_id = service.searchId(mem_name, mem_email);
		//model = "key값", value값
		String url = "";
		
		if (mem_id != null) {
			model.addAttribute("mem_id", mem_id); //key, value
			url = "/member/searchid"; //jsp 파일
			
		}else {
			url = "redirect:/member/usersearch"; //주소로 이동하면 get방식으로 이동이됨.
			rttr.addFlashAttribute("msg", "noId");
		}

		return url;

	}
	
	//임시비밀번호 발급
	@PostMapping("/searchImsiPw")
	public String searchImsiPw(@RequestParam("mem_id") String mem_id, @RequestParam("mem_email") String mem_email, Model model) {
		
		//db에서 아이디와 메일존재여부를 확인
		String db_mem_id = service.getIdEmailExists(mem_id, mem_email);
		String temp_mem_pw = "";
		
		String url = "";
		
		if(db_mem_id != null) {
			//메일보내기 작업
			
			//1)임시비밀번호 생성
			UUID uid = UUID.randomUUID();
			temp_mem_pw = uid.toString().substring(0, 6); //인덱스 6은 제외한 범위의 6자리문자열
			
			//2)temp_mem_pw 임시비밀번호를 암호화하여, db에 저장
			service.changePw(mem_id, bCryptPasswordEncoder.encode(temp_mem_pw));
			
			//3)메일보내기
			EmailDTO dto = new EmailDTO("DocMall", "DocMall", mem_email, "DocMall 임시비밀번호입니다.", "");
									
			try {
				mailservice.sendMail(dto, temp_mem_pw);
				model.addAttribute("mail", "mail");
				url = "/member/searchid";  //jsp파일이름
			}catch(Exception ex) {
				ex.printStackTrace();
			}
	
		}else {
			url = "redirect:/member/lostpass";
		}
		
		return url;
	}
	
//	회원정보수정을 위한 비밀번호 재확인.
	@GetMapping("/confirmPW")
	public void confirmPW() {
		log.info("==confirmPW 호출됨==");
	}
	
//	회원정보 수정을 위한 비밀번호 재확인.
	@PostMapping("/confirmPW")
	public String confirmPW(@RequestParam("mem_pw") String mem_pw, HttpSession session, RedirectAttributes rttr ) {
		 String mem_id = ((MemberVO) session.getAttribute("loginStatus")).getMem_id();
		 String url = "";
		 
		 LoginDTO dto = new LoginDTO(mem_id, mem_pw);
		//		 로그인시 사용했던 코드를 재사용.
		 MemberVO vo = service.login_ok(dto);
		 
		 if (vo != null) { //true 비밀번호 재확인이 되었을 경우.
			 
				String passwd = dto.getMem_pw(); //사용자가 입력한 비밀번호
				String db_passwd = vo.getMem_pw(); // DB에서 가져온 비밀번호.
				
				if (bCryptPasswordEncoder.matches(passwd, db_passwd)) {
					url = "member/modify"; 				
				}else { //비밀번호가 일치 안된경우
					url = "member/confirmPW";
					rttr.addFlashAttribute("msg", "noPW");
					}	
				}else { //false 비밀번호가 틀린 경우 원래 재확인 주소로 이동.
					url = "member/confirmPW";
					rttr.addFlashAttribute("msg", "noId");
					
				}
				 
		return "redirect:/" + url;	
	}
	
/*	HttpSession session 파라미터로 사용할 경우? 
	로그인한 상태에서 세션에서 로그인 정보를 참조하고자 할 경우.(로그인이나 회원정보 수정)
*/

//	회원정보 수정 폼.
	@GetMapping("/modify")
	public void modify(HttpSession session, Model model) {
		
		String mem_id = ((MemberVO) session.getAttribute("loginStatus")).getMem_id();
		
		LoginDTO dto = new LoginDTO(mem_id, ""); //비밀번호는 쿼리에서 사용이 되고있지 않아, 그래서 공백처리
//		로그인 쿼리가 회원정보 쿼리와 동일하여 사용한다.
		MemberVO vo = service.login_ok(dto);
		
		model.addAttribute("memberVO", vo);
		
	}
	
//	회원정보 수정 저장하기.
	@PostMapping("/modify")
	public String modify(MemberVO vo, RedirectAttributes rttr) {
		
		log.info("회원수정 정보." + vo);
//		클라이언트에서 입력한 정보에 파라미터명이 MemberVO클래스와 일치하지 않으면 , 필드가 참조타입일 경우에는 Null이 된다.
//		1) 파라미터가 일치하지 않는 경우 : 클라이언트 mem_pw100 --------> 자바(스프링) MemberVO클래스의 필드 mem_pw가 Null이 된다.
//		2) 파라미터가 일치하는 경우 : 클라이언트 mem_pw
		if (vo.getMem_pw().equals("")) log.info("공백문자열"); 
		
		if (vo.getMem_pw() != null && !vo.getMem_pw().equals("")) {
			
			log.info("변경 비밀번호 : " + vo.getMem_pw());
			String cryptEncoderPW = bCryptPasswordEncoder.encode(vo.getMem_pw());
			vo.setMem_pw(cryptEncoderPW);
			
		}
		
//		메일 수신여부
		if (vo.getMem_accept_e().equals("on")) {
			vo.setMem_accept_e("Y");
		}else {
			vo.setMem_accept_e("N");
		}
		
		service.modify(vo);
		
		return "redirect:/";	
	}
	
	
	
	
	

}