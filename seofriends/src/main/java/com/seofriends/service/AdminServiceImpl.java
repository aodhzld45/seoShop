package com.seofriends.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seofriends.domain.AdminVO;
import com.seofriends.domain.MemberVO;
import com.seofriends.mapper.AdminMapper;


@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private AdminMapper admapper;
	
	
	public AdminVO adlogin_ok(AdminVO vo) {
	
		return admapper.adlogin_ok(vo);
	}


	// 2024-05-31 관리자 로그인 프로세스 변경
	@Override
	public MemberVO adlogin(MemberVO memberVO) {
		// TODO Auto-generated method stub
		return admapper.adlogin(memberVO);
	}

}
