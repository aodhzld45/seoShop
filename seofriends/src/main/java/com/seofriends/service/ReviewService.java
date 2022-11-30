package com.seofriends.service;

import java.util.List;

import com.seofriends.domain.ReviewVO;
import com.seofriends.dto.Criteria;

public interface ReviewService {
//	상품 후기 작성
	void create(ReviewVO vo);
	
//	상품 후기 목록 + 페이징
	List<ReviewVO> list(Integer pdt_num, Criteria cri); //상품 후기 목록 : 검색 제외.

//	상품 후기 데이터 개수 정보
	int listCount(Integer pdt_num);
//	상품 후기 수정
	void modify(ReviewVO vo);
//	상품 후기 삭제
	void delete(Integer rv_num);
	
}
