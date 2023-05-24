package com.dreamcup.dto.response;

import java.util.HashMap;
import java.util.Map;

import lombok.Builder;

public record ErrorResponse(String code, String message, Map<String, String> validation) {

	@Builder
	public ErrorResponse(String code, String message, Map<String, String> validation) {
		this.code = code;
		this.message = message;
		this.validation = validation != null ? validation : new HashMap<>();
	}

	public void addValidation(String field, String errorMessage) {
		validation.put(field, errorMessage);
	}
}
