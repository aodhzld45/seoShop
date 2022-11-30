package com.seofriends.domain;

import lombok.Data;

/*주문 상세 필드
 * ODR_CODE, PDT_NUM, ODR_AMOUNT, ODR_PRICE
 * 주문 페이지에서 데이타를 받아오는 방법
 * 장바구니 테이블에서 데이타를 처리하는 방법(진행)
 * */
@Data
public class OrderDetailVO {
	/* 공통  */
	private Long odr_code; //orderVO의 odr_code 시퀀스값을 참조.
	
	
	/*
	 *  ORDER_TBL
	 * 	O.ODR_CODE, O.ODR_NAME, O.ODR_ZIPCODE, O.ODR_ADDR, O.ODR_ADDR_D, O.ODR_PHONE, O.ODR_REQ_MESSAGE, O.ODR_TOTAL_PRICE, O.ODR_DATE
	 * */
	
	
	/*ORDER_DETAIL_TBL*/
	
	private Integer pdt_num;
	private int 	odr_amount;
	private int 	odr_price; // 상품 가격 * 수량.
	
	
	
	
	

}
