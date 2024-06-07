package com.seofriends.kakao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;

// 프로필 정보 관련 

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Profile {
	
	// 필요한 동의항목: 프로필 정보(닉네임/프로필 사진) 또는 닉네임
	@JsonProperty("nickname")
	public String nickName;
	
	// 프로필 미리보기 이미지 URL 110px * 110px 또는 100px * 100px	
	@JsonProperty("thumbnail_image_url")
	public String ThumbnailImageUrl;
	
	// 프로필 사진 URL 640px * 640px 또는 480px * 480px\
	@JsonProperty("profile_image_url")
	public String profileImageUrl;
	
	// 프로필 사진 URL이 기본 프로필 사진 URL인지 여부
	@JsonProperty("is_default_image")
	public Boolean isDefaultImage;
	
	// 닉네임이 기본 닉네임인지 여부
	@JsonProperty("is_default_nickname")
	public Boolean isDefaultNickname;
	

}
