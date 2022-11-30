package com.seofriends.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seofriends.domain.CartVO;
import com.seofriends.domain.CartVOList;
import com.seofriends.mapper.CartMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
public class CartServiceImpl implements CartService {

	@Setter(onMethod_ = {@Autowired})
	private CartMapper cartmapper;

//	카트 추가
	@Override
	public void cart_add(CartVO vo) {
		cartmapper.cart_add(vo);		
	}
	
//	카트 리스트
	@Override
	public List<CartVOList> cart_list(String mem_id) {
		return cartmapper.cart_list(mem_id);
	}

//	카트 수량 변경.
	@Override
	public void cart_amount_update(Long cart_code, int cart_amount) {
		cartmapper.cart_amount_update(cart_code, cart_amount);
	}

//	카트 목록 삭제
	@Override
	public Long cart_delete(Long cart_code) {
		// TODO Auto-generated method stub
		return cartmapper.cart_delete(cart_code);
	}

//	카트 목록 비우기
	@Override
	public void cart_empty(String mem_id) {
		cartmapper.cart_empty(mem_id);
	}
	

	
	
	
}
