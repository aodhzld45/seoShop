package com.seofriends.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.seofriends.kakaopay.KaKaoApi;
import com.seofriends.kakaopay.KakaoApiService;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class KakaoController {
	
	private final KaKaoApi kaKaoApi;
	
	@Autowired
	public KakaoController(KaKaoApi kaKaoApi) {
		this.kaKaoApi = kaKaoApi;
	}

	@GetMapping("/callback")
	public String getAccessToken(@RequestParam("code") String code) {
		
		// Access Token 요청 부분
		// 1. header 생성
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE,  "application/x-www-form-urlencoded");
        
        // 2. Body 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kaKaoApi.getRestApiKey());
        params.add("redirect_uri", kaKaoApi.getRedirectUri());
        params.add("code", code);
        

        // 3. header + body 작성
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, httpHeaders);
        
        // 4. http 요청하기        
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object> response = restTemplate.exchange(
        		"https://kauth.kakao.com/oauth/token",
        		HttpMethod.POST,
        		httpEntity,
        		Object.class
        );
        
        System.out.println(response.getBody().toString());
        
		
		return "redirect:/";
		
	}
	
	
}
