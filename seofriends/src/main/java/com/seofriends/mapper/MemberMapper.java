package com.seofriends.mapper;

import org.apache.ibatis.annotations.Param;

import com.seofriends.domain.MemberVO;
import com.seofriends.dto.LoginDTO;

public interface MemberMapper {
//	회원가입.
	void join(MemberVO vo);
	
//	아이디 중복체크.
	String IdCheck(String mem_id);
	
//	로그인.
	MemberVO login_ok(LoginDTO dto); //아이디만 사용함.
	
//	아이디 찾기.
//	*Mapper인터페이스의 메서드가 파라미터 2개 이상일 경우에는 @Param 어노테이션을 반드시 사용해야한다.
//	@Param("mem_name")이 SQL Mapper.xml에서 사용되는 이름이다.
	String searchId(@Param("mem_name") String mem_name, @Param("mem_email") String mem_email);
	
//	임시 비밀번호 발급을 위한 확인작업.
	String getIdEmailExists(@Param("mem_id") String mem_id, @Param("mem_email") String mem_email);
	
//	임시 비밀번호를 암호화하여 변경.
	void changePw(@Param("mem_id") String mem_id, @Param("mem_pw") String enc_mem_pw);
	
//	회원정보 수정 저장.
	void modify(MemberVO vo);    
	
	
	
	
	
}
