package com.example.config.interceptor;

import com.example.constants.AuthConstants;
import com.example.utils.TokenUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtTokenInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		final String header = request.getHeader(AuthConstants.AUTH_HEADER);

		if(header != null) {
			if(TokenUtils.isValidToken(TokenUtils.getTokenFromHeader(header))) {
				return true;
			}
		}
		response.sendRedirect("/error/unauthorized");
		return false;
	}
}
