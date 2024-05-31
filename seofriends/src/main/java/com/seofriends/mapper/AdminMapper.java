package com.seofriends.mapper;

import com.seofriends.domain.AdminVO;
import com.seofriends.domain.MemberVO;

public interface AdminMapper {
	
//	관리자 로그인
	AdminVO adlogin_ok(AdminVO vo);
	
// 2024-05-31 관리자 로그인 프로세스 변경
	MemberVO adlogin(MemberVO memberVO);

}
