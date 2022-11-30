package com.seofriends.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.seofriends.domain.CartVO;
import com.seofriends.domain.CartVOList;

public interface CartMapper {
//	카트 추가
	void cart_add(CartVO vo);
	
//	카트 목록
	List<CartVOList> cart_list(String mem_id);
	
//	카트 수량 변경
	void cart_amount_update(@Param("cart_code") Long cart_code, @Param("cart_amount") int cart_amount );
	
//	카트 목록 삭제
	Long cart_delete(@Param("cart_code") Long cart_code);
	
//	카트 목록 비우기
	void cart_empty(@Param("mem_id") String mem_id);
	

}
