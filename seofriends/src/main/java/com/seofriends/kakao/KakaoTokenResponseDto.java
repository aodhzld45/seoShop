package com.seofriends.kakao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor // 역 직렬화를 위한 기본 생성자
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoTokenResponseDto {
	/*
	 * 
	 *  Request DTO 
		grant_type	String	authorization_code로 고정	O
		client_id	String	앱 REST API 키
		[내 애플리케이션] > [앱 키]에서 확인 가능	O
		redirect_uri	String	인가 코드가 리다이렉트된 URI	O
		code	String	인가 코드 받기 요청으로 얻은 인가 코드	O
		client_secret	String	토큰 발급 시, 보안을 강화하기 위해 추가 확인하는 코드
		[내 애플리케이션] > [카카오 로그인] > [보안]에서 설정 가능
		ON 상태인 경우 필수 설정해야 함
	*/
	
	@JsonProperty("token_type")
	public String tokenType; //토큰 타입, bearer로 고정
	
	@JsonProperty("access_token")
	public String accessToken; // 사용자 액세스 토큰 값
	
	@JsonProperty("id_token")
	public String idToken; // ID 토큰 값 OpenID Connect 확장 기능을 통해 발급되는 ID 토큰, Base64 인코딩 된 사용자 인증 정보 포함
	
	@JsonProperty("expires_in")
	public Integer expiresIn; // 액세스 토큰과 ID 토큰의 만료 시간(초)

	@JsonProperty("refresh_token")
	public String refreshToken; // 사용자 리프레시 토큰 값
	
	@JsonProperty("refresh_token_expires_in")
	public Integer refreshTokenExpiresIn; // 리프레시 토큰 만료 시간(초)	
	
	@JsonProperty("scope")
	public String scope; // 인증된 사용자의 정보 조회 권한 범위 범위가 여러 개일 경우, 공백으로 구분
	
	
}
