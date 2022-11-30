package com.seofriends.domain;

import lombok.Data;

//CATE_CODE, CATE_CODE_PRT, CATE_NAME
@Data
public class CategoryVO {
	private Integer catecode; //2차 (자식 카테고리) -> 1차카테고리를 참조.
	private Integer catecoderef; //1차 (부모카테고리)
	private String  catename; //카테고리 이름.
}

