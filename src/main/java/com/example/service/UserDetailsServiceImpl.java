package com.example.service;

import com.example.domain.MyUserDetails;
import com.example.domain.User;
import com.example.domain.UserAddResponse;
import com.example.domain.UserDetailResponse;
import com.example.enums.UserRole;
import com.example.exception.UserErrorResult;
import com.example.exception.UserException;
import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public MyUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return userRepository.findByEmail(email)
			.map(u -> new MyUserDetails(u, Collections.singleton(new SimpleGrantedAuthority(u.getRole().getValue()))))
			.orElseThrow(() -> new UserException(UserErrorResult.USER_NOT_FOUND));
	}
}
