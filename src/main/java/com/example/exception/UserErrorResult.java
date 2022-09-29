package com.example.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorResult {

	DUPLICATE_USER_REGISTER(HttpStatus.BAD_REQUEST, "duplicated user register request"),
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "user not found");

	private final HttpStatus httpStatus;
	private final String message;
}
