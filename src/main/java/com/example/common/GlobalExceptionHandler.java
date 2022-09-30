package com.example.common;

import com.example.domain.User;
import com.example.exception.UserException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UserException.class)
	public ResponseEntity<ErrorResponse> handleUserException(final UserException exception) {
		log.warn("UserException occur : ", exception);
		return this.makeErrorResponseEntity(exception.getErrorResult().getHttpStatus(), exception.getErrorResult().name(), exception.getErrorResult().getMessage());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(final Exception exception) {
		log.warn("Exception occur : ", exception);
		return this.makeErrorResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "UNKNOWN_EXCEPTION","Unknown Exception");
	}

	private ResponseEntity<ErrorResponse> makeErrorResponseEntity(HttpStatus httpStatus, String code, String message) {
		return ResponseEntity.status(httpStatus)
			.body(new ErrorResponse(code, message));
	}

	@Getter
	@RequiredArgsConstructor
	static class ErrorResponse {
		private final String code;
		private final String message;
	}
}
