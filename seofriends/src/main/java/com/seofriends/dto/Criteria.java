package com.seofriends.dto;

import org.springframework.web.util.UriComponentsBuilder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// 페이징정보필드 : 현재 페이지, 페이지마다 출력건수 
// 검색필드 : 검색종류, 검색어

@ToString
@Setter
@Getter
public class Criteria  {
//	1)	페이징 정보.
	private int pageNum; //현재페이지번호 	1	2	3	4	5
	private int amount;	//게시물 출력 개수
	
//	2)	검색 정보
	private String type; // 검색종류(상품명, 제조사, 상품명+제조사)
	private String keyword;// 검색어
	
//	기본 생성자 작업.
	public Criteria() {
		this(1, 10); //	다른 생성자 메서드 호출 -> 첫페이지와 보여줄 게시물 수를 명시
	}
//	매개변수가 있는 생성자 정의.
//	페이지당 게시물 10개씩 출력.
	public Criteria(int pageNum, int amount) {
		super();
		this.pageNum = pageNum; //	1
		this.amount = amount; //	10
	}
	
	//검색기능.xml mapper파일에서 사용될 메서드명. 메서드명의 규칙은 get이름() 형식이어야 한다.
		public String[] getTypeArr() {
			//   검색항목을 [상품명 - N or 제조사 - C] 선택시 전송온 값 :  "NC"
			return type == null? new String[] {} : type.split("");  // {"T", "W"}
		}
	
		//주소에 Criteria클래스 파라미터추가작업.  ?pageNum=값1&amount=값2&type=값3&keyword=값4
		public String getListLink() {
			
			// 메서드 체이닝문법.
			UriComponentsBuilder builder = UriComponentsBuilder.fromPath("")
					.queryParam("pageNum", this.pageNum)
					.queryParam("amount", this.getAmount())
					.queryParam("type", this.getType())
					.queryParam("keyword", this.getKeyword());

			return builder.toUriString();
		}
}