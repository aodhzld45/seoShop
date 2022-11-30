package com.seofriends.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seofriends.domain.ChartVO;
import com.seofriends.mapper.ChartMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
@Log4j
@Service
public class ChartServiceImpl implements ChartService {
	@Setter(onMethod_ = {@Autowired})
	private ChartMapper chartmapper;
	
	//카테고리별 통계
	@Override
	public List<ChartVO> primaryChar() {
		return chartmapper.primaryChar();
	}

	//월별 총매출 통계
	@Override
	public List<ChartVO> monthall() {
		return chartmapper.monthall();
	}

}
