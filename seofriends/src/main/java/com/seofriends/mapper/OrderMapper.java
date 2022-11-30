package com.seofriends.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.seofriends.domain.CartOrderInfo;
import com.seofriends.domain.OrderVO;
import com.seofriends.domain.PaymentVO;

public interface OrderMapper {
	//장바구니에서 주문 목록 : 상품이 여러개
	List<CartOrderInfo> cartOrderList(String mem_id);
	
	/*직접 구매 주문 목록 : 상품이 한개 -> 
	* LIst<>컬렉션을 사용하는 이유 : 장바구니에서 주문 목록과 model에서 동일하게 사용하기 위해서.
	*/
 	List<CartOrderInfo> directOrderList(@Param("pdt_num") Integer pdt_num, @Param("odr_amount") int odr_amount);
 
	//주문 저장하기 기능 : 주문테이블  
	void orderSave(OrderVO o_vo); //odr_code 필드가 시퀀스의 값이 채워진다.
	
	//주문상세 테이블, 장바구니 테이블 (삭제) : cartservice에서 처리한다.
	void orderDetailSave(@Param("odr_code") Long odr_code, @Param("mem_id") String mem_id);
	
	// 결제 정보 저장하기
	void payMentSave(PaymentVO p_vo);
	
	// 주문 내역
	List<OrderVO> orderHistory(String mem_id);
	
}
