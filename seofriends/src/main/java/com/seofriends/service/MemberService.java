package com.seofriends.service;


import org.springframework.web.bind.annotation.RequestParam;

import com.seofriends.domain.MemberVO;
import com.seofriends.dto.LoginDTO;

public interface MemberService {
//	회원가입.
	void join(MemberVO vo);

//	아이디 중복 체크
	String IdCheck(String mem_id);
//	로그인
	MemberVO login_ok(LoginDTO dto); //아이디만 사용함.
//	아이디 찾기
/*	@Param("mem_name")   @Param("mem_email")
 * 	는 mapper interface나 mapper.xml에서만 사용되므로 
 * 	service에서는 쓰지않는다. 
 *  */
	String searchId(String mem_name, String mem_email);
	
//	임시 비밀번호 발급 확인 작업.
	String getIdEmailExists(String mem_id, String mem_email);
 
//	임시 비밀번호를 암호화하여 변경
	void changePw(String mem_id, String enc_mem_pw);
	
//	회원정보 수정 저장.
	void modify(MemberVO vo);    


}
