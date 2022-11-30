package com.seofriends.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.seofriends.domain.BoardVO;
import com.seofriends.dto.Criteria;

public interface BoardService {
	
	//글 등록
	void write(BoardVO boardVO, MultipartHttpServletRequest mpRequest) throws Exception;
	
	//첨부파일 업로드
//	void insertFile(Map<String, Object> map) throws Exception;
	
	//첨부파일 조회
	List<Map<String, Object>> getFileList(Long brd_num);
	
	//첨부파일 다운로드
	public Map<String, Object> selectFileInfo(Map<String, Object> map) throws Exception;	
	
	
	
	//게시판 리스트
	List<BoardVO> getList();
	// Criteria클래스에서 검색필드 4개 사용(pageNum, amount, type, keyword, typeArr)
	List<BoardVO> getListWithPaging(Criteria cri);
	
	//Criteria클래스에서 검색필드 3개만 사용(type, keyword, typeArr)
	int getTotalCount(Criteria cri);
	
	BoardVO get(Long brd_num);
	
	//게시물 수정	
	void update(BoardVO b_vo,
				String[] files,
				String[] fileNames,
				MultipartHttpServletRequest mpRequest) throws Exception;
	
	void delete(Long brd_num);
}
