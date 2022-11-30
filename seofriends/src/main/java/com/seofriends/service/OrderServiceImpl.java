package com.seofriends.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seofriends.domain.CartOrderInfo;
import com.seofriends.domain.OrderVO;
import com.seofriends.domain.PaymentVO;
import com.seofriends.mapper.CartMapper;
import com.seofriends.mapper.OrderMapper;

import lombok.Setter;


@Service
public class OrderServiceImpl implements OrderService {
	
	@Setter(onMethod_= {@Autowired} )
	private OrderMapper ordermapper;
	
	@Setter(onMethod_= {@Autowired})
	private CartMapper cartmapper;

	//장바구니 구매 주문 정보 목록
	@Override
	public List<CartOrderInfo> cartOrderList(String mem_id) {
		
		return ordermapper.cartOrderList(mem_id);
	}
	
	//직접 구매 주문 정보 목록
	@Override
	public List<CartOrderInfo> directOrderList(Integer pdt_num, int odr_amount) {
		return ordermapper.directOrderList(pdt_num, odr_amount);
	}

	
	@Transactional
	@Override
	public void orderbuy(OrderVO o_vo, PaymentVO p_vo) {
//		1)주문테이블 저장하기 . 시퀀스값
		ordermapper.orderSave(o_vo);
		
//		2)주문 상세 테이블 저장하기.(장바구니 테이블에서 이동작업.)
		Long odr_code = o_vo.getOdr_code();
		String mem_id = o_vo.getMem_id();
		
		ordermapper.orderDetailSave(odr_code, mem_id);
		
//		3)장바구니 테이블 삭제하기. 직접 구매 진행시 장바구니에 데이터가 존재하지 않아서, 실행은 되지만 삭제되는 데이터가 없다.
		cartmapper.cart_empty(mem_id);
		
	//	4)결제 정보 저장하기.
		p_vo.setOdr_code(odr_code);
		ordermapper.payMentSave(p_vo);
		
	}

//	테스트용 주문 목록
	@Override
	public List<OrderVO> orderHistory(String mem_id) {
		return ordermapper.orderHistory(mem_id);
	}


}
