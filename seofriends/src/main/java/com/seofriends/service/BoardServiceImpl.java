package com.seofriends.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.seofriends.domain.BoardVO;
import com.seofriends.dto.Criteria;
import com.seofriends.mapper.BoardMapper;
import com.seofriends.mapper.ReplyMapper;
import com.seofriends.util.BoardFileUtils;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
@Log4j
@Service
public class BoardServiceImpl implements BoardService {
	
	@Setter(onMethod_= {@Autowired})
	private BoardMapper boardmapper;
	
	@Setter(onMethod_= {@Autowired})
	private ReplyMapper replymapper;
	
	@Resource(name="boardfileutils")
	private BoardFileUtils boardfileutils;
	
	// 게시글 작성 + 첨부파일 업로드
	@Transactional
	@Override
	public void write(BoardVO boardVO, MultipartHttpServletRequest mpRequest) throws Exception {
		boardmapper.insert(boardVO);
		
		List<Map<String, Object>> list = boardfileutils.parseInsertFileInfo(boardVO, mpRequest);
		
		int size = list.size();
		for(int i=0; i<size; i++){ 
			boardmapper.insertFile(list.get(i)); 
		}
	}

//	첨부파일 조회
	@Override
	public List<Map<String, Object>> getFileList(Long brd_num) {
		
		return boardmapper.getFileList(brd_num);
	}
	

	
//	게시판 리스트
	@Override
	public List<BoardVO> getList() {
		return boardmapper.getList();
	}

//	게시판 페이징
	@Override
	public List<BoardVO> getListWithPaging(Criteria cri) {
		return boardmapper.getListWithPaging(cri);
	}

//	게시판 테이블 Count
	@Override
	public int getTotalCount(Criteria cri) {
		return boardmapper.getTotalCount(cri);
	}

//	게시판 글 정보 얻어오기
	@Override
	public BoardVO get(Long brd_num) {
		return boardmapper.get(brd_num);
	}

//	게시판 글 수정
	@Transactional
	@Override
	public void update(BoardVO b_vo, String[] files, String[] fileNames, MultipartHttpServletRequest mpRequest)
			throws Exception {
		
		boardmapper.update(b_vo);
		
		List<Map<String, Object>> list = boardfileutils.parseUpdateFileInfo(b_vo, files, fileNames, mpRequest);
		Map<String, Object> tempMap = null;
		int size = list.size();
		for (int i = 0; i < size; i++) {
			tempMap = list.get(i);
			if (tempMap.get("IS_NEW").equals("Y")) {
				boardmapper.insertFile(tempMap);
			}else {
				boardmapper.updateFile(tempMap);
			}	
		}	
	}

//	게시판 글삭제
	@Override
	public void delete(Long brd_num) {
		
		boardmapper.delete(brd_num);
	}

//	첨부파일 다운로드
	@Override
	public Map<String, Object> selectFileInfo(Map<String, Object> map) throws Exception {
		return boardmapper.selectFileInfo(map);
	}












	




	

}
