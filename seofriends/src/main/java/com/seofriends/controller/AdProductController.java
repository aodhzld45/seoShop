
package com.seofriends.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.seofriends.domain.CategoryVO;
import com.seofriends.domain.ProductVO;
import com.seofriends.dto.Criteria;
import com.seofriends.dto.PageDTO;
import com.seofriends.service.AdProductService;
import com.seofriends.util.uploadFileUtils;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@RequestMapping("/admin/product/*")
@Controller
public class AdProductController {
/*		
	파일 업로드 bean 주입 = 파일정보나 자원등의 대한 bean정보를 참조할때 사용.
	Bean주에 uploadPath bean 객체를 찾아, 아래변수에 주입한다.
 */
	@Setter(onMethod_ = {@Autowired} )
	private AdProductService proservice;
	
	@Resource(name = "uploadPath")
	private String uploadPath; // "D://DEV//upload"
	
/* =====================================================================================================*/	
	


//	#상품등록 폼 : 1차 카테고리 정보를 불러옴
	@GetMapping("/adproductInsert")
	public void productInsert(Model model) {
		
		model.addAttribute("cateList", proservice.getCateList()); //jsp에서 참조하는 이름 cateList
	}
	
//	#2차 카테고리 불러오기 - @PathVariable : 경로주소의 일부분을 파라미터로 참조시 사용한다.
	@ResponseBody
	@GetMapping("/subCategoryList/{catecode}") //subCategoryList/1차 카테고리 코드 RestAPI활용. / RestAPI를 활용하지 않으면 쿼리스트링으로 url매핑.
	ResponseEntity<List<CategoryVO>> subCategoryList(@PathVariable("catecode") Integer catecode){
		
		ResponseEntity<List<CategoryVO>> entity = null;
		
		entity = new ResponseEntity<List<CategoryVO>>(proservice.getSubCategoryList(catecode), HttpStatus.OK);
		log.info(catecode);
		return entity;
	}
	
//	#CKedtior 웹에디터를 통한 이미지 업로드 작업 (상세설명에서 사용하는 설명 이미지 파일)
	@PostMapping("/imageUpload") // CKedtior : 업로드 <input type ="file" name= "upload"
	public void imageUpload(HttpServletRequest req, HttpServletResponse res, MultipartFile upload ) {
		
		//입출력 방식으로 파일 업로드 구현
		/*
		 *	request : 클라이언트가 서버에 접속하는 작업형태
		 * 	response : 서버에서 클라이언트에게 결과를 보낼때 담당하는 의미.
		 * 
		 * */
		OutputStream out = null; 
		PrintWriter pw = null;
		
		res.setCharacterEncoding("utf-8");
		res.setContentType("text/html; charset=utf-8");
		
		try {
			String fileName = upload.getOriginalFilename(); //클라이언트에서 업로드한 원본파일명.
			byte[] bytes = upload.getBytes(); //업로드 파일
//			서버측의 업로드 폴더 경로 작업.
//			톰캣이 실제관리하는 물리적인 경로.
//			1) 프로젝트 내부 server.xml 부분의 
			//<Context docBase="C:\\DEV\\upload\\ckeditor" path="/upload/" reloadable="true" /> 부분을 삭제
//			String uploadTomcatPath = req.getSession().getServletContext().getRealPath("/") + "resources\\upload\\";
			
//			2) 외부
			String uploadPath = "D:\\DEV\\upload\\ckeditor\\";
			
			/* aws 배포용 
			 * String uploadPath = "/usr/local/tomcat/tomcat9/webapps/temp";
			 */
		
			log.info("톰캣 물리적 경로" + uploadPath);
			
			uploadPath = uploadPath + fileName;
			
			out = new FileOutputStream(new File(uploadPath)); //파일 입출력스트림 객체 생성(실제 폴더에 파일이 생성됨 ) 0byte
			out.write(bytes); //출력 스트림에 업로드된 파일을 가리키는 바이트 배열을 쓴다. 업로드된 파일크기.
			
//			Ckdedtior 에게 보낼 파일 정보 작업.
			
			pw = res.getWriter();
			
//			클라이언트에서 요청할 이미지 주소 정보
			String fileUrl = "/upload/" + fileName;
			// {"filename":"abc.gif", "uploaded":1, "url":"/upload/abc.gif"} CKEditor 4.x version에서 요구하는  json포맷
						pw.println("{\"filename\":\"" + fileName + "\", \"uploaded\":1,\"url\":\"" + fileUrl + "\"}");
						pw.flush(); // 전송 (return과 같은 역할: 클라이언트로 보냄)

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (pw != null) {
				pw.close();
				
			}
		}		
	}
	
//	#상품 저장
	@PostMapping("/AdProductInsert")
	public String AdProductInsert(ProductVO p_vo,   RedirectAttributes rttr) {
//		VO에서 얻어온 데이터를 얻어오지 못하는 결과 - 로그가 출력이 되지않음. 400번 에러 클라이언트 -> 서버
		log.info("상품 등록정보: " + p_vo) ;
		
//		파일 업로드 작업.
//		vo.getPdt_img(); vo.getPdt_img() NULL 상태임.
//		이미지 파일명이 저장될 pdt_img필드에 업로드한 후 실제 파일명을 저장.
		String uploadDateFolderPath = uploadFileUtils.getFolder();
		p_vo.setPdt_img_folder(uploadDateFolderPath); //날짜 폴더명
		p_vo.setPdt_img(uploadFileUtils.uploadFile(uploadPath, uploadDateFolderPath, p_vo.getUploadFile())); //실제 업로드된 이미지파일이 저장이 됨.
		
//	상품정보저장.
		proservice.AdProductInsert(p_vo);
		return "redirect:/admin/product/adproductlist";
	}
	
//	상품 목록: 페이징, 검색기능 추가
	@GetMapping("/adproductlist") //Criteria cri = new Criteria();
	public void productlist(@ModelAttribute("cri") Criteria cri, Model model){
		log.info("테스트용 상품목록");
		log.info("검색 및 페이징 정보: " + cri);
		
		List<ProductVO> productList = proservice.getProductList(cri);
		
//		\(역슬래쉬)를 /(슬래쉬)로 변환하는작업. \가 클라이언트에서 서버로 보내지는 데이터로 사용되지 않아 변환
		for (int i = 0; i < productList.size(); i++) {
			String Pdt_img_folder = productList.get(i).getPdt_img_folder().replace("\\", "/");// "/" 부분에 File.separator 운영체제 경로 구분자 (다른 os 예)Linux에 배포할때 사용)
			productList.get(i).setPdt_img_folder(Pdt_img_folder);
		}
		
//		페이징쿼리에 의한 상품목록.
		model.addAttribute("productList", productList);
		
//		[prev]	1	2	3	4	5 [prev]
		int totalCount = proservice.getProductTotalCount(cri);
		log.info("getProductTotalCount : " + totalCount);
		model.addAttribute("pageMaker", new PageDTO(cri, totalCount));	
	}
	
//	상품목록 이미지 작업.
	@ResponseBody
	@GetMapping("/dlsplayFile")
	public ResponseEntity<byte[]> dlsplayFile(String folderName, String fileName){
		
		log.info("파일이름 = " + fileName);
		log.info("폴더이름 = " + folderName);
		
		
		//이미지를 바이트배열로 읽어오는 작업
		return uploadFileUtils.getFile(uploadPath, folderName + "\\" + fileName);
	}
	
//	상품 수정 폼
	@GetMapping("/adproductModify")
	public void productModify(@RequestParam("pdt_num") Integer pdt_num, @ModelAttribute("cri") Criteria cri,
			Model model) {
		log.info("상품코드 : " +  pdt_num);
		log.info("페이징정보  : " + cri);
		
//		1차 카테고리를 가져오는 작업
		model.addAttribute("cateList", proservice.getCateList()); //jsp에서 참조하는 이름 cateList
	
//		상품정보 가져오기
		ProductVO p_vo = proservice.getProductByNum(pdt_num);
		p_vo.setPdt_img_folder(p_vo.getPdt_img_folder().replace("\\", "/"));
		model.addAttribute("productVO", p_vo);
		
//		상품정보에서 1차카테고리 코드를 참조
		Integer catecoderef  = p_vo.getCatecoderef();
//		1차를 부모로 하는 2차카테고리 정보
		model.addAttribute("SubCateList", proservice.getSubCategoryList(catecoderef));
		
	}
	
//	상품 수정 전송
	@PostMapping("/AdProductModify")
	public String productModify(ProductVO p_vo, RedirectAttributes rttr, Criteria cri ) {
//		VO에서 얻어온 데이터를 얻어오지 못하는 결과 - 로그가 출력이 되지않음. 400번 에러 클라이언트 -> 서버
		log.info("상품 등록정보: " + p_vo) ;
		
//		상품이미지 변경문제 -파일 업로드 유무체크
		
//		상품 수정 이미지 파일업로드 할 경우
		if (!p_vo.getUploadFile().isEmpty()) {

//			1) 상품 수정전 이미지 파일삭제. (날짜 폴더명, 변경전 이미지 파일명)
			uploadFileUtils.deleteFile(uploadPath, p_vo.getPdt_img_folder() + "\\s_" + p_vo.getPdt_img());
			
//			파일 업로드 작업.
//			vo.getPdt_img(); vo.getPdt_img() NULL 상태임.
//			이미지 파일명이 저장될 pdt_img필드에 업로드한 후 실제 파일명을 저장.
			String uploadDateFolderPath = uploadFileUtils.getFolder();
			p_vo.setPdt_img_folder(uploadDateFolderPath); //날짜 폴더명
			p_vo.setPdt_img(uploadFileUtils.uploadFile(uploadPath, uploadDateFolderPath, p_vo.getUploadFile())); //실제 업로드된 이미지파일이 저장이 됨.
			
		}
//		2) 상품정보수정.
		proservice.AdProductModify(p_vo);

		return "redirect:/admin/product/adproductlist" + cri.getListLink();

	}
	
//	상품 삭제
	@GetMapping("/AdDeleteProduct") //상품코드, 페이지 및 검색 파라미터, 날짜폴더, 파일이름
	public String productDelete(@RequestParam("pdt_num") Integer pdt_num, @ModelAttribute("cri") Criteria cri, String pdt_img_folder, String pdt_img) {
		log.info("삭제할 상품 번호: " + pdt_num);
		
//		1) 상품 수정전 이미지 파일삭제. (날짜 폴더명, 변경전 이미지 파일명)
		uploadFileUtils.deleteFile(uploadPath, pdt_img_folder + "\\s_" + pdt_img);
		proservice.AdDeleteProduct(pdt_num);
		
		
		return "redirect:/admin/product/adproductlist" + cri.getListLink();

	}
	
	
	

	
	
	
	
	
	
	
}