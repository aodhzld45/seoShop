package com.seofriends.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seofriends.domain.MemberVO;
import com.seofriends.dto.LoginDTO;
import com.seofriends.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private MemberMapper mapper;
	
	
//	회원가입.
	@Override
	public void join(MemberVO vo) {
		mapper.join(vo);
	}

// 아이디 중복체크
	@Override
	public String IdCheck(String mem_id) {
		return mapper.IdCheck(mem_id);
	}

//	로그인
	@Override
	public MemberVO login_ok(LoginDTO dto) {
		return mapper.login_ok(dto);
	}

//	아이디 찾기
	@Override
	public String searchId(String mem_name, String mem_email) {
		return mapper.searchId(mem_name, mem_email);
	}

//	임시 비밀번호 발급 확인 작업.
	@Override
	public String getIdEmailExists(String mem_id, String mem_email) {
		return mapper.getIdEmailExists(mem_id, mem_email);
	}
	
//	임시 비밀번호를 암호화하여 변경
	@Override
	public void changePw(String mem_id, String enc_mem_pw) {
		mapper.changePw(mem_id, enc_mem_pw);
	}
	
//	회원정보 수정
	@Override
	public void modify(MemberVO vo) {
		mapper.modify(vo);		
	}

}
