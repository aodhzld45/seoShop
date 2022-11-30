package com.seofriends.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.seofriends.domain.CartVO;
import com.seofriends.domain.CartVOList;
import com.seofriends.domain.MemberVO;
import com.seofriends.service.CartService;
import com.seofriends.util.uploadFileUtils;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RequestMapping("/user/cart/*")
@Log4j
@Controller
public class CartController {
	
	@Setter(onMethod_ = {@Autowired})
	private CartService cartservice;
	
	@Resource(name = "uploadPath")
	private String uploadPath; // "D://DEV//upload"
	
	@ResponseBody
	@GetMapping("/cart_add")
	public ResponseEntity<String> cart_add(CartVO vo, HttpSession session) {
		
		ResponseEntity<String> entity = null;
		
		log.info("장바구니 : " + vo);
		
//		세션에서 로그인시 사용한 정보를 사용.
		String mem_id = ((MemberVO) session.getAttribute("loginStatus")).getMem_id();
		vo.setMem_id(mem_id);
		
		cartservice.cart_add(vo);

		entity = new ResponseEntity<String>("success", HttpStatus.OK); 
		return entity;
		
	}
	
//	로그인한 사용자가 사용하는 매핑주소의 메서드는 파라미터를 HttpSession 반드시 사용해야 한다.
//	이유는 ? 세션을 통하여 로그인한 정보가 저장되어 있기 때문에.
	
	@GetMapping("/cart_List")
	public String cart_List(HttpSession session, Model model) {

		//ProductVO provo = userSerivce.getUserProductByNum(pdt_num);
		
		//vo.setPdt_img_folder(vo.getPdt_img_folder().replace("\\", "/"));// "/" 부분에 File.separator 운영체제 경로 구분자 (다른 os 예)Linux에 배포할때 사용)
//		세션에서 로그인시 사용한 정보를 사용.
		String mem_id = ((MemberVO) session.getAttribute("loginStatus")).getMem_id();
		
		List<CartVOList> cartlist = cartservice.cart_list(mem_id);
		
		for (int i = 0; i < cartlist.size(); i++) {
			String Pdt_img_folder = cartlist.get(i).getPdt_img_folder().replace("\\", "/");// "/" 부분에 File.separator 운영체제 경로 구분자 (다른 os 예)Linux에 배포할때 사용)
			cartlist.get(i).setPdt_img_folder(Pdt_img_folder);
		}
		
		model.addAttribute("cartlist", cartlist);
		
		return "/user/cart/cartList";
	}
	
	@ResponseBody
	@GetMapping("/cart_amount_update1")
	public ResponseEntity<String>cart_amount_update1(@RequestParam("cart_code") Long cart_code, @RequestParam("cart_amount") int cart_amount){
		ResponseEntity<String> entity = null;
		
		cartservice.cart_amount_update(cart_code, cart_amount);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	@GetMapping("/cart_amount_update2")
	public String cart_amount_update2(@RequestParam("cart_code") Long cart_code, @RequestParam("cart_amount") int cart_amount){
		
		cartservice.cart_amount_update(cart_code, cart_amount);
		
		return "redirect:/user/cart/cart_List";
	}

//	등록, 수정, 삭제후 다른 주소로 이동해야 하는 경우는 리턴타입은 String이여야한다. "redirect:/이동주소."
	@GetMapping("/cart_delete")
	public String cart_delete(@RequestParam("cart_code") Long cart_code) {
		
		cartservice.cart_delete(cart_code);
		
		return "redirect:/user/cart/cart_List";
		
	}
	
//	카트목록 비우기
	@GetMapping("/cart_empty")
	public String cart_empty(HttpSession session) {
		String mem_id = ((MemberVO) session.getAttribute("loginStatus")).getMem_id();
		
		cartservice.cart_empty(mem_id);
		
		return "redirect:/user/cart/cart_List";
	}
	
//	상품목록에서 이미지 보여주기
	@ResponseBody
	@GetMapping("/displayFile")
	public ResponseEntity<byte[]>displayFile(String folderName, String fileName){
		return uploadFileUtils.getFile(uploadPath, folderName + "\\" + fileName);
	}
	
	
	
	
	
	
}
