package com.seofriends.dto;

import java.util.List;

import com.seofriends.domain.ReplyVO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor //모든 필드를 파라미터로 하는 생성자 메서드
public class ReplyPageDTO {

	private int replyCnt; //댓글 페이징 정보를 구성하기 위한 데이터 개수

	private List<ReplyVO> list; //댓글 목록.
	
	/*
	public ReplyPageDTO(int replyCnt, List<ReplyVO> list) {
		super();
		this.replyCnt = replyCnt;
		this.list = list;
	}
	*/

}
