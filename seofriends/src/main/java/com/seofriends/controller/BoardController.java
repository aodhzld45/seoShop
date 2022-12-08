package com.seofriends.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.seofriends.domain.BoardVO;
import com.seofriends.dto.Criteria;
import com.seofriends.dto.PageDTO;
import com.seofriends.service.BoardService;
import com.seofriends.util.uploadFileUtils;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Controller
@RequestMapping("/board/*")
public class BoardController {
	@Setter(onMethod_= {@Autowired})
	private BoardService boardservice;
	
	@Resource(name = "uploadPath")
	private String uploadPath; // "D://DEV//upload"
	
//	글 작성 get
	@GetMapping("/write")
	public void write() {
		log.info("getMapping write");
	}
	
//	Http 400 상태코드 : 클라이언트에서 보내는 데이터가 메서드의 파라미터에 적합하지 않을 경우에 발생.
//	글작성 post
	// 게시판 글 작성
	@PostMapping("/write")
	public String write(BoardVO boardVO, MultipartHttpServletRequest mpRequest) throws Exception{
		log.info("write");
		
		boardservice.write(boardVO, mpRequest);
		
		return "redirect:/board/list";
	}
	
	// 1	2	3	4	5	<a href="/board/list?pageNum=선택번호&amount=10">
	// 페이징 및 검색 기능 추가 리스트
	@GetMapping("/list") // 스프링이 1)Criteria cri = new Criteria(); 2)setter메서드가 동작
	public void list(BoardVO vo,Criteria cri, Model model) {
		log.info("list: " + cri);
//		1)jsp(뷰)에서 보여줄 목록 데이타 가져오기
		List<BoardVO> list = boardservice.getListWithPaging(cri);
		
//		\(역슬래쉬)를 /(슬래쉬)로 변환하는작업. \가 클라이언트에서 서버로 보내지는 데이터로 사용되지 않아 변환
//		for (int i = 0; i < list.size(); i++) {
//			String brd_file_folder = list.get(i).getBrd_file_folder().replace("\\", "/");// "/" 부분에 File.separator 운영체제 경로 구분자 (다른 os 예)Linux에 배포할때 사용)
//			list.get(i).setBrd_file_folder(brd_file_folder);
//		}
		
		//model.addAttribute("jsp파일명", list)
		model.addAttribute("list", list);
		
//		2)jsp(뷰)에서 페이징 기능 구현(PageDTO). 1 2 3 4 5 [다음]
		int total = boardservice.getTotalCount(cri);//테이블의 전체 갯수를 가져옴.
		log.info("total: "+ total);
		PageDTO pageDTO = new PageDTO(cri, total);
		model.addAttribute("pageMaker", pageDTO); //startPage, endPage, prev, next
	}
	

	
//	이미지 작업.
	@ResponseBody
	@GetMapping("/dlsplayFile")
	public ResponseEntity<byte[]> dlsplayFile(String folderName, String fileName){
		
		log.info("파일이름 = " + fileName);
		log.info("폴더이름 = " + folderName);
		
		
		//이미지를 바이트배열로 읽어오는 작업
		return uploadFileUtils.getFile(uploadPath, folderName + "\\" + fileName);
	}
	

	/*
	1) @ModelAttribute("cri") Criteria cri : cri파라미터로 전송되어 온 값을 JSP로 전달하여, 사용하고 자 할 경우.
	2) Model model : 메서드안에서 수동으로 데이타작업하여, JSP에 전달하고 자 할 경우. 예>대부분 DB내용을 JSP에 전달시 사용.
	*/
	// 게시물 읽기: get.jsp, 수정폼: modify.jsp
	@GetMapping(value = {"/get", "/modify"})
	public void get(@RequestParam("brd_num") Long brd_num, @ModelAttribute("cri") Criteria cri, Model model, BoardVO b_vo) {
		log.info("글번호 : " + brd_num );
		
//		댓글 목록 작업이 존재하지 않음. get.jsp에서 ajax로 진행이 되어있음
//		ajax로 사용하지 않을 경우 현재위치에서 댓글 목록 데이타를 가져와서 모델로 추가해서 jsp로 보낸다.
		
		BoardVO board = boardservice.get(brd_num); 
		
		model.addAttribute("board", board);

		List<Map<String, Object>> fileList = boardservice.getFileList(b_vo.getBrd_num());
		
		model.addAttribute("file", fileList);
		
		// 역슬래시(\\)를 슬래시(/)로 변환 후 모델로 추가작업
		log.info("==============================");
		
		//board.setBrd_file_folder(board.getBrd_file_folder().replace("\\", "/"));;
	
	}
	
	//파일 다운로드
	@RequestMapping(value ="/fileDown")
	public void fileDown(@RequestParam Map<String, Object> map, HttpServletResponse response) throws Exception{
		Map<String, Object> resultMap = boardservice.selectFileInfo(map);
		String storedFileName = (String) resultMap.get("STORED_FILE_NAME"); //컬럼정의
		String originalFileName = (String) resultMap.get("ORG_FILE_NAME");
		
		// 파일을 저장했던 위치에서 첨부파일을 읽어 byte[]형식으로 변환한다.
		byte fileByte[] = org.apache.commons.io.FileUtils.readFileToByteArray(new File("D:\\DEV\\upload\\"+storedFileName));
		
		response.setContentType("application/octet-stream");
		response.setContentLength(fileByte.length);
		response.setHeader("Content-Disposition",  "attachment; fileName=\""+URLEncoder.encode(originalFileName, "UTF-8")+"\";");
		response.getOutputStream().write(fileByte);
		response.getOutputStream().flush();
		response.getOutputStream().close();
		
	}
	
	
//	게시판 수정
	@PostMapping("/modify")
	public String modify(BoardVO b_vo,
			Criteria cri,
			RedirectAttributes rttr,
			@RequestParam("fileNoDel[]") String[] files,
			@RequestParam("fileNameDel[]") String[] fileNames,
			MultipartHttpServletRequest mpRequest) throws Exception {
	
		
		log.info("수정 내용:" + b_vo.toString());
		boardservice.update(b_vo, files, fileNames, mpRequest);
		
		// /board/list 주소에 4개의 파라미터 정보가 추가 되어진다.
		/*
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getPageNum());
		rttr.addAttribute("type", cri.getPageNum());
		rttr.addAttribute("keyword", cri.getPageNum());
		 */
		
		return "redirect:/board/list" + cri.getListLink();
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam("brd_num") Long brd_num, Criteria cri) {
		log.info("삭제할 글번호" + brd_num);
		
		boardservice.delete(brd_num);
		
		return "redirect:/board/list" + cri.getListLink();
	}
	

}
