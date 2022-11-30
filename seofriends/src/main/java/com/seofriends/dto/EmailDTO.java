package com.seofriends.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailDTO {
	private String senderName; //발신자 이름.
	private String senderMail; //발신자 메일주소.
	private String receiveMail; //수신자.
	private String subject; // 제목
	private String message; // 본문
	
	public EmailDTO() {
		this.senderName = "DocMall";
		this.senderMail = "DocMall"; //Gmail 전자우편 정보 / Gmail SMTP메일서버 이름. 현업>회사 메일서버 정보를 이용. 
		this.subject = "DocMall 회원가입 메일 인증코드 입니다.";
		this.message = "메일 인증을 위한 인증코드를 확인하시고, 회원가입시 인증코드 입력란에 입력하세요.";
	}
}
