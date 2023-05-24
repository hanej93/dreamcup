package com.dreamcup.common.advisor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dreamcup.common.dto.response.ErrorResponse;
import com.dreamcup.common.exception.CustomException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class RestControllerAdvisor {


	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler
	public ErrorResponse invalidRequestHandler(MethodArgumentNotValidException e) {
		log.error(e.getLocalizedMessage(), e);

		ErrorResponse errorResponse = ErrorResponse.builder()
			.code("400")
			.message("잘못된 요청입니다.")
			.build();

		for (FieldError fieldError : e.getFieldErrors()) {
			errorResponse.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
		}

		return errorResponse;
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> customExceptionHandler(CustomException e) {
		log.error(e.getLocalizedMessage(), e);

		int statusCode = e.getStatusCode();

		ErrorResponse errorResponse = ErrorResponse.builder()
			.code(String.valueOf(statusCode))
			.message(e.getMessage())
			.validation(e.getValidation())
			.build();

		return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(statusCode));
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler
	public ErrorResponse internalServerErrorHandler(Exception e) {
		log.error(e.getLocalizedMessage(), e);

		ErrorResponse errorResponse = ErrorResponse.builder()
			.code("500")
			.message("Internal Server Error")
			.build();

		return errorResponse;
	}

}
