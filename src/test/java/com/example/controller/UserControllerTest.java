package com.example.controller;

import com.example.common.GlobalExceptionHandler;
import com.example.domain.UserAddResponse;
import com.example.domain.UserRequest;
import com.example.enums.UserRole;
import com.example.exception.UserErrorResult;
import com.example.exception.UserException;
import com.example.service.UserDetailsServiceImpl;
import com.google.gson.Gson;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

	@InjectMocks
	private UserController target;
	@Mock
	private UserDetailsServiceImpl userDetailsService;

	private MockMvc mockMvc;
	private Gson gson;

	@BeforeEach
	public void init() {
		gson = new Gson();
		mockMvc = MockMvcBuilders.standaloneSetup(target)
			.setControllerAdvice(new GlobalExceptionHandler())
			.build();
	}

	@Test
	public void null확인() {
		assertThat(target).isNotNull();
		assertThat(mockMvc).isNotNull();
	}
	
	@Test
	public void 회원등록실패_이미존재함() throws Exception {
	    // given
		final String url = "/api/user/";
		doThrow(new UserException(UserErrorResult.DUPLICATE_USER_REGISTER))
			.when(userDetailsService).registerUser("test","test", UserRole.ROLE_ADMIN);

	    // when
		final ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.post(url)
				.content(gson.toJson(UserRequest.builder().email("test").pw("test").role(UserRole.ROLE_ADMIN).build()))
				.contentType(MediaType.APPLICATION_JSON)
		);
	    
	    // then
		resultActions.andExpect(status().isBadRequest());
	}

	@Test
	public void 회원등록성공() throws Exception {
		// given
		final String url = "/api/user/";
		doReturn(UserAddResponse.builder().id(-1L).email("test").build()).when(userDetailsService).registerUser("test",null,null);

		// when
		final ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.post(url)
				.content(gson.toJson(UserRequest.builder().email("test").build()))
				.contentType(MediaType.APPLICATION_JSON)
		);

		// then
		resultActions.andExpect(status().isCreated());

		final UserAddResponse response = gson.fromJson(resultActions.andReturn()
			.getResponse()
			.getContentAsString(StandardCharsets.UTF_8), UserAddResponse.class);

		assertThat(response.getEmail()).isEqualTo("test");
	}


}





















