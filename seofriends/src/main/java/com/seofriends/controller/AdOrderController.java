 package com.seofriends.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.seofriends.domain.OrderVO;
import com.seofriends.dto.Criteria;
import com.seofriends.dto.PageDTO;
import com.seofriends.service.AdOrderService;
import com.seofriends.util.ExcelUtil;
import com.seofriends.util.uploadFileUtils;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Controller
@RequestMapping("/admin/order/*")
public class AdOrderController {
	
	@Setter(onMethod_ = {@Autowired})
	private AdOrderService adorderservice;
	
	@Resource(name = "uploadPath")
	private String uploadPath; // "D://DEV//upload"
	 
	//	주문관리 목록 - 페이징 추가
	@GetMapping("/adOrderList")
	public void adOrderList(
			Model model, @ModelAttribute("cri") Criteria cri,
			@RequestParam(value =  "startDate", required = false) String startDate,
			@RequestParam(value =  "endDate", required = false) String endDate) {
		log.info("시작 날짜 = " + startDate);
		log.info("종료 날짜 = " + endDate);
		
		cri.setAmount(10); //한 페이지에 주문 목록 개수를 10개로 표시.
		List<OrderVO> adOrderList = adorderservice.adOrderList(cri, startDate, endDate);

		model.addAttribute("adOrderList", adOrderList);
		
		int totalCount = adorderservice.getOrderTotalCount(cri, startDate, endDate);
		model.addAttribute("pageMaker", new PageDTO(cri, totalCount));
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
	}
	
	//주문 상태 변경
	@GetMapping("/orderStatusChange")
	public ResponseEntity<String> orderStatusChange(Long odr_code, String odr_status ){
		ResponseEntity<String> entity = null;
		
		adorderservice.orderStatusChange(odr_code, odr_status);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
			
	}
	
	//CS 상태변경
	@GetMapping("/orderCsStatusChange")
	public ResponseEntity<String> orderCsStatusChange(Long odr_code, String cs_status ){
		ResponseEntity<String> entity = null;
		
		//service 작성.
		adorderservice.orderCsStatusChange(odr_code, cs_status);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
			
	}
	
//	주문 상세 목록
	@GetMapping("/adOrderDetailView")
	public void orderDetailView( Long odr_code, Model model) {
		
		log.info("(주문상세)주문번호: " + odr_code);
		
//		List<AdOrderDetailVO> orderproductinfo = null;
		

//		\(역슬래쉬)를 /(슬래쉬)로 변환하는작업. \가 클라이언트에서 서버로 보내지는 데이터로 사용되지 않아 변환
//		for (int i = 0; i < orderproductinfo.size(); i++) {
//			String Pdt_img_folder = orderproductinfo.get(i).getPdt_img_folder().replace("\\", "/");// "/" 부분에 File.separator 운영체제 경로 구분자 (다른 os 예)Linux에 배포할때 사용)
//			orderproductinfo.get(i).setPdt_img_folder(Pdt_img_folder);
//		}
		
		//주문정보 
		model.addAttribute("orderVO", adorderservice.getOrderInfo(odr_code));
		
		//결제정보
		model.addAttribute("paymentVO", adorderservice.getPaymentInfo(odr_code));
		
		//주문 상품 정보
		List<Map<String, Object>> orderProductListMap = adorderservice.getOrderProductInfo(odr_code);
		
		for (int i = 0; i < orderProductListMap.size(); i++) {
			Map<String, Object> orderProduct = orderProductListMap.get(i);
			
			String img_folder = String.valueOf(orderProduct.get("p_imgfolder")).replace("\\", "/");
			orderProduct.put("p_imgfolder", img_folder);
		}
		
		
		model.addAttribute("orderProductMap", orderProductListMap);
		

	}
	
	//주문 변경 목록
	@GetMapping("/adOrderHistory")
	public void adOrderHistory(Model model, OrderVO o_vo){
		
		model.addAttribute("adOrderHistory", adorderservice.adOrderHistory(o_vo));
		
	}
	
	
	//주문목록 상세 상품 수량변경 
	@ResponseBody
	@GetMapping("/adOrderUnitChange")
	public ResponseEntity<String>adOrderUnitChange(@RequestParam("odr_code") Long odr_code, @RequestParam("odr_amount") int odr_amount){
		ResponseEntity<String> entity = null;
		
		adorderservice.adOrderUnitChange(odr_code, odr_amount);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	
	
	//	주문 목록 내역 삭제 (관리자)
	@GetMapping("/adOrderDelete") //상품코드, 페이지 및 검색 파라미터, 날짜폴더, 파일이름
	public String adOrderDelete(@RequestParam("odr_code") Long odr_code) {
		log.info("삭제할 주문번호: " + odr_code);
		
//		1) 상품 수정전 이미지 파일삭제. (날짜 폴더명, 변경전 이미지 파일명)
		adorderservice.adOrderDelete(odr_code);
		
		return "redirect:/admin/order/adOrderList";
	}
	
	//주문 상품 개별 취소 기능
	@GetMapping("/orderUnitProductCancel")
	public String orderUnitProductCancel(Criteria cri, Long odr_code, Integer pdt_num, int odr_price, RedirectAttributes rttr) {
		
		adorderservice.orderUnitProductCancel(odr_code, pdt_num, odr_price);
		
		rttr.addAttribute("odr_code", odr_code);
		
		return "redirect:/admin/order/adOrderDetailView" + cri.getListLink();
		
		
	}
	
	//선택된 주문정보 삭제. ajax구문으로 배열값이 파라미터로 전송될 경우, 스프링 메서드에서는 파라미터 작업? @RequestParam("ordCodeArr[]")
	@ResponseBody
	@PostMapping("/orderCheckedDelete")
	public ResponseEntity<String> orderCheckedDelete(@RequestParam("ordCodeArr[]") List<Long> ordCodeArr) {
		ResponseEntity<String> entity = null;
		
		//방법1.  선택한 개수만큼 반복
		for(int i=0; i<ordCodeArr.size(); i++) {
			
			//주문번호를 이용하여 삭제구문진행.
			//adOrderService.adOrderDelete(ordCodeArr.get(i));
		}
		
		//방법2
		// mybatis에서 이구문 작업을 해야 한다.  delete 주문테이블 where 주문번호 in (1, 2, 3, 4, 5)
		adorderservice.orderListDelete(ordCodeArr);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	//엑셀 다운로드 
	
	@PostMapping("/excelDown")
	public void excelDown(@ModelAttribute OrderVO o_vo, HttpServletResponse response
	                                                    , HttpServletRequest request) throws Exception{
		//adorderservice.excelDown(o_vo, response);
		ExcelUtil.ExcelDown(adorderservice.excelmodule(o_vo), response);
	
	}
	
	
	
//	주문 상세보기에서 이미지 보여주기
	@ResponseBody
	@GetMapping("/displayFile")
	public ResponseEntity<byte[]>displayFile(String folderName, String fileName){
		log.info("폴더이름 = " + folderName);
		log.info("파일이름 = " + fileName);
		
		return uploadFileUtils.getFile(uploadPath, folderName + "\\" + fileName);
		
	}
	
	
	

}