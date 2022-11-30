package com.seofriends.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.seofriends.domain.AdOrderDetailVO;
import com.seofriends.domain.OrderVO;
import com.seofriends.domain.PaymentVO;
import com.seofriends.dto.Criteria;

public interface AdOrderMapper {
	
//	사용자가 주문힌 목록
	List<OrderVO> adOrderList(@Param("cri") Criteria cri, @Param("startDate") String startDate, @Param("endDate") String endDate);
	
//	주문 총 갯수
	int getOrderTotalCount(@Param("cri") Criteria cri, @Param("startDate") String startDate, @Param("endDate") String endDate);
	
//	주문 상태 변경
	void orderStatusChange(@Param("odr_code") Long odr_code, @Param("odr_status") String odr_status);
	
//	CS 상태 변경
	void orderCsStatusChange(@Param("odr_code") Long odr_code, @Param("cs_status") String cs_status);
	
//  주문 상세 목록
	List<AdOrderDetailVO> adOrderDetailView(Long odr_code);
	
//	주문 변경 목록
	List<OrderVO> adOrderHistory(OrderVO o_vo);
	
//	주문정보 
	OrderVO getOrderInfo(Long odr_code);
	
//  결제정보
	PaymentVO getPaymentInfo(Long odr_code);
	
//	주문상품정보
	List<Map<String, Object>> getOrderProductInfo(Long odr_code);
	
//	주문 상세 개별상품 수량 변경
	void adOrderUnitChange(@Param("odr_code")Long odr_code, @Param("odr_amount") int odr_amount);
	
//	주문 목록 내역 삭제 (관리자) - odr_code(data) -1
	Long adOrderDelete(Long odr_code);
	
//	개별 상품삭제
//	1)주문 상세 테이블 주문 개별 상품 데이타 삭제
	void orderDetailProductDelete(@Param("odr_code") Long odr_code, @Param("pdt_num") Integer pdt_num);
//	2)주문 테이블 : 총 주문 가격 수정
	void orderTotalPriceChange(@Param("odr_code") Long odr_code, @Param("odr_price") int odr_price);
//	3)결제 테이블 : 결제 총금액 수정
	void PaymentTotalPriceChange(@Param("odr_code") Long odr_code, @Param("odr_price") int odr_price);
//	4)주문 상세테이블의 데이터 값 1개인지 확인
	int getOrderDetailProductCount(Long odr_code);
	
//	결제 테이블 삭제
	void paymentDelete(Long odr_code);
	
	// 파라미터로 mapper파일에 컬렉션을 사용할 때.  DELETE FROM ORDER_TBL WHERE ODR_CODE IN (주문번호1, 주문번호2, 주문번호3)
	// 참고> 파라미터가 Map컬렉션일 경우는 어떻게 구문을 사용해야 하냐?  HashMap<String, Object> map = new HashMap(); map.put("checkList", chkList)
	// <foreach collection="checkList">
	void orderListDelete(List<Long> ordCodeArr);
	
//	엑셀다운로드용 주문정보
	List<OrderVO> excelDown(OrderVO o_vo);

	
	
	
	
	
	
}
