package com.seofriends.mapper;

import java.util.List;

import com.seofriends.domain.CategoryVO;
import com.seofriends.domain.ProductVO;
import com.seofriends.dto.Criteria;

public interface AdProductMapper {
	
//	1차 카테고리 
	List<CategoryVO> getCateList(); 
	
//	2차 카테고리 불러오기
	List<CategoryVO> getSubCategoryList(Integer catecode);
	
//	상품등록
	void AdProductInsert(ProductVO p_vo);
	
//	상품목록
	List<ProductVO> getProductList(Criteria cri);
	
//	상품 데이타 개수
	int getProductTotalCount(Criteria cri);
	
//	상품정보 가져오기
	ProductVO getProductByNum(Integer pdt_num);
	
//	상품 수정
	void AdProductModify(ProductVO p_vo);
	
//	상품 삭제
	Integer AdDeleteProduct(Integer pdt_num);
	

}
