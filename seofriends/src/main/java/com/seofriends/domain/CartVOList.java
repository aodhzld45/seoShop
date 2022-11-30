package com.seofriends.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CartVOList {
	
//	c.cart_code, c.pdt_num, c.mem_id, c.cart_amount, p.pdt_img_folder, p.pdt_img, p.pdt_name, p.pdt_price, m.mem_point
	private Long cart_code;
	
	private Integer pdt_num;
	private String mem_id;
	private int cart_amount;
	
	private String pdt_img_folder;
	private String pdt_img; //상품 이미지 파일이름. 예) test.jpg
	private String pdt_name;
	private int pdt_price;
	
	private int mem_point;
	



	

}
