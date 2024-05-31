package com.seofriends.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.seofriends.domain.CartOrderInfo;
import com.seofriends.domain.CartVO;
import com.seofriends.domain.CartVOList;
import com.seofriends.domain.MemberVO;
import com.seofriends.domain.OrderVO;
import com.seofriends.domain.PaymentVO;
import com.seofriends.kakaopay.ApproveResponse;
import com.seofriends.kakaopay.ReadyResponse;
import com.seofriends.service.CartService;
import com.seofriends.service.KakaoPayServiceImpl;
import com.seofriends.service.OrderService;
import com.seofriends.util.uploadFileUtils;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RequestMapping("/user/order/*")
@Log4j
@Controller
public class OrderController {
	
	@Setter(onMethod_ = {@Autowired})
	private OrderService orderservice;
	
	@Setter(onMethod_ = {@Autowired})
	private CartService cartservice;
	
	@Setter(onMethod_ = {@Autowired})
	private KakaoPayServiceImpl kakaopayserviceimpl;
	
	@Resource(name = "uploadPath")
	private String uploadPath; // "D://DEV//upload"
	
//	주문하기 목록 정보 .1)장바구니에서 주문하기 2)상품리스트 팝업대화상자 -바로구매 -> 구분값을 사용하여 처리
	@GetMapping("/orderListInfo")
	public void orderListInfo(@RequestParam("type") String type, HttpSession session, Model model,
		 /* @RequestParam(value = "pdt_num", required = false) */    Integer pdt_num,
		 /* @RequestParam(value = "odr_amount", required = false) */ Integer odr_amount) {
		
		log.info("주문하기 목록 호출됨.");
		String mem_id = ((MemberVO) session.getAttribute("loginStatus")).getMem_id();
		
		List<CartOrderInfo> orderInfoList = null;
		
		if (type.equals("cartOrder")) { //장바구니 구매  Integer pdt_num, int odr_amount 파라미터 사용하지 않음. 
			orderInfoList = orderservice.cartOrderList(mem_id);
		}else if (type.equals("direct")) { //직접 구매 Integer pdt_num, int odr_amount 파라미터 사용. 
			orderInfoList = orderservice.directOrderList(pdt_num, odr_amount);
			//직접 구매시 장바구니에 데이타를 저장한다.
			CartVO vo = new CartVO();
			vo.setMem_id(mem_id);
			vo.setPdt_num(pdt_num);
			vo.setCart_amount(odr_amount);
			cartservice.cart_add(vo);
		}else if (type.equals("detailorder")) {//상품상세 페이지 구매 - directOrderList 재사용
			orderInfoList = orderservice.directOrderList(pdt_num, odr_amount);
			//직접 구매시 장바구니에 데이타를 저장한다.
			CartVO vo = new CartVO();
			vo.setMem_id(mem_id);
			vo.setPdt_num(pdt_num);
			vo.setCart_amount(odr_amount);
			cartservice.cart_add(vo);
		}
		
		/*
		 * String subNum = "";
		 * 
		 * for (int i = 0; i < 6; i++) { subNum += (int)Math.random() * 10; }
		 */
				
		//상품 이미지 // \\ 변환 
		for (int i = 0; i < orderInfoList.size(); i++) {
			String Pdt_img_folder = orderInfoList.get(i).getPdt_img_folder().replace("\\", "/");// "/" 부분에 File.separator 운영체제 경로 구분자 (다른 os 예)Linux에 배포할때 사용)
			orderInfoList.get(i).setPdt_img_folder(Pdt_img_folder);
		}
		
		model.addAttribute("cartOrderList", orderInfoList);
	}
	
//	주문 저장하기
	@PostMapping("/orderSave")
	public String orderSave(PaymentVO p_vo ,OrderVO o_vo, String type, HttpSession session) {
		String mem_id = ((MemberVO) session.getAttribute("loginStatus")).getMem_id();
		o_vo.setMem_id(mem_id);
		
		log.info("주문 정보" + o_vo);
		log.info("결제 정보" + p_vo);
		
		System.out.println("type = {}" + type);
		
//		1. 무통장 입금일 경우.
		if (type.equals("무통장입금")) {
			o_vo.setPayment_status("무통장 입금완료");
			p_vo.setPay_tot_price(o_vo.getOdr_total_price()); //총 실제 결제금액.
			p_vo.setPay_rest_price(0); //추가 입금 금액
		}
		
//		2. 카카오 페이 결제일 경우.
		if (type.equals("카카오페이")) {
			o_vo.setPayment_status("카카오페이 결제완료");
			p_vo.setPay_nobank_user(mem_id);
			p_vo.setPay_nobank_price(0);
			p_vo.setPay_tot_price(o_vo.getOdr_total_price()); //총 실제 결제금액.
			p_vo.setPay_rest_price(0); //추가 입금 금액
		}
		
		//주문 저장하기 기능 : 주문테이블, 주문상세 테이블, 장바구니 테이블 (삭제)
		orderservice.orderbuy(o_vo, p_vo);
		
		
		return "redirect:/user/order/orderHistory";
	}
	
//	카카오 결제요청 -ajax 요청 바로구매는 에러발생 
	@GetMapping("/orderPay")
	public @ResponseBody ReadyResponse payReady(OrderVO o_vo,  PaymentVO p_vo, int totalAmount, HttpSession session) {
		//사용자 아이디 정보 (session) 로그인했을시만
		String mem_id = ((MemberVO) session.getAttribute("loginStatus")).getMem_id();

		//장바구니 테이블에서 상품정보(상품명, 상품코드, 수량, 상품가격*수량=단위별 금액)
		List<CartVOList> cartList = cartservice.cart_list(mem_id);
		String itemName = cartList.get(0).getPdt_name() + "외" + String.valueOf(cartList.size() - 1 )+ " 개";
		int quantity = cartList.size() - 1;
		//int totalAmount = o_vo.getOdr_total_price();
		
		//카카오페이서버에서 보내온 정보. next_redirect_pc_url -> 
		ReadyResponse readyresponse = kakaopayserviceimpl.payReady(itemName, quantity, mem_id, totalAmount);
		
		
		session.setAttribute("tid", readyresponse.getTid());
		o_vo.setMem_id(mem_id);
		session.setAttribute("order", o_vo);
		session.setAttribute("payment", p_vo);
		
		//log.info("결제고유번호: " + readyresponse.getTid());
		
		return readyresponse;
		
	}
	
//	결제 승인 요청 : QR코드를 찍고 (결제 요청) 카카오페이 서버에서 결제가 성공적으로 끝나면, 카카오페이 서버에서 호출하는 주소.
	@GetMapping("/orderApproval")
	public String orderApproval(@RequestParam("pg_token") String pgToken,  /*, @ModelAttribute("tid") String tid, OrderVO o_vo, */HttpSession session ) {
		log.info("결제 승인요청 인증토큰: " + pgToken);
		//log.info("주문정보: " + o_vo);
		String mem_id = ((MemberVO) session.getAttribute("loginStatus")).getMem_id();

		String tid = (String) session.getAttribute("tid"); 
		OrderVO o_vo = (OrderVO) session.getAttribute("order");
		PaymentVO p_vo = (PaymentVO) session.getAttribute("payment");
		
		session.removeAttribute("tid");//세션 제거, 반드시 처리할 것. 로그인 상태에서 세션 정보가 필요하지 않게되면 , 불피요하게 서버측의 메모리를 사용하고 있게된다.
		session.removeAttribute("order");//세션 제거, 반드시 처리할 것. 로그인 상태에서 세션 정보가 필요하지 않게되면 , 불피요하게 서버측의 메모리를 사용하고 있게된다.
		session.removeAttribute("payment");//세션 제거, 반드시 처리할 것. 로그인 상태에서 세션 정보가 필요하지 않게되면 , 불피요하게 서버측의 메모리를 사용하고 있게된다.

		//카카오페이 결제하기
		ApproveResponse approveresponse = kakaopayserviceimpl.payApprove(tid, pgToken, mem_id);
		log.info("approveresponse" + approveresponse);
		
		//카카오페이 결제정보 db저장 : approveResponse 제외.
		
		orderservice.orderbuy(o_vo, p_vo);
		
		return "redirect:/user/order/orderHistory";
	}
	
//	주문내역
	@GetMapping("/orderHistory")
	public void orderHistory(OrderVO o_vo, HttpSession session, Model model) {
	//mem_id 값 가져오기
		String mem_id = ((MemberVO) session.getAttribute("loginStatus")).getMem_id();
		o_vo.setMem_id(mem_id);
		
		List<OrderVO> orderHistory = orderservice.orderHistory(mem_id);
		
		model.addAttribute("orderHistory", orderHistory);
				
	}
		
//	상품목록에서 이미지 보여주기
	@ResponseBody
	@GetMapping("/displayFile")
	public ResponseEntity<byte[]>displayFile(String folderName, String fileName){
		
		//이미지를 바이트 배열로 읽어오는 작업.
		return uploadFileUtils.getFile(uploadPath, folderName + "\\" + fileName);
		
	}
	

	
 	
	

}
