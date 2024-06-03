package com.seofriends.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.seofriends.kakaopay.ApproveResponse;
import com.seofriends.kakaopay.ReadyResponse;

import lombok.extern.log4j.Log4j;

//인터페이스가 없이 단독 클래스로 구성.

@Log4j
@Service
public class KakaoPayServiceImpl {

	public ReadyResponse payReady(String itemName, int quantity, String mem_id, int totalAmount) {
		
		String order_id = "100";
		//String itemName = "테스트상품"; // 상품 이름
		//int quantity = 1;
		
//		카카오페이가 요청한 결제 요청한 request 정보를 구성한다.
//		컬렉션의 Map인터페이스의 특징은 key, value 구성. key 1개당 값(value)이 1개 ex)map.put("key","value")의 형태
//		MultiValueMap : key 1개당 여러개의 value를 갖는 구조의 Spring FrameWork에서 제공하는 map
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.add("cid", "TC0ONETIME"); //테스트 가맹점 id
		parameters.add("partner_order_id", order_id); //고객 주문번호.
		parameters.add("partner_user_id", mem_id); //고객 회원 Id
		parameters.add("item_name", itemName); //상품명 체크 예시) 메인 상품 1개외 2건
		parameters.add("quantity", String.valueOf(quantity)); //상품 수량 체크
		parameters.add("total_amount", String.valueOf(totalAmount)); // 상품총액
		parameters.add("tax_free_amount", "0"); //상품 비과세 금액
		parameters.add("approval_url", "http://localhost:9090/user/order/orderApproval"); //결제 성공 redirect url
		parameters.add("cancel_url", "http://localhost:9090/user/order/orderCancel"); //결제 취소 redirect url
		parameters.add("fail_url", "http://localhost:9090/user/order/orderFail"); //결제 실패 redirect url
		
		//HttpEntity<T> 클래스 : Http header와 Http Body를 포함하는 클래스
		//HttpEntity<T> 클래스를 상속받아 구현한 클래스가 있다 -> RequestEntity / ResponseEntity
		//ResponseEntity 클래스 : Httpheader, HttpBody, HttpStatus 3가지 정보를 관리한다.
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());
		
		//RestTemplate? Spring에서 지원. http통신에 사용하는 기능을 제공한다.
		//Http서버와 통신을 단순화하고, Restfull 원칙을 지킨다.
		// 특징? 기계적이고, 반복적인 코드를 최대한 줄여줌, Restful형식, 데이타 : json or xml 형태로 응답을 받아, 쉽게 사용이 가능하다.
		
		//외부 URL 통신
		RestTemplate template = new RestTemplate();
		String url = "https://kapi.kakao.com/v1/payment/ready";		
		//첫번째 요청.
		//메인(핵심). 카카오페이에서 응답해준 json데이타를 객체로 변환해서 받음.
		ReadyResponse readyResponse = template.postForObject(url, requestEntity, ReadyResponse.class);		
		
		return readyResponse;
		
	}
	
	// 두번째 요청 : 결제 승인요청 메서드
	public ApproveResponse payApprove(String tid, String pgToken, String mem_id) {
		
		String order_id = "100";
		
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.add("cid", "TC0ONETIME"); //테스트 가맹점 id
		parameters.add("tid", tid); //카카오페이에서 보내준 결제고유 ID
		parameters.add("partner_order_id", order_id); // 주문번호
		parameters.add("partner_user_id", mem_id); // 주문자
		parameters.add("pg_token", pgToken); //토큰값
		
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

		//외부 URL 통신
		RestTemplate template = new RestTemplate();
		String url = "https://kapi.kakao.com/v1/payment/approve";
		
		//두번째 요청 (결제 승인)
		//메인(핵심). 카카오페이에서 응답해준 json데이타를 객체로 변환해서 받음.
		ApproveResponse readyresponse = template.postForObject(url, requestEntity, ApproveResponse.class);
		
		return readyresponse;
		
		
	}
	
	//kakao pay requset Header 정보 설정.
	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "kakaoDevel에서 발급한 앱키값");
		headers.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		return headers;
	}
	
	
	
	
	
}