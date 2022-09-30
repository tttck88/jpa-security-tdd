package com.example.config.security;

import com.example.domain.MyUserDetails;
import com.example.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private final UserDetailsServiceImpl userDetailsServiceImpl;
	private final BCryptPasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		final UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)authentication;
		final String userEmail = token.getName();
		final String userPw = (String)token.getCredentials();

		final MyUserDetails userDetails = (MyUserDetails) userDetailsServiceImpl.loadUserByUsername(userEmail);

		if (!passwordEncoder.matches(userPw, userDetails.getPassword())) {
			throw new BadCredentialsException(userDetails.getUsername() + "Invalid password");
		}

		return new UsernamePasswordAuthenticationToken(userDetails, userPw, userDetails.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
