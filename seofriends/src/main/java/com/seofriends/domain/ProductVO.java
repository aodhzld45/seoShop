package com.seofriends.domain;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

/*
 * pdt_num, cate_code, cate_code_prt, 
 * pdt_name, pdt_price, pdt_discount, 
 * pdt_company, pdt_detail, pdt_img, 
 * pdt_amount, pdt_buy, pdt_date_sub, 
 * pdt_date_up

 * 
 * 
 * */
@Data
public class ProductVO {
	private Integer pdt_num; //시퀀스 적용.
	private Integer catecode; //2차
	private Integer catecoderef; //1차 (부모카테고리)
	
	private String pdt_name;
	
	private int pdt_price;
	private int pdt_discount;
	
	private String pdt_company;
	private String pdt_detail;
	private String pdt_img_folder; //상품 이미지 업로드 폴더.
	private String pdt_img; //상품 이미지 파일이름. 예) test.jpg
	
	private int pdt_amount;
	private String pdt_buy;
	
	private Date pdt_date_sub;
	private Date pdt_date_up;

	
	// 	상품 이미지 파일(업로드)
	//	<input type="file"  class="form-control" id="" name="uploadFile"  >
	private MultipartFile uploadFile; //첨부된 파일을 name 값을 가져옴. 파일이 여러개면 배열로 생성 -> input type에 multiple 속성 추가.
	
	
	

}
