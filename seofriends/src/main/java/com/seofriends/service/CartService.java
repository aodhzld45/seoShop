package com.seofriends.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.seofriends.domain.CartVO;
import com.seofriends.domain.CartVOList;

public interface CartService {
//	카트 추가
	void cart_add(CartVO vo);
	
//	카트 목록
	List<CartVOList> cart_list(String mem_id);
	
//	카트 수량 변경
	void cart_amount_update(Long cart_code, int cart_amount );
	
//	카트 목록 삭제
	Long cart_delete(Long cart_code);
	
//	카트 목록 비우기
	void cart_empty(String mem_id);
	

}
