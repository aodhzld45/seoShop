package com.seofriends.domain;

import java.util.Date;

import lombok.Data;

/*
	테이블 구조 컬럼 - rno, brd_num, reply, replyer
	VO : Value Object 클래스
	방법1 : 테이블 구조의 컬럼과 클래스의 필드명을 일치시키는것.
	방법2 : 테이블의 컬럼을 클래스의 필드명으로 사용하기가 적절하지 않은 경우.
	-컬럼을 별칭으로 사용, Mybatis의 resultMap을 사용한다.
*/
@Data
public class ReplyVO {
	private Long rno;
	private Long brd_num;
	
	private String reply;
	private String replyer;
	private Date replydate;
	private Date updatedate;
	
}
