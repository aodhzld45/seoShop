package com.seofriends.service;

import com.seofriends.domain.ReplyVO;
import com.seofriends.dto.Criteria;
import com.seofriends.dto.ReplyPageDTO;

public interface ReplyService {

	int insert(ReplyVO r_vo);
	
	ReplyPageDTO getListPage(Criteria cri, Long brd_num);
	
	//List<ReplyVO> getListWithPaging(Criteria cri, Long brd_num);
	
	int modifyReply(ReplyVO r_vo);
	
	int deleteReply(Long rno);
}
