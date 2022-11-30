package com.seofriends.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seofriends.domain.ReplyVO;
import com.seofriends.dto.Criteria;
import com.seofriends.dto.PageDTO;
import com.seofriends.dto.ReplyPageDTO;
import com.seofriends.service.ReplyService;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

/*
 컨트롤러 클래스는 jsp파일이 사용안한다. @RestController 사용시.
 모든 매핑주소는 ajax요청으로 사용된다.
 */

@RequestMapping("/replies/*")
@RestController	// @Controller+@ResponseBody  리턴값을 반환. 객체를 json으로 변환해서 반환.
@Log4j
public class ReplyController {

	/*
	 REST 설계구현 
	 1)	@RestController
	 2)	ResponseEntity클래스? 1)결과값(리턴값) 2)헤더작업 3)HTTP상태코드
	 3)	주소 : 자원을 나타내는 의미로 정의
	 4) 데이타 생성 주소 : Post, 데이타 조회 주소 : Get, 데이타 삭제 주소 : Delete, 데이타 수정 주소 : Put
	 5) JSP와 같은 뷰를 리턴하는 것이 아니라, 데이타 그 자체를 리턴. -> 다양한 클라이언트 기기에 표준 데이타를 제공하여 서비스함.
	 
	  3,4,5 REST 이론.
	 */
	
	@Setter(onMethod_= {@Autowired})
	private ReplyService replyservice;
	
	//댓글저장하기. 댓글데이타는 JSON문자열 형식으로 전송되어옴.
	// {"bno":5120,"replyer":"user01","reply":"댓글테스트"} --> ReplyVO vo ( @RequestBody 사용해야 함)
	// consumes = "application/json" : 클라이언트에서 보내는 데이타의 성격을 명시하는 구문.
	// produces = {MediaType.TEXT_PLAIN_VALUE} : 서버에서 클라이언트에게 보내는 데이타의 포맷을 명시.
	@PostMapping(value = "/new", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> create(@RequestBody ReplyVO r_vo) {
		ResponseEntity<String> entity = null;
		
		int count = replyservice.insert(r_vo);

		return count == 1 ? new ResponseEntity<String>("success", HttpStatus.OK)
							: new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	//1)댓글목록데이타, 2)페이징정보를 JSON포맷으로 클라이언트에게 리턴해주는 작업
	// 주소: /pages/{bno}/{page}. 주소의 일부분을 파라미터값으로 사용하고자 할경우
	// 예>  /pages/5120/1
	// 테스트주소:  http://localhost:9090/replies/pages/5120/1.json
	@GetMapping(value = "/pages/{brd_num}/{page}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<Map<String, Object>> getList(@PathVariable("brd_num") Long brd_num, @PathVariable("page") int page){
		ResponseEntity<Map<String, Object>> entity = null;
		Map<String, Object> map = new HashMap<String, Object>();
		
		//1)댓글목록작업. 클릭된 번호 page
		Criteria cri = new Criteria(page, 5);
		ReplyPageDTO replyObj = replyservice.getListPage(cri, brd_num); // 1)댓글목록, 2)본문글을 참조하는 댓글데이타 개수
			
		map.put("list", replyObj.getList());
		
		//2)페이징정보작업
		PageDTO pageDTO = new PageDTO(cri, replyObj.getReplyCnt());
		map.put("pageMaker", pageDTO);
		
		entity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
				
		return entity;
	}
	
	//일반적인 웹브라우저는 get, post방식만 지원한다.
	@PutMapping(value = "/modify/{rno}")
	public ResponseEntity<String> update(@PathVariable("rno") Long rno, @RequestBody ReplyVO r_vo){
		ResponseEntity<String> entity = null;
		
		replyservice.modifyReply(r_vo);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	@DeleteMapping(value = "/delete/{rno}")
	public ResponseEntity<String> delete(@PathVariable("rno") Long rno){
		
		ResponseEntity<String> entity = null;
		
		replyservice.deleteReply(rno);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
}
