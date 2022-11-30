package com.seofriends.domain;

import java.util.Date;

import lombok.Data;

/* 관리자 주문 상세 VO */
@Data
public class AdOrderDetailVO {
	/*공통 컬럼*/
   private 	Long 	odr_code; //시퀀스 처리 SEQ_ODR_CODE;
	/* ORDER_TBL O */
/*
 * O.ODR_CODE, O.MEM_ID, O.ODR_NAME, O.ODR_ZIPCODE, O.ODR_ADDR, O.ODR_ADDR_D,
 * O.ODR_PHONE, O.ODR_REQ_MESSAGE, O.ODR_TOTAL_PRICE, O.ODR_DATE,	
 */
   
	private String 	mem_id; // 세션 로그인 아이디
	private String 	odr_name; // 입력 데이타
	private String 	odr_zipcode; //입력 데이타
	private String 	odr_addr; //입력 데이타
	private String 	odr_addr_d; //입력 데이타
	private String 	odr_phone; //입력 데이타
	private String	odr_req_message; //입력 데이타
	private int 	odr_total_price;
	private Date 	odr_date; //기본 값 데이타 sysdate
	
	/* PAYMENT_TBL P*/
	/*
	 * P.PAY_METHOD, P.PAY_DATE, P.PAY_TOT_PRICE, P.PAY_REST_PRICE,
	 * P.PAY_NOBANK_PRICE, P.PAY_NOBANK_USER, P.PAY_NOBANK,
	 *  
	 * */
	private Integer pay_code;
	private String pay_method;
	private Date pay_date;
	private int pay_tot_price;
	private int	pay_rest_price;
	private int pay_nobank_price;
	private String pay_nobank_user;
	private String pay_nobank;
	
	/* PRODUCT_TBL */
	/*PD.PDT_NAME, PD.PDT_PRICE, PD.PDT_IMG_FOLDER, PD.PDT_IMG*/
	private Integer pdt_num; //시퀀스 적용.
	private String  pdt_img_folder; //상품 이미지 업로드 폴더.
	private String  pdt_img; //상품 이미지 파일이름. 예) test.jpg
	private String  pdt_name;

}
