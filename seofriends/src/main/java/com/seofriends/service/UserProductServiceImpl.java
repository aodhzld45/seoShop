package com.seofriends.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seofriends.domain.CategoryVO;
import com.seofriends.domain.ProductVO;
import com.seofriends.dto.Criteria;
import com.seofriends.mapper.UserProductMapper;

import lombok.Setter;

@Service
public class UserProductServiceImpl implements UserProductService {

	@Setter(onMethod_ = {@Autowired} )
	private UserProductMapper userMapper;
	
//	1차 카테고리 불러오기
	@Override
	public List<CategoryVO> getUserCategoryList() {
		return userMapper.getUserCategoryList();
	}
	
// 2차 카테고리 불러오기
	@Override
	public List<CategoryVO> getUserSubCategoryList(Integer catecode) {
		return userMapper.getUserSubCategoryList(catecode);
	}
	
//	2차 카테고리별 상품 목록
	@Override
	public List<ProductVO> getProductListbysubCategory(Integer catecode, Criteria cri) {
		return userMapper.getProductListbysubCategory(catecode, cri);
	}
//	2차 카테고리별 상품 개수
	@Override
	public int getProductTotalCountbysubCategory(Integer catecode, Criteria cri) {
		return userMapper.getProductTotalCountbysubCategory(catecode, cri);
	}

//	상품정보 가져오기
	@Override
	public ProductVO getUserProductByNum(Integer pdt_num) {
		
		return userMapper.getUserProductByNum(pdt_num);
	}

}