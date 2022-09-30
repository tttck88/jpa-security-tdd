package com.example.controller;

import com.example.domain.UserAddResponse;
import com.example.domain.UserRequest;
import com.example.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

	private final UserDetailsServiceImpl userDetailsService;

	// 회원 등록
	@PostMapping
	public ResponseEntity<UserAddResponse> registerUser(@RequestBody final UserRequest userRequest) {
		final UserAddResponse userAddResponse = userDetailsService.registerUser(userRequest.getEmail(), userRequest.getPw(), userRequest.getRole());
		return ResponseEntity.status(HttpStatus.CREATED).body(userAddResponse);
	}

	// 회원 찾기

	// 회원 로그인

	// 회원 로그아웃

}
