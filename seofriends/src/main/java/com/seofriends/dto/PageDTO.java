package com.seofriends.dto;

import lombok.Data;

// [이전] 1 2 3 4 5 [다음] 형태를 만들기위한 정보.
/*
 * 			1	2	3	4	5		[다음] -첫번째 블럭
 * [이전]		6	7	8	9	10	-두번째 블럭.
 * 
 * 
 * */
@Data
public class PageDTO {
	
	private int startPage; //각 블럭의 시작페이지 번호저장.
	private int endPage; //각 블럭의 마지막페이지 번호저장.
//	startPage endPage prev next
	
//	이전, 다음 표시여부.
	private boolean prev, next;
	
//	총 데이타 개수
	private int total;
	private Criteria cri; //페이징 정보와 검색 필드가 있음. page, amount, type, keyword

//	매개변수가 있는 생산자 정의
	public PageDTO(Criteria cri, int total ) {
		this.total = total;
		this.cri = cri;
		
		int pageSize = 10; 
		int endPageInfo = pageSize - 1; // 5-1 = 4 - 고정
		
//		pageNum이 1~5범위에 해당되면 endPage값이 전부 동일하다. 1		2	3	4	5
//		pageNum이 6이면, endPage의 변수의 값이 다르게 된다. 6	7	8	9	10
//		(int) (ceil(1 / 5.0)) * 5, (ceil(2 / 5.0))
		this.endPage = (int) ((Math.ceil(cri.getPageNum() / (double)pageSize)) * pageSize);
		this.startPage = this.endPage - endPageInfo; //10 - 4 = 6
		
//		실제 데이터수를 사용한 전체페이지수를 구한다.
//		math.ceil를 이용하여 나머지를 반올림하여 계산한다.
		int realEnd = (int) (Math.ceil((total * 1.0) / cri.getAmount()));
		
		if (realEnd <= this.endPage) {
			this.endPage = realEnd;
		}
		
//		이전, 다음 표시여부
		this.prev = this.startPage > 1; // 이전 표시
		this.next = this.endPage < realEnd; //다음 표시(전체 페이지수가 마지막페이지보다 클경우)
	
	}
}