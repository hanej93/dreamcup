package com.dreamcup.mail;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MailController {

	private final MailService mailService;

	@PostMapping("/verify-mail")
	public ResponseEntity<String> sendVerifyMail(@RequestBody String email) {
		String authCode = mailService.sendAuthMail(email);
		return new ResponseEntity<>(authCode, HttpStatus.OK);
	}

}
