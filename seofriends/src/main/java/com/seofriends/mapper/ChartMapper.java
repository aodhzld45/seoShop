package com.seofriends.mapper;

import java.util.List;

import com.seofriends.domain.ChartVO;

public interface ChartMapper {
	
	//카테고리별 통계
	List<ChartVO> primaryChar();
	
	//월별 총매출 통계
	List<ChartVO> monthall();
}
