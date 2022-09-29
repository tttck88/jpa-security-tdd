package com.example.service;

import com.example.domain.MyUserDetails;
import com.example.domain.User;
import com.example.domain.UserAddResponse;
import com.example.domain.UserDetailResponse;
import com.example.enums.UserRole;
import com.example.exception.UserErrorResult;
import com.example.exception.UserException;
import com.example.exception.UserNotFoundException;
import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.enums.UserRole.ROLE_ADMIN;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

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

	public List<UserDetailResponse> getUserList() {
		return userRepository.findAll().stream()
			.map(user -> UserDetailResponse.builder()
				.id(user.getId())
				.email(user.getEmail())
				.role(user.getRole())
				.build())
			.collect(Collectors.toList());
	}
	
	public UserDetailResponse getUser(String email) {
		return userRepository.findByEmail(email)
			.map(user -> UserDetailResponse.builder().id(user.getId()).email(user.getEmail()).role(user.getRole()).build())
			.orElseThrow(() -> new UserException(UserErrorResult.USER_NOT_FOUND));
	}

	@Override
	public MyUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return userRepository.findByEmail(email)
			.map(u -> new MyUserDetails(u, Collections.singleton(new SimpleGrantedAuthority(u.getRole().getValue()))))
			.orElseThrow(() -> new UserNotFoundException(email));
	}
}
