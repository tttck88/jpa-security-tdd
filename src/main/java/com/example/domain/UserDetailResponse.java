package com.example.domain;

import com.example.enums.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class UserDetailResponse {
	private final Long id;

	private final String email;

	private final UserRole role;
}
