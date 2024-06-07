package com.seofriends.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	public ResponseEntity<?>  getAccessToken(@RequestParam("code") String code) {
		
		String accessToken = kakaoApiService.getAccessTokenFromKakao(code);
		
		// 발급 받은 Access Token을 Bearer Token으로 인증.
		KakaoUserInfoResponseDto userInfo = kakaoApiService.getUserInfo(accessToken); 
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	
	
	
	
	
	
	
	

}
