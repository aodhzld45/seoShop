package com.seofriends.domain;

import lombok.Data;

/*
 * c.pdt_num, c.mem_id, c.cart_amount,
p.pdt_num,  p.pdt_name, p.pdt_price,
p.pdt_discount, p.pdt_company, p.pdt_img_folder, p.pdt_img
 * */
@Data
public class CartOrderInfo {
	
	private Integer pdt_num;
	private String 	mem_id;
	private int cart_amount;
	private String pdt_name;
	private int pdt_price;
	private int pdt_discount;
	private String pdt_company;
	private String pdt_img_folder; //상품 이미지 업로드 폴더.
	private String pdt_img; //상품 이미지 파일이름. 예) test.jpg


}
