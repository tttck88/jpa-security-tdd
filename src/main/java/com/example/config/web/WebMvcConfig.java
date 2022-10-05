package com.example.config.web;

import com.example.config.interceptor.JwtTokenInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(jwtTokenInterceptor())
			.excludePathPatterns("/api/user/signUp")
			.excludePathPatterns("/api/user/login")
			.addPathPatterns("/api/user/*");
	}

	@Bean
	public JwtTokenInterceptor jwtTokenInterceptor() {
		return new JwtTokenInterceptor();
	}
}
