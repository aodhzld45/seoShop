package com.seofriends.kakao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KakaoApiService {
	
	private final KaKaoApi kaKaoApi;
	
	@Autowired
	public KakaoApiService(KaKaoApi kaKaoApi) {
		this.kaKaoApi = kaKaoApi;
	}
	
    public String getAuthorizeUrl() {
        return String.format("https://kauth.kakao.com/oauth/authorize?client_id=%s&redirect_uri=%s&response_type=code", kaKaoApi.getRestApiKey(), kaKaoApi.getRedirectUri());
    }
	
}
