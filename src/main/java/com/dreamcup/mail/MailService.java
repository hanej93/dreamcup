package com.dreamcup.mail;

import java.io.UnsupportedEncodingException;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.dreamcup.common.util.CommonUtil;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {

	private final JavaMailSender mailSender;
	private final SpringTemplateEngine templateEngine;

	public String sendAuthMail(String email) {
		String authCode = generateAuthCode(6);

		try {
			MailUtils sendMail = new MailUtils(mailSender);

			Context context = new Context();
			context.setVariable("authCode", authCode);
			String mailContent = templateEngine.process("mail", context);

			sendMail.setSubject("회원가입 이메일 인증");
			sendMail.setText(mailContent);
			sendMail.setFrom("kimsumy599@gmail.com", "dreamcup");
			sendMail.setTo(email);
			sendMail.send();
		} catch (MessagingException e) {
			// todo : error handling
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return authCode;
	}

	private String generateAuthCode(int size) {
		return CommonUtil.randomAlphaNumeric(size);
	}

}
