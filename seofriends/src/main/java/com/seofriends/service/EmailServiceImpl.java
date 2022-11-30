package com.seofriends.service;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.seofriends.dto.EmailDTO;

@Service
public class EmailServiceImpl implements EmailService {
	//lombok.Setter 어노테이션
	@Autowired // jdk 1.8 onMethod_ jdk 다른 버전은 _(언더바)를 사용하지 않음
	private JavaMailSender mailSender; //root-context.xml에 설정한 bean 주입.
	
	@Override
	public void sendMail(EmailDTO dto, String message) {
//		메일을 보내는 작업 / 메일 구성정보를 담당하는 객체(발신인, 수신인, 이메일주소, 메일내용)
		MimeMessage msg = mailSender.createMimeMessage();
		
		try {
//			받는사람 메일 주소.
			msg.addRecipient(RecipientType.TO, new InternetAddress(dto.getReceiveMail()));
//			보내는 사람 (메일, 이름)
			msg.addFrom(new InternetAddress[] { new InternetAddress(dto.getSenderMail(),dto.getSenderName())});
//			메일 제목.
			msg.setSubject(dto.getSubject(), "utf-8");
//			본문내용
//			msg.setText(dto.getMessage(), "utf-8");
			msg.setText(message, "utf-8");
			
			mailSender.send(msg); //GMail 보안 설정을 낮게 해야 한다.
				
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
