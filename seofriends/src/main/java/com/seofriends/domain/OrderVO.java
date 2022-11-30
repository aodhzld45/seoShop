package com.seofriends.domain;

import java.util.Date;

import lombok.Data;

/* 주문 테이블 필드
 * odr_code, mem_id, odr_name, odr_zipcode, odr_addr, odr_addr_d, odr_phone, odr_total_price, odr_date 
 * */

@Data
public class OrderVO {
	
	private Long 	odr_code; //시퀀스 처리 SEQ_ODR_CODE;
	private String 	mem_id; // 세션 로그인 아이디
	private String 	odr_name; // 입력 데이타
	private String 	odr_zipcode; //입력 데이타
	private String 	odr_addr; //입력 데이타
	private String 	odr_addr_d; //입력 데이타
	private String 	odr_phone; //입력 데이타
	private String	odr_req_message; //입력 데이타
	private int 	odr_total_price;
	private Date 	odr_date; //기본 값 데이타 sysdate
	
	private String 	odr_status; //주문 상태 : 배송상태
	private String 	payment_status; //결제 상태 : 입금상태
	private String 	cs_status; //cs 상태 : 교환 및 환불, 취소상태
	
	
//	2022-09-05 트리거 주문 변경 내역 컬럼 추가 odr_event_date, state_updatedate, event_name
	private Date odr_event_date; //변경 사항 등록일
	private Date state_updatedate;
	private String event_name; //  -- 취소/환불상태 '취소접수(7)/반품완료(8)/환불처리중(9)/환불완료(0)'
	
}