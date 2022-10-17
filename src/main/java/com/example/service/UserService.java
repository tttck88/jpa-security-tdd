package com.example.service;

import com.example.domain.User;
import com.example.domain.UserAddResponse;
import com.example.domain.UserDetailResponse;
import com.example.enums.UserRole;
import com.example.exception.UserErrorResult;
import com.example.exception.UserException;
import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	public UserAddResponse registerUser(String email, String pw, UserRole role) {
		if(userRepository.findByEmail(email).isPresent()) {
			throw new UserException(UserErrorResult.DUPLICATE_USER_REGISTER);
		}

		final User user = userRepository.save(User.builder().email(email).pw(passwordEncoder.encode(pw)).role(role).build());

		return UserAddResponse.builder()
			.id(user.getId())
			.email(user.getEmail())
			.role(user.getRole())
			.build();
	}

	public UserAddResponse updateUser(String email, String pw, UserRole role) {
		User user = userRepository.findByEmail(email).orElse(null);
		if(user != null) {
			user.updateUser(email, pw, role);
		} else {
			throw new UserException(UserErrorResult.USER_NOT_FOUND);
		}

		return UserAddResponse.builder()
			.id(user.getId())
			.email(user.getEmail())
			.role(user.getRole())
			.build();
	}

	public List<UserDetailResponse> getUserList() {
		return userRepository.findAll().stream()
			.map(user -> UserDetailResponse.builder()
				.id(user.getId())
				.email(user.getEmail())
				.role(user.getRole())
				.build())
			.collect(Collectors.toList());
	}

	public UserDetailResponse getUser(Long Id) {
		return userRepository.findById(Id)
			.map(user -> UserDetailResponse.builder().id(user.getId()).email(user.getEmail()).role(user.getRole()).build())
			.orElseThrow(() -> new UserException(UserErrorResult.USER_NOT_FOUND));
	}
}
