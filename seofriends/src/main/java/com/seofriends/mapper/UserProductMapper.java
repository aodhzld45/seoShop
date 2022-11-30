package com.seofriends.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.seofriends.domain.CategoryVO;
import com.seofriends.domain.ProductVO;
import com.seofriends.dto.Criteria;

public interface UserProductMapper {
//	1차 카테고리 
	List<CategoryVO> getUserCategoryList();
	
//	2차 카테고리 불러오기
	List<CategoryVO> getUserSubCategoryList(Integer catecode);
	
//	2차 카테고리별 상품 목록
	List<ProductVO> getProductListbysubCategory(@Param("catecode") Integer catecode, @Param("cri") Criteria cri);
	
//	2차 카테고리별 상품 개수
	int getProductTotalCountbysubCategory(@Param("catecode") Integer catecode, @Param("cri") Criteria cri);

//	상품정보 가져오기
	ProductVO getUserProductByNum(Integer pdt_num);
	
}
