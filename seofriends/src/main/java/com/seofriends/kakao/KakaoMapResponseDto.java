package com.seofriends.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@Getter
@Slf4j
public class KakaoMapResponseDto {
//	도로명 주소 response Data - road_address
	@JsonProperty("address_name")
	public String addressName; // 도로명 주소 전체
	
	@JsonProperty("region_1depth_name")
	public String region1DepthName; // 지역 첫번째 - ex) 경기
	
	@JsonProperty("region_2depth_name")
	public String region2DepthName;  // 지역 두번째 - ex) 안성시
	
	@JsonProperty("region_3depth_name")
	public String region3DepthName; // 지역 세번째 - ex) 죽산면
	
	@JsonProperty("road_name")
	public String roadName; // 도로명
	
	@JsonProperty("underground_yn")
	public String undergroundYn; // 지하 여부
	
	@JsonProperty("main_building_no")
	public String main_building_no; // 건물 본번
	
	@JsonProperty("sub_building_no") // 건물 부번, 없을 경우 빈 문자열("") 반환
	public String subBuildingNo;
	
	@JsonProperty("building_name")
	public String buildingName; // 건물 이름
	
	@JsonProperty("zone_no")
	public String zoneNo; // 우편번호 (5자리)
	

}
