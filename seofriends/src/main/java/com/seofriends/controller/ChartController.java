package com.seofriends.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.seofriends.domain.ChartVO;
import com.seofriends.service.ChartService;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@RequestMapping("/admin/chart/*")
@Controller
public class ChartController {
	@Setter(onMethod_ = {@Autowired})
	ChartService chartservice;
	
	//카테고리별 통계
	@GetMapping("/overall")
	public void overall(Model model) {
		
		/*
		 * 
		 * 
		 [ -시작
		 *['Task', 'Hours per Day'],
          ['Work',     11],
          ['Eat',      2],
          ['Commute',  2],
          ['Watch TV', 2],
          ['Sleep',    7]
		 ] -끝
		 * 
		 * */
		List<ChartVO> primary_list = chartservice.primaryChar();
		
		String primaryData = "[";
		primaryData += "['1차 카테고리', '매출'],";
		
		int i= 0;
		for(ChartVO c_vo : primary_list ) {
			primaryData += "['" + c_vo.getPrimary_cd() + "'," + c_vo.getSales_p() + "]";
			i++;
		
			if (i < primary_list.size()) primaryData += ","; 					
		}	
		primaryData += "]";	
		model.addAttribute("primaryData", primaryData);
		
	}
	
	//월별 통계
	@GetMapping("/monthall")
	public void monthall(Model model) {
		/*
		 *    function drawChart() {
		      var data = google.visualization.arrayToDataTable([
		        ['Year', 'Visitations', { role: 'style' } ],
		        ['2010', 10, 'color: gray'],
		        ['2020', 14, 'color: #76A7FA'],
		        ['2030', 16, 'opacity: 0.2'],
		        ['2040', 22, 'stroke-color: #703593; stroke-width: 4; fill-color: #C5A5CF'],
		        ['2050', 28, 'stroke-color: #871B47; stroke-opacity: 0.6; stroke-width: 8; fill-color: #BC5679; fill-opacity: 0.2']
      		]);
		 * 
		 * 	
		 * */
		List<ChartVO> month_list = chartservice.monthall();
		
		String monthData = "[";
		monthData += "['월별', '총매출'],";
		
		int i= 0;
		for(ChartVO c_vo : month_list ) {
			
			monthData += "['" + c_vo.getMonth() + "'," + c_vo.getMontly_sales() + "]";
			i++;
		
			if (i < month_list.size()) monthData += ","; 					
		}	
		monthData += "]";	
		model.addAttribute("monthData", monthData);
		
	}
}