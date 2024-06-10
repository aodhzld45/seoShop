package com.seofriends.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.seofriends.domain.MemberVO;
import com.seofriends.kakao.KaKaoApi;
import com.seofriends.kakao.KakaoApiService;
import com.seofriends.kakao.KakaoUserInfoResponseDto;
import com.seofriends.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class KakaoController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private KaKaoApi kaKaoApi;

	@Autowired
	private KakaoApiService kakaoApiService;

//	STEP 1 : 인가 코드 받기
	@GetMapping("/kakao/authorize-url")
	public ResponseEntity<String> getAuthorizeUrl() {
		String authorizeUrl = kakaoApiService.getAuthorizeUrl();

		return ResponseEntity.ok(authorizeUrl);
	}

//	STEP 2 : Access Token 발급 받기 + 사용자 정보 가져오기
	@GetMapping("/callback")
	public ResponseEntity<?>  getAccessToken(@RequestParam("code") String code, HttpServletResponse res, RedirectAttributes rttr) throws IOException {
		
		String accessToken = kakaoApiService.getAccessTokenFromKakao(code);
		
		// 발급 받은 Access Token을 Bearer Token으로 인증.
		KakaoUserInfoResponseDto userInfo = kakaoApiService.getUserInfo(accessToken); 
		
		MemberVO memberVO = new MemberVO();
		memberVO.setMem_id(userInfo.getId().toString());
		memberVO.setMem_name(userInfo.getKakaoAccount().getProfile().getNickName());
		memberVO.setMem_pw("");
		memberVO.setMem_email(userInfo.getKakaoAccount().getEmail());
		memberVO.setMem_zipcode("");
		memberVO.setMem_addr("");
		memberVO.setMem_addr_d("");
		memberVO.setMem_phone("");		
		memberVO.setMem_nick(userInfo.getKakaoAccount().getProfile().getNickName());
		memberVO.setMem_accept_e("N");
		memberVO.setMem_point(0);
		memberVO.setMem_authcode("Y");
		
		System.out.println("memberVO에 Setting된 값 = " + memberVO);
		
		String isuserID = "";
		// 카카오 회원가입 MemberService Join
		if (memberService.IdCheck(memberVO.getMem_id()) != null) {			
			res.sendRedirect("http://localhost:9090/member/login");
			isuserID = "N";
		}else {
			isuserID = "Y";
			memberService.join(memberVO);
			res.sendRedirect("http://localhost:9090");
		}
		
		// 카카오 로그인 + 기존의 MemberService Login
		rttr.addFlashAttribute("isuserID", isuserID);
		
		return new ResponseEntity<>(HttpStatus.OK); 
		
	}
	
	
// 카카오 지도 API 테스트
	@GetMapping("/kakao/map")
	public ResponseEntity<?> getKakaoMapAddress(
			@RequestParam("longitude") Double longitude,
			@RequestParam("latitude") Double latitude
			) {
		
		
		kakaoApiService.getKakaoMapAddress(longitude, latitude);
		
		return new ResponseEntity<>(HttpStatus.OK); 
	}

	
	
	
	
	
	
	
	
	
	
	

}
