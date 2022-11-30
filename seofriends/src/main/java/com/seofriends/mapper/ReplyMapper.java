package com.seofriends.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.seofriends.domain.ReplyVO;
import com.seofriends.dto.Criteria;

public interface ReplyMapper {

	// mapper xml파일에서 insert, delete, update 구문이 실행이 반영된 데이타 행수를 리턴한다. 반환값 1
	int insert(ReplyVO r_vo);
	
	// Criteria cri : 페이징파라미터, Long bno : 게시판글번호(본문글)
	// MAPPER INTERFACE의 메서드 파라미터가 2개이상일 경우 @Param 어노테이션을 사용해야 한다.(중요)
	List<ReplyVO> getListWithPaging(@Param("cri") Criteria cri, @Param("brd_num") Long brd_num);
	
	// 본문글을 참조하는 댓글 데이터 개수
	int getCountByBno(Long brd_num);
	
	int update(ReplyVO r_vo);
	
	int delete(Long rno); // primary key로 삭제
	
	void deleteBybno(Long brd_num); // foreign key 컬럼값으로 삭제
	
	void replyCountUpdate(Long brd_num); //댓글 카운트 증가
	
	void replyCountDelete(Long brd_num); //댓글 카운트 감소

	
}
