package com.seofriends.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.seofriends.service.UserProductService;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

/*	
 * com.docmall.controller 패키지안에 존재하는 컨트롤러에서 주소 요청을 받으면,
 * CategoryList 메서드가 자동으로 호출되어, 공통모델을 사용할 수가 있게 된다..
 */

@ControllerAdvice(basePackages = {"com.seofriends.controller"}) // 
@Log4j
public class GlobalContrllorAdvice {
	
//	Service bean 주입.
	@Setter(onMethod_ = {@Autowired})
	private UserProductService userService;
	
//	1차 카테고리 작업.
	@ModelAttribute("categoryList")
	public void categoryList(Model model) {
		log.info("==1차 카테고리 모델작업==");
		model.addAttribute("mainCategoryList", userService.getUserCategoryList());
	}
		
	
	
	
	
	
	
	
	
	
	
	
}