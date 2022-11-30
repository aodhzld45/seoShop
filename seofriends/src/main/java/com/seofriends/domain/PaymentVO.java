package com.seofriends.domain;

import java.util.Date;

import lombok.Data;

/*
 * 
 *PAY_CODE, ODR_CODE, PAY_METHOD, PAY_DATE, PAY_TOT_PRICE,
 * PAY_REST_PRICE, PAY_NOBANK_PRICE, PAY_NOBANK_USER, PAY_NOBANK*/
@Data
public class PaymentVO {
	private Integer pay_code;
	private Long odr_code;
	private String pay_method;
	private Date pay_date;
	private int pay_tot_price;
	private int	pay_rest_price;
	private int pay_nobank_price;
	private String pay_nobank_user;
	private String pay_nobank;
	
	

}
