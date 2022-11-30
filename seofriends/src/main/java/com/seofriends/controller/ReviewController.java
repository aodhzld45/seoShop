package com.seofriends.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seofriends.domain.MemberVO;
import com.seofriends.domain.ReviewVO;
import com.seofriends.dto.Criteria;
import com.seofriends.dto.PageDTO;
import com.seofriends.service.ReviewService;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@RequestMapping("/user/review/*")
@RestController //ResponseBody + controller ajax이용 json
public class ReviewController {
	@Setter(onMethod_= {@Autowired} )
	private ReviewService reviewservice;
	
	
	
//	상품 후기 쓰기
//	consumes : 클라이언트에서 보내는 데이타 타입 저장.
//	produces : 서버에서 클라이언트에게 보내는 데이타 타입을 지정한다.

	@PostMapping("/new")
	public ResponseEntity<String> create(@RequestBody ReviewVO vo, HttpSession session){
		
		ResponseEntity<String> entity = null;
		
		log.info("상품 후기 : " + vo);
		
		String mem_id = ((MemberVO) session.getAttribute("loginStatus")).getMem_id();

		vo.setMem_id(mem_id);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		reviewservice.create(vo);

		return entity;
	}
	
//	상품 후기 수정
	
//	상품 후기 삭제
	
//	상품 후기 목록 : 목록 + 페이징 정보(2개의 데이타) -> JSON 클라이언트에게 보내는 작업.
	@GetMapping(value = "/list/{pdt_num}/{page}")
	public ResponseEntity<Map<String, Object>> reviewList(@PathVariable("pdt_num") Integer pdt_num, @PathVariable("page") Integer page){
		
		ResponseEntity<Map<String, Object>> entity = null;
		Map<String, Object> map = new HashMap<String, Object>();
		
		Criteria cri = new Criteria(); //검색 필드가 필요없으므로, 메서드의 파라미터로 사용하지 않고, 객체를 직접 생성한다.
		cri.setPageNum(page);
		
//		1)댓글목록
		List<ReviewVO> list = reviewservice.list(pdt_num, cri);
		map.put("list", list);
		
//		2)페이지 정보
		PageDTO pageMaker = new PageDTO(cri, reviewservice.listCount(pdt_num));
		map.put("pageMaker", pageMaker);
		
		entity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
		
		return entity;
		
	}
	
	//상품 후기 수정
	
	@PatchMapping(value = "/modify", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> modify(@RequestBody ReviewVO vo, HttpSession session){
		
		ResponseEntity<String> entity = null;
		
		log.info("상품 후기 수정 : " + vo);
		
		String mem_id = ((MemberVO) session.getAttribute("loginStatus")).getMem_id();

		vo.setMem_id(mem_id);
		
		reviewservice.modify(vo);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);

		return entity;
	}
	
	@DeleteMapping(value = "/delete/{rv_num}", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> delete(@PathVariable("rv_num") Integer rv_num){
		ResponseEntity<String> entity = null;
		
		//상품 삭제 서비스 추가
		reviewservice.delete(rv_num);

		entity = new ResponseEntity<String>("success", HttpStatus.OK);

	
		return entity;
		
		
	} 
	
	
	
	

}
