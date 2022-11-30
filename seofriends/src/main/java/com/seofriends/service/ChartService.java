package com.seofriends.service;

import java.util.List;

import com.seofriends.domain.ChartVO;

public interface ChartService {
	
	//카테고리별 통계
	List<ChartVO> primaryChar();
	
	//월별 총매출 통계
	List<ChartVO> monthall();

}
