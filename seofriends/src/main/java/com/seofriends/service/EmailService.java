package com.seofriends.service;

import com.seofriends.dto.EmailDTO;

public interface EmailService {
	void sendMail(EmailDTO dto, String message);
}
