package com.example.handler;

import com.example.constants.AuthConstants;
import com.example.domain.User;
import com.example.utils.TokenUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response,
	                                    final Authentication authentication) {
		final User user = ((User) authentication.getPrincipal());
		final String token = TokenUtils.generateJwtToken(user);
		response.addHeader(AuthConstants.AUTH_HEADER, AuthConstants.TOKEN_TYPE + " " + token);
	}
}
