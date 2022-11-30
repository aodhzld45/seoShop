package com.seofriends.domain;

import lombok.Data;
/*CARTCODE, PDT_NUM, MEM_ID, CART_AMOUNT*/
@Data
public class CartVO {
	//장바구니 테이블의 구조를 가지고 있는 VO클래스
	private Long cart_code; //시퀀스사용
	private Integer pdt_num;
	private String mem_id;
	private int cart_amount;

}
