package com.seofriends.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seofriends.domain.AdOrderDetailVO;
import com.seofriends.domain.OrderVO;
import com.seofriends.domain.PaymentVO;
import com.seofriends.dto.Criteria;
import com.seofriends.mapper.AdOrderMapper;

import lombok.Setter;

@Service
public class AdOrderServiceImpl implements AdOrderService {
	
	@Setter(onMethod_ = {@Autowired})
	private AdOrderMapper adordermapper;
	

//	사용자가 주문힌 목록 - 페이징 추가.
	@Override
	public List<OrderVO> adOrderList(Criteria cri, String startDate, String endDate) {
		return adordermapper.adOrderList(cri, startDate, endDate);
	}
	
	//주문 총갯수
	@Override
	public int getOrderTotalCount(Criteria cri, String startDate, String endDate) {
		return adordermapper.getOrderTotalCount(cri, startDate, endDate);
	}

// 주문 상세 목록
	@Override
	public List<AdOrderDetailVO> adOrderDetailView(Long odr_code) {
		return adordermapper.adOrderDetailView(odr_code);
	}
	
// 주문 변경 목록
	@Override
	public List<OrderVO> adOrderHistory(OrderVO o_vo) {
		return adordermapper.adOrderHistory(o_vo);
	}
	
// 주문 상태변경
	@Override
	public void orderStatusChange(Long odr_code, String odr_status) {
		adordermapper.orderStatusChange(odr_code, odr_status);
	}
	
// CS 상태변경
	@Override
	public void orderCsStatusChange(Long odr_code, String cs_status) {
		adordermapper.orderCsStatusChange(odr_code, cs_status);
		
	}
	
//	주문 목록 내역 삭제 (관리자)
	@Override
	public Long adOrderDelete(Long odr_code) {
		return adordermapper.adOrderDelete(odr_code);
	}
	
//	주문 목록 내역 삭제 (관리자) - List로 작업.
	@Override
	public void orderListDelete(List<Long> ordCodeArr) {
		//주문삭제기능 : 관련된 작업을 모두삭제 트랙잭션 추가
		//주문 테이블 삭제(처리완료)
		
		//주문 상세테이블삭제 - 트리거
		//결제 테이블삭제
		
		
		adordermapper.orderListDelete(ordCodeArr);
		
	}

//	주문 정보 가져오기
	@Override
	public OrderVO getOrderInfo(Long odr_code) {
		return adordermapper.getOrderInfo(odr_code);
	}
//	결제 정보 가져오기
	@Override
	public PaymentVO getPaymentInfo(Long odr_code) {
		return adordermapper.getPaymentInfo(odr_code);
	}
//	상품 정보 가져오기
	@Override
	public List<Map<String, Object>> getOrderProductInfo(Long odr_code) {
		return adordermapper.getOrderProductInfo(odr_code);
	}

//	개별 상품 삭제
	@Transactional
	@Override
	public void orderUnitProductCancel(Long odr_code, Integer pdt_num, int odr_price) {
//		테이블에 조건식에 일치되는 데이타가 없어도 정상 실행이된다. update, delete

//		마지막 주문상품을 취소시 주문테이블, 결제테이블 주문정보 삭제
//		마지막 데이타인지 확인 작업 - > 마지막 데이타 count(1)이면 
		if (adordermapper.getOrderDetailProductCount(odr_code) == 1) {
			//주문 테이블 정보 삭제
			adordermapper.adOrderDelete(odr_code);
			//결제 테이블 정보 삭제
			adordermapper.paymentDelete(odr_code);
		}
//		1)주문 상세 테이블 주문 개별 상품 데이타 삭제
		adordermapper.orderDetailProductDelete(odr_code, pdt_num);		
//		2)주문 테이블 : 총 주문 가격 수정(차감)
		adordermapper.orderTotalPriceChange(odr_code, odr_price);
//		3)결제 테이블 : 결제 총금액 수정
		adordermapper.PaymentTotalPriceChange(odr_code, odr_price);
		
	}

//	엑셀 다운로드
	@Override
	public void excelDown(OrderVO o_vo){
		List<OrderVO> excelList = adordermapper.excelDown(o_vo);
		
		   /*try {
		      //Excel Down 시작
		      Workbook workbook = new HSSFWorkbook();
		      //시트생성
		      Sheet sheet = workbook.createSheet("주문목록관리");
		      //행, 열, 열번호
		      Row row = null;
		      Cell cell = null;
		      int rowNo = 0;
		      // 테이블 헤더용 스타일
		      CellStyle headStyle = workbook.createCellStyle();
		      // 가는 경계선을 가집니다.
		      headStyle.setBorderTop(BorderStyle.THIN);
		      headStyle.setBorderBottom(BorderStyle.THIN);
		      headStyle.setBorderLeft(BorderStyle.THIN);
		      headStyle.setBorderRight(BorderStyle.THIN);
		      // 배경색은 노란색입니다.
		      headStyle.setFillForegroundColor(HSSFColorPredefined.YELLOW.getIndex());
		      headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		      // 데이터용 경계 스타일 테두리만 지정
		      CellStyle bodyStyle = workbook.createCellStyle();
		      bodyStyle.setBorderTop(BorderStyle.THIN);
		      bodyStyle.setBorderBottom(BorderStyle.THIN);
		      bodyStyle.setBorderLeft(BorderStyle.THIN);
		      bodyStyle.setBorderRight(BorderStyle.THIN);
	 
		      // 헤더명 설정
		      String[] headerArray = {"주문번호", "주문자아이디","주문자이름","우편번호","주소","상세주소","요청사항", "주문총금액", "주문날짜", "주문상태", "결제상태", "CS상태"};
		      row = sheet.createRow(rowNo++);
		      for(int i=0; i<headerArray.length; i++) {
		      cell = row.createCell(i);
		      cell.setCellStyle(headStyle);
		      cell.setCellValue(headerArray[i]);
		      }

		      for(OrderVO excelData : excelList ) {
		      row = sheet.createRow(rowNo++);
		      
		      cell = row.createCell(0);
		      cell.setCellStyle(bodyStyle);
		      cell.setCellValue(excelData.getOdr_code()); //주문번호
		      
		      cell = row.createCell(1);
		      cell.setCellStyle(bodyStyle);
		      cell.setCellValue(excelData.getMem_id()); //주문자아이디
		      
		      cell = row.createCell(2);
		      cell.setCellStyle(bodyStyle);
		      cell.setCellValue(excelData.getOdr_name()); //주문자이름

		      cell = row.createCell(3);
		      cell.setCellStyle(bodyStyle);
		      cell.setCellValue(excelData.getOdr_zipcode()); //우편번호

		      cell = row.createCell(4);
		      cell.setCellStyle(bodyStyle);
		      cell.setCellValue(excelData.getOdr_addr()); //주소
		 
		      cell = row.createCell(5);
		      cell.setCellStyle(bodyStyle);
		      cell.setCellValue(excelData.getOdr_addr_d()); //상세주소
		      
		      cell = row.createCell(6);
		      cell.setCellStyle(bodyStyle);
		      cell.setCellValue(excelData.getOdr_req_message()); //요청사항
		      
		      cell = row.createCell(7);
		      cell.setCellStyle(bodyStyle);
		      cell.setCellValue(excelData.getOdr_total_price()); //주문총금액
		      
		      cell = row.createCell(8);
		      cell.setCellStyle(bodyStyle);
		      cell.setCellValue(excelData.getOdr_date()); //주문날짜
		      
		      cell = row.createCell(9);
		      cell.setCellStyle(bodyStyle);
		      cell.setCellValue(excelData.getOdr_status()); //주문상태
		      
		      cell = row.createCell(10);
		      cell.setCellStyle(bodyStyle);
		      cell.setCellValue(excelData.getPayment_status()); //결제상태
		      
		      cell = row.createCell(11);
		      cell.setCellStyle(bodyStyle);
		      cell.setCellValue(excelData.getCs_status()); //CS상태
		      	      
		      }

		      // 컨텐츠 타입과 파일명 지정
		      response.setContentType("ms-vnd/excel");
		      response.setHeader("Content-Disposition", "attachment; filename=" + java.net.URLEncoder.encode("주문목록관리.xls", "UTF8"));

		      // 엑셀 출력
		      workbook.write(response.getOutputStream());
		      workbook.close();

		      } catch (Exception e) {

		      e.printStackTrace();

		      }
		   
		return excelList;*/
     
	}
	//엑셀 다운로드(모듈) 
	@Override
	public List<OrderVO> excelmodule(OrderVO o_vo) {
		return adordermapper.excelDown(o_vo);
	}
	
	//주문상세 개별상품 수량 변경
	@Override
	public void adOrderUnitChange(Long odr_code, int odr_amount) {
		adordermapper.adOrderUnitChange(odr_code, odr_amount);
		
	}

	








	



}
