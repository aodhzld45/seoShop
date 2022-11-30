package com.seofriends.service;

import java.util.List;

import com.seofriends.domain.CartOrderInfo;
import com.seofriends.domain.OrderVO;
import com.seofriends.domain.PaymentVO;

public interface OrderService {
	//장바구니 구매 주문 목록
	List<CartOrderInfo> cartOrderList(String mem_id);
	//직접 구매 주문 목록
 	List<CartOrderInfo> directOrderList(Integer pdt_num, int odr_amount);

	//주문 저장하기 기능 : 주문테이블, 주문상세 테이블, 장바구니 테이블 (삭제) : cartservice에서 처리한다.
	void orderbuy(OrderVO o_vo, PaymentVO p_vo); //odr_code 필드가 시퀀스의 값이 채워진다.
	
	//테스트용 주문 목록
	List<OrderVO> orderHistory(String mem_id);
	
}
