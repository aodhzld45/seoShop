package com.seofriends.kakao;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoApiService {
	
	private final KaKaoApi kaKaoApi;
	
	private final String KAUTH_TOKEN_URL_HOST;
	
    private final String KAUTH_USER_URL_HOST;
    
    @Value("${kakao.rest.api.key}")
	private String restApiKey;
    
    @Value("${kakao.redirect.uri}")
	private String redirectUri;
    
    
	@Autowired
	public KakaoApiService(KaKaoApi kaKaoApi) {
		this.kaKaoApi = new KaKaoApi();
		kaKaoApi.getRestApiKey(); // client_id 값 셋팅.
		KAUTH_TOKEN_URL_HOST = "https://kauth.kakao.com/oauth/token";
		KAUTH_USER_URL_HOST = "https://kapi.kakao.com/v2/user/me";
	}
	
	// 인가 코드를 받는 getAuthorizeUrl 메서드
    public String getAuthorizeUrl() {
        return String.format("https://kauth.kakao.com/oauth/authorize?client_id=%s&redirect_uri=%s&response_type=code", restApiKey, redirectUri);
    }
    
    
    // 허용 토큰을 받는 getAccessTokenFromKakao 메서드
    public String getAccessTokenFromKakao(String code) {
    	
    	// Access Token 요청 부분
		// 1. header 생성
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE,  "application/x-www-form-urlencoded");
        
        // 2. Body 생성
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        
        requestBody.add("grant_type", "authorization_code");
        requestBody.add("client_id", restApiKey);
        requestBody.add("redirect_uri", redirectUri);
        requestBody.add("code", code);
        

        // 3. header + body 작성
        HttpEntity<MultiValueMap<String, String>> requestEntity  = new HttpEntity<>(requestBody, httpHeaders);
        
        // 4. http 요청하기        
        RestTemplate restTemplate = new RestTemplate();
        
        ResponseEntity<KakaoTokenResponseDto> responseEntity  = restTemplate.exchange(
        		KAUTH_TOKEN_URL_HOST,
        		HttpMethod.POST,
        		requestEntity,
        		KakaoTokenResponseDto.class
        );
        
        // 5. 응답 처리
        KakaoTokenResponseDto kakaoTokenResponseDto = responseEntity.getBody();
        
        if (kakaoTokenResponseDto == null) {
            throw new RuntimeException("토큰 발급에 실패하였습니다.");
        }
        
        // kakaoTokenResponseDto 전체 출력
        log.info("kakaoTokenResponseDto = {} " + kakaoTokenResponseDto);
        
        // 로그 출력
        System.out.println(" [Kakao Service] Access Token ------> " +  kakaoTokenResponseDto.getAccessToken());
        System.out.println(" [Kakao Service] Refresh Token ------> " + kakaoTokenResponseDto.getRefreshToken());
        System.out.println(" [Kakao Service] Id Token ------> " + kakaoTokenResponseDto.getIdToken());
        System.out.println(" [Kakao Service] Scope ------> " +  kakaoTokenResponseDto.getScope());
        

		return kakaoTokenResponseDto.getAccessToken();
		
  }
    
    // 사용자의 정보를 가져오는 getUserInfo 메서드
    
    public KakaoUserInfoResponseDto getUserInfo(String accessToken) {
    	
		// 1. header 생성
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE,  "application/x-www-form-urlencoded;charset=utf-8");
        httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken); // access token 인가
               
        // 2. http 요청하기        
        RestTemplate restTemplate = new RestTemplate();
        
        // 3.
        HttpEntity<HashMap<String, Object>> httpEntity = new HttpEntity<HashMap<String,Object>>(httpHeaders);
        
       
        ResponseEntity<KakaoUserInfoResponseDto> responseEntity  = restTemplate.exchange(
        			KAUTH_USER_URL_HOST,
        			HttpMethod.POST,
        			httpEntity,
        			KakaoUserInfoResponseDto.class
        );
        
    	KakaoUserInfoResponseDto userInfo = responseEntity.getBody();
    	
    	System.out.println("[ Kakao Service ] Auth ID ---> {} " + userInfo.getId());
    	System.out.println("[ Kakao Service ] NickName ---> {} " + userInfo.getKakaoAccount().getProfile().getNickName());
    	System.out.println("[ Kakao Service ] Email ---> {} " + userInfo.getKakaoAccount().getEmail());
    	System.out.println("[ Kakao Service ] Gender ---> {} " + userInfo.getKakaoAccount().getGender());
    	System.out.println("[ Kakao Service ] BirthDay ---> {} " + userInfo.getKakaoAccount().getBirthDay());

		return userInfo;
    }
    
    
    // kakao 지도 Map API get    
    public String  getKakaoMapAddress(double lat, double lon) {
    	
        String url = "https://dapi.kakao.com/v2/local/geo/coord2address.json?x=" + lon + "&y=" + lat;
    	
    	HttpHeaders headers = new HttpHeaders();
    	headers.add("Authorization", "KakaoAK " + restApiKey);
    	
        RestTemplate restTemplate = new RestTemplate();
        
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
        		url,
        		HttpMethod.GET,
        		httpEntity,
        		String.class
        	);
        
        System.out.println(response.getBody());
        
        
        if (response.getStatusCodeValue() == 200) {
            return response.getBody();

        } else {
            throw new RuntimeException("Failed to get address from coordinates");
        }
        
    }
    
    
    

    	
    
    
    
    
    
    
    
    
    
    
    
    	
}