package com.seofriends.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seofriends.domain.AdminVO;
import com.seofriends.mapper.AdminMapper;


@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private AdminMapper admapper;
	
	
	public AdminVO adlogin_ok(AdminVO vo) {
	
		return admapper.adlogin_ok(vo);
	}

}
