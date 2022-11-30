package com.seofriends.service;

import java.util.List;
import java.util.Map;

import com.seofriends.domain.AdOrderDetailVO;
import com.seofriends.domain.OrderVO;
import com.seofriends.domain.PaymentVO;
import com.seofriends.dto.Criteria;

public interface AdOrderService {
	
//	사용자가 주문힌 목록
	List<OrderVO> adOrderList(Criteria cri, String startDate, String endDate);
	
//	주문 총 갯수
	int getOrderTotalCount(Criteria cri, String startDate, String endDate);
	
//	주문 상태 변경
	void orderStatusChange(Long odr_code, String odr_status);
	
//	CS 상태 변경
	void orderCsStatusChange(Long odr_code, String cs_status);
	
//  주문 상세 목록
	List<AdOrderDetailVO> adOrderDetailView(Long odr_code);
	
//	주문 변경 목록
	List<OrderVO> adOrderHistory(OrderVO o_vo);
	
//	주문정보 가져오기
	OrderVO getOrderInfo(Long odr_code);
	
//  결제정보 가져오기
	PaymentVO getPaymentInfo(Long odr_code);
	
//	주문상품정보
	List<Map<String, Object>> getOrderProductInfo(Long odr_code);
	
//	주문 상세 개별상품 수량 변경
	void adOrderUnitChange(Long odr_code, int odr_amount );

//	주문 목록 내역 삭제 (관리자) - 1
	Long adOrderDelete(Long odr_code);
	
//	주문 목록 내역 삭제 (관리자) - 2 List작업.
	void orderListDelete(List<Long> ordCodeArr);

// 개별상품 삭제 
	void orderUnitProductCancel(Long odr_code, Integer pdt_num, int odr_price);
	
// 엑셀 다운로드
	void excelDown(OrderVO o_vo);

// 엑셀 모듈
	List<OrderVO> excelmodule(OrderVO o_vo);

}