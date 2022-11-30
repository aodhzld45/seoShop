package com.seofriends.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seofriends.domain.ReviewVO;
import com.seofriends.dto.Criteria;
import com.seofriends.mapper.ReviewMapper;

import lombok.Setter;

@Service
public class ReviewServiceImpl implements ReviewService {
	@Setter(onMethod_= {@Autowired})
	private ReviewMapper reviewmapper;
	
//	상품 후기 작성
	@Override
	public void create(ReviewVO vo) {
		reviewmapper.create(vo);		
	}

//	상품 후기 목록 + 페이징 적용
	@Override
	public List<ReviewVO> list(Integer pdt_num, Criteria cri) {
		return reviewmapper.list(pdt_num, cri);
	}
	
//	상품 후기 데이터 개수 정보.
	@Override
	public int listCount(Integer pdt_num) {
		// TODO Auto-generated method stub
		return reviewmapper.listCount(pdt_num);
	}

//	상품 후기 수정
	@Override
	public void modify(ReviewVO vo) {
		reviewmapper.modify(vo);
	}
//	상품 후기 삭제
	@Override
	public void delete(Integer rv_num) {
		reviewmapper.delete(rv_num);
		
	}


	



	

}
