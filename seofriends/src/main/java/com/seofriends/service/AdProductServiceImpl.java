package com.seofriends.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seofriends.domain.CategoryVO;
import com.seofriends.domain.ProductVO;
import com.seofriends.dto.Criteria;
import com.seofriends.mapper.AdProductMapper;

import lombok.Setter;

@Service
public class AdProductServiceImpl implements AdProductService {
	
	//	관리자 상품 관련 mapper bean 주입.
	@Setter(onMethod_ = {@Autowired})
	private AdProductMapper adpromapper;

	//	1차 카테고리
	@Override
	public List<CategoryVO> getCateList() {
		return adpromapper.getCateList();
	}
	
//	2차 카테고리 불러오기
	@Override
	public List<CategoryVO> getSubCategoryList(Integer catecode) {
		return adpromapper.getSubCategoryList(catecode);
	}
	
//	상품등록

	@Override
	public void AdProductInsert(ProductVO p_vo) {
		adpromapper.AdProductInsert(p_vo);
	}
	

//	상품목록 페이징
	@Override
	public List<ProductVO> getProductList(Criteria cri) {
		return adpromapper.getProductList(cri);
	}

//	상품 데이타 개수
	@Override
	public int getProductTotalCount(Criteria cri) {
		return adpromapper.getProductTotalCount(cri);
	}
	
// 상품번호 가져오기
	@Override
	public ProductVO getProductByNum(Integer pdt_num) {
		return adpromapper.getProductByNum(pdt_num);
	}

//	상품 수정하기
	@Override
	public void AdProductModify(ProductVO p_vo) {
		adpromapper.AdProductModify(p_vo);
		
	}
	
// 상품 삭제하기
	@Override
	public Integer AdDeleteProduct(Integer pdt_num) {
		
		return adpromapper.AdDeleteProduct(pdt_num);
	}






}
