package com.example.config.security;

import com.example.constants.AuthConstants;
import com.example.utils.TokenUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthorizationFilter extends OncePerRequestFilter {
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		final String header = request.getHeader(AuthConstants.AUTH_HEADER);

		if(header != null) {
			if(!TokenUtils.isValidToken(TokenUtils.getTokenFromHeader(header))) {
				response.sendRedirect("/error/unauthorized");
			}
		}
	}
}
