package com.seofriends.kakao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Partner {
	
	// 고유 ID 카카오톡 메시지 API 사용 권한이 있는 경우에만 제공
	@JsonProperty("uuid")
	public String uuid;

}
