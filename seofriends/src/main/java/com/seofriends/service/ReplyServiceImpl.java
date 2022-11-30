package com.seofriends.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seofriends.domain.ReplyVO;
import com.seofriends.dto.Criteria;
import com.seofriends.dto.ReplyPageDTO;
import com.seofriends.mapper.ReplyMapper;

@Service
public class ReplyServiceImpl implements ReplyService {

	@Autowired
	private ReplyMapper replymapper;

	@Transactional
	@Override
	public int insert(ReplyVO r_vo) {

		//게시판에 replycnt컬럼에 댓글카운트작업
		replymapper.replyCountUpdate(r_vo.getBrd_num());
		//댓글쓰기
		return replymapper.insert(r_vo);
	}

	@Override
	public ReplyPageDTO getListPage(Criteria cri, Long brd_num) {
		// mapper.getCountByBno(bno): 댓글데이타 개수, mapper.getListWithPaging(cri, bno) : 댓글목록
		return new ReplyPageDTO(replymapper.getCountByBno(brd_num), replymapper.getListWithPaging(cri, brd_num));
	}

	@Override
	public int modifyReply(ReplyVO r_vo) {
		return replymapper.update(r_vo);
	}

	@Override
	public int deleteReply(Long rno) {
		
		return replymapper.delete(rno);
	}

}
