package com.example.controller;

import com.example.domain.UserAddResponse;
import com.example.domain.UserDetailResponse;
import com.example.domain.UserRequest;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	// 회원 등록
	@PostMapping("/api/user/signUp")
	public ResponseEntity<UserAddResponse> registerUser(@RequestBody final UserRequest userRequest) {
		final UserAddResponse userAddResponse = userService.registerUser(userRequest.getEmail(), userRequest.getPw(), userRequest.getRole());
		return ResponseEntity.status(HttpStatus.CREATED).body(userAddResponse);
	}

	// 회원 수정
	@PatchMapping("/api/user")
	public ResponseEntity<UserAddResponse> updateUser(@RequestBody final UserRequest userRequest) {
		final UserAddResponse userAddResponse = userService.updateUser(userRequest.getEmail(), userRequest.getPw(), userRequest.getRole());
		return ResponseEntity.status(HttpStatus.OK).body(userAddResponse);
	}

	// 회원 목록조회
	@GetMapping("/api/user")
	public ResponseEntity<List<UserDetailResponse>> getUserList() {
		return ResponseEntity.status(HttpStatus.OK).body(userService.getUserList());
	}
	
	// 회원 상세조회
	@GetMapping("/api/user/{id}")
	public ResponseEntity<UserDetailResponse> getUser(@PathVariable final Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(id));
	}

	// 회원 로그인
	@PostMapping("/api/user/login")
	public ResponseEntity<Void> login(@RequestBody final UserRequest userRequest) {

		return ResponseEntity.status(HttpStatus.OK).build();
	}

	// 회원 로그아웃

}
