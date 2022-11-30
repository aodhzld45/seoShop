package com.seofriends.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.seofriends.util.QRUtil;
import com.seofriends.util.uploadFileUtils;

import lombok.Setter;
import lombok.extern.log4j.Log4j;


@Log4j
@RequestMapping("/admin/qrcode/*")
@Controller
public class AdQRController {
	
	@Setter(onMethod_= {@Autowired})
	private QRUtil qrsaveutil;
	

	// qr코드 생성 페이지
	@GetMapping("/qrcreate")
	public void qrcreate() {
		log.info("qrcreate Get");
	}
	
	@ResponseBody
	@PostMapping("/qrcreate")
	public ModelAndView qrcreate(@RequestParam String content, HttpServletResponse res) throws IOException {
		log.info("qrcreate Post");

		String filePath = "D:\\QRCODE\\";
						
		String QRFile = QRUtil.MakeQR(content, filePath);
		
		log.info("===============>" + QRFile);
		res.setHeader("Content-Transfer-Encoding", "binary");
		
		ServletOutputStream out = res.getOutputStream();
		
		Map<String, Object> model = new HashMap<String, Object>();
		
		model.put("fileName", QRFile);	//object
		model.put("content", content);

		return new ModelAndView("qrsrc", model);
	
	}
	

	
		
}