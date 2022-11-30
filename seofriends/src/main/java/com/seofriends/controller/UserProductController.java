package com.seofriends.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.seofriends.domain.CategoryVO;
import com.seofriends.domain.ProductVO;
import com.seofriends.dto.Criteria;
import com.seofriends.dto.PageDTO;
import com.seofriends.service.UserProductService;
import com.seofriends.util.uploadFileUtils;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RequestMapping("/user/product/*")
@Controller
@Log4j
public class UserProductController {
//	userService bean 주입.
	@Setter(onMethod_ = {@Autowired})
	private UserProductService userSerivce;
	
	@Resource(name = "uploadPath")
	private String uploadPath; // "D://DEV//upload"
	

//	->@ControllerAdvice 어노테이션을 적용된 클래스가 카테고리 Model작업을 한다.
	
//	2차 카테고리 정보 작업.
	@ResponseBody
	@GetMapping("/subUserCategoryList/{catecode}") //Ajax 요청.
	public ResponseEntity<List<CategoryVO>> subCategoryList(@PathVariable("catecode") Integer catecode){
		ResponseEntity<List<CategoryVO>> entity = null;
		log.info("카테고리 코드 : " + catecode);
		entity = new ResponseEntity<List<CategoryVO>>(userSerivce.getUserSubCategoryList(catecode), HttpStatus.OK);
		
		return entity;
	}
//	상품 리스트 페이징 기능추가, (검색 기능 제와)
	@GetMapping("/productList/{catecode}/{catename}")//{catrname},  @PathVariable("catrname") String catrname,
	public String productList(@PathVariable("catecode") Integer catecode, @PathVariable("catename") String catrname, @ModelAttribute("cri") Criteria cri, Model model) {
		log.info("2차 카테고리 코드 : " + catecode);
		
		cri.setAmount(9); //상품 9개씩 출력.
		
//		List<ProductVO> productList = userSerivce.getProductListbysubCategory(catecode, cri);
		List<ProductVO> productList = userSerivce.getProductListbysubCategory(catecode, cri);
		
//		\(역슬래쉬)를 /(슬래쉬)로 변환하는작업. \가 클라이언트에서 서버로 보내지는 데이터로 사용되지 않아 변환
		for (int i = 0; i < productList.size(); i++) {
			String Pdt_img_folder = productList.get(i).getPdt_img_folder().replace("\\", "/");// "/" 부분에 File.separator 운영체제 경로 구분자 (다른 os 예)Linux에 배포할때 사용)
			productList.get(i).setPdt_img_folder(Pdt_img_folder);
		}
		
//		1)페이징쿼리에 의한 상품목록.
		model.addAttribute("productList", productList);
		
//		2) 페이지 표시 [prev]	1	2	3	4	5 [prev]
		
		int totalCount = userSerivce.getProductTotalCountbysubCategory(catecode, cri);
		log.info("getProductTotalCount : " + totalCount);
		model.addAttribute("pageMaker", new PageDTO(cri, totalCount));	
		
		return "/user/product/productList";	
	}
	
//	상품목록에서 이미지 보여주기
	@ResponseBody
	@GetMapping("/displayFile")
	public ResponseEntity<byte[]>displayFile(String folderName, String fileName){
		
		return uploadFileUtils.getFile(uploadPath, folderName + "\\" + fileName);
		
	}

//	모달 대화상자에서 사용할 상품상세정보 Json 데이타로 넘겨줌
	@ResponseBody
	@GetMapping("/productDetail/{pdt_num}") //REST API 
	public ResponseEntity<ProductVO> productDetail(@PathVariable("pdt_num") Integer pdt_num) {
		
		ResponseEntity<ProductVO> entity = null;
		
		ProductVO vo = userSerivce.getUserProductByNum(pdt_num);
	
		vo.setPdt_img_folder(vo.getPdt_img_folder().replace("\\", "/"));// "/" 부분에 File.separator 운영체제 경로 구분자 (다른 os 예)Linux에 배포할때 사용)
		
		entity = new ResponseEntity<ProductVO>(vo, HttpStatus.OK);
		
		return entity;
	}
	
//	상품 상세 주소
	@GetMapping("/productDetail")
	public String productDetail(@RequestParam("pdt_num") Integer pdt_num, @ModelAttribute("catecode") Integer catecode, @ModelAttribute("catename") String catename, @ModelAttribute("cri") Criteria cri, Model model) {
		
		ProductVO vo = userSerivce.getUserProductByNum(pdt_num);
	
		vo.setPdt_img_folder(vo.getPdt_img_folder().replace("\\", "/"));// "/" 부분에 File.separator 운영체제 경로 구분자 (다른 os 예)Linux에 배포할때 사용)
		
		model.addAttribute("productVO", vo);
		
		return "/user/product/productDetail";
	}
	
	
}