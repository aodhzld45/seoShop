package com.seofriends.kakao;

import java.util.Date;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
/*
 * 
이름	타입	설명	필수
id	Long	회원번호	O
has_signed_up	Boolean	자동 연결 설정을 비활성화한 경우만 존재
연결하기 호출의 완료 여부
false: 연결 대기(Preregistered) 상태
true: 연결(Registered) 상태	X

connected_at	Datetime	서비스에 연결 완료된 시각, UTC*	X
synched_at	Datetime	카카오싱크 간편가입을 통해 로그인한 시각, UTC*	X
properties	JSON	사용자 프로퍼티(Property)
사용자 프로퍼티 참고	X
kakao_account	KakaoAccount	카카오계정 정보	X
for_partner	Partner	uuid 등 추가 정보	X
 * 
 * */



public class KakaoUserInfoResponseDto {

// KakaoTokenResponseDto에서 받아온 토큰으로 사용자 정보 가져오기
	// 회원 번호
	@JsonProperty("id")
	public Long id;
	
	// 자동 연결 설정을 비활성화한 경우만 존재 - true : 연결(Registered) 상태  / false : 연결 대기(Preregistered) 상태
	@JsonProperty("has_signed_up")
	public Boolean hasSignedUp;
	
	// 	서비스에 연결 완료된 시각, UTC
	@JsonProperty("connected_at")
	public Date connectedAt;
	
	// 카카오싱크 간편가입을 통해 로그인한 시각, UTC*
	@JsonProperty("synched_at")
	public Date synchedAt;
	
	// 사용자 프로퍼티 : json -> HashMap
	@JsonProperty("properties")
	public HashMap<String, Object> properties;
	
	// 카카오 계정 정보
	@JsonProperty("kakao_account")
	public KakaoAccount KakaoAccount;
	
    //uuid 등 추가 정보
    @JsonProperty("for_partner")
    public Partner partner;
    
}

