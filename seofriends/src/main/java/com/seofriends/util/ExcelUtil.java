package com.seofriends.util;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import com.seofriends.domain.OrderVO;

@Component("excelutil")
public class ExcelUtil {
	
	public static void ExcelDown(List<OrderVO> excelList,  HttpServletResponse response) {	
		 try {
		      //Excel Down 시작
		      Workbook workbook = new HSSFWorkbook();
		      //시트생성
		      Sheet sheet = workbook.createSheet("주문목록관리");
		      for (int i = 4; i <= 12; i++) {
		    	  sheet.setColumnWidth(i, 6000);
			}
		      		      
		      //행, 열, 열번호
		      Row row = null;
		      Cell cell = null;
		      int rowNo = 0;
		  
		      // 테이블 헤더용 스타일
		   	  
		      CellStyle headStyle = workbook.createCellStyle();
		      CreationHelper createHelper = workbook.getCreationHelper();
		      
		      headStyle.setDataFormat(createHelper.createDataFormat().getFormat("m/d/yy h:mm"));
		      
		      // 가는 경계선
		      headStyle.setBorderTop(BorderStyle.MEDIUM);
		      headStyle.setBorderBottom(BorderStyle.MEDIUM);
		      headStyle.setBorderLeft(BorderStyle.MEDIUM);
		      headStyle.setBorderRight(BorderStyle.MEDIUM);
		      // 배경색은 파란색
		      headStyle.setFillForegroundColor(HSSFColorPredefined.SKY_BLUE.getIndex());
		      headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		      // 데이터용 경계 스타일 테두리만 지정
		      CellStyle bodyStyle = workbook.createCellStyle();
		      bodyStyle.setBorderTop(BorderStyle.MEDIUM);
		      bodyStyle.setBorderBottom(BorderStyle.MEDIUM);
		      bodyStyle.setBorderLeft(BorderStyle.MEDIUM);
		      bodyStyle.setBorderRight(BorderStyle.MEDIUM);
	 
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
		      
		      
		      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			  cell = row.createCell(8);
			  cell.setCellStyle(bodyStyle);
		      cell.setCellValue(sdf.format(excelData.getOdr_date())); //주문날짜
		      
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
		       
	}

}


