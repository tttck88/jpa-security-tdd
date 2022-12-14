package com.example.domain;

import com.example.enums.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@Builder
public class UserRequest {
	private final String email;

	private final String pw;

	private final String name;

	private final String phoneNum;

	private final String address;

	private final UserRole role;
}
