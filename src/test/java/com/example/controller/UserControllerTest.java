package com.example.controller;

import com.example.common.GlobalExceptionHandler;
import com.example.domain.UserAddResponse;
import com.example.domain.UserDetailResponse;
import com.example.domain.UserRequest;
import com.example.enums.UserRole;
import com.example.exception.UserErrorResult;
import com.example.exception.UserException;
import com.example.service.UserService;
import com.google.gson.Gson;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.xml.transform.Result;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WebAppConfiguration
class UserControllerTest {

	@Autowired
	private WebApplicationContext context;
	private MockMvc mockMvc;
	private Gson gson;

	@BeforeEach
	public void init() {
		gson = new Gson();
		mockMvc = MockMvcBuilders
			.webAppContextSetup(this.context)
			.apply(springSecurity())
			.build();
	}

	@Test
	public void null확인() {
		assertThat(mockMvc).isNotNull();
	}

	@Test
	public void 회원등록성공() throws Exception {
		// given
		final String url = "/api/user/";

		// when
		final ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.post(url)
				.content(gson.toJson(UserRequest.builder().email("test").pw("password").role(UserRole.ROLE_ADMIN).build()))
				.contentType(MediaType.APPLICATION_JSON)
		);

		// then
		resultActions.andExpect(status().isCreated());

		final UserAddResponse response = gson.fromJson(resultActions.andReturn()
			.getResponse()
			.getContentAsString(StandardCharsets.UTF_8), UserAddResponse.class);

		assertThat(response.getEmail()).isEqualTo("test");
	}
	
	@Test
	public void 회원목록조회성공() throws Exception {
	    // given
		final String url ="/api/user/findAll";
	    
	    // when
		final ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.get(url)
				.contentType(MediaType.APPLICATION_JSON)
		);

	    // then
		resultActions.andExpect(status().isOk());
	}
	
	@Test
	public void 회원상세조회실패_회원이없음() throws Exception {
	    // given
		final String url = "/api/user/-1";

	    // when
		ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.get(url)
				.contentType(MediaType.APPLICATION_JSON)
		);

	    // then
		resultActions.andExpect(status().isNotFound());
	}
	
	@Test
	public void 회원로그인실패_패스워드가틀림() throws Exception {
	    // given
		final String url1 = "/api/user/signUp";
		final String url2 = "/api/user/login";
		final String email = "test@email.com";
		final String pw = "password";

		mockMvc.perform(
			MockMvcRequestBuilders.post(url1)
				.content(gson.toJson(UserRequest.builder().email(email).pw(pw).role(UserRole.ROLE_ADMIN).build()))
				.contentType(MediaType.APPLICATION_JSON)
		);

	    // when
		ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.post(url2)
				.content(gson.toJson(UserRequest.builder().email(email).pw(pw).role(UserRole.ROLE_ADMIN).build()))
				.contentType(MediaType.APPLICATION_JSON)
		);

	    // then
		resultActions.andExpect(status().is4xxClientError());
	}
	
	@Test
	public void 회원로그인성공() {
	    // given
	    
	    // when
	    
	    // then
	}
}





















