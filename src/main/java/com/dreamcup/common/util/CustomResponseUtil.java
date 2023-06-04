package com.dreamcup.common.util;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;

import com.dreamcup.common.dto.response.ErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomResponseUtil {

	public static void success(HttpServletResponse response, Object dto) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String responseBody = objectMapper.writeValueAsString(dto);
			response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
			response.setStatus(200);
			response.getWriter().print(responseBody);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void fail(HttpServletResponse response, String message, HttpStatus status) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			ErrorResponse errorResponse = ErrorResponse.builder()
				.code(String.valueOf(status.value()))
				.message(message)
				.build();
			String responseBody = objectMapper.writeValueAsString(errorResponse);

			response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
			response.setStatus(status.value());
			response.getWriter().print(responseBody);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
