package com.example.service;

import com.example.domain.User;
import com.example.domain.UserAddResponse;
import com.example.domain.UserDetailResponse;
import com.example.enums.UserRole;
import com.example.exception.UserErrorResult;
import com.example.exception.UserException;
import com.example.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

	@InjectMocks
	private UserService target;
	@Mock
	private UserRepository userRepository;
	@Mock
	private BCryptPasswordEncoder passwordEncoder;

	String email = "test";
	String pw = "test";
	UserRole role = UserRole.ROLE_ADMIN;

	// 회원등록...
	@Test
	public void 회원등록실패_이미존재함() {
	    // given
	    doReturn(Optional.of(User.builder().build())).when(userRepository).findByEmail("test");

	    // when
		final UserException result =  assertThrows(UserException.class,
			() -> target.registerUser(email, pw, role));
	    
	    // then
		assertThat(result.getErrorResult()).isEqualTo(UserErrorResult.DUPLICATE_USER_REGISTER);
	}
	
	@Test
	public void 회원등록성공() {
	    // given
		doReturn(Optional.empty()).when(userRepository).findByEmail(email);
		doReturn(User.builder().email(email).pw(pw).role(role).build()).when(userRepository).save(any(User.class));

	    // when
		final UserAddResponse result = target.registerUser(email, pw, role);
	    
	    // then
		assertThat(result).isNotNull();
		assertThat(result.getEmail()).isEqualTo(email);
		assertThat(result.getRole()).isEqualTo(role);
	}

	// 회원조회...
	@Test
	public void 회원목록조회() {
	    // given
		doReturn(Arrays.asList(
			User.builder().build(),
			User.builder().build(),
			User.builder().build()
		)).when(userRepository).findAll();
	    
	    // when
		List<UserDetailResponse> result = target.getUserList();
	    
	    // then
		assertThat(result.size()).isEqualTo(3);
	}
	
	@Test
	public void 회원상세조회_없음() {
	    // given
		doReturn(Optional.empty()).when(userRepository).findById(-1L);
	    
	    // when
		final UserException result = assertThrows(UserException.class,
			() -> target.getUser(-1L));
	    
	    // then
		assertThat(result.getErrorResult()).isEqualTo(UserErrorResult.USER_NOT_FOUND);
	}
	
	@Test
	public void 회원상세조회성공() {
	    // given
		doReturn(Optional.of(User.builder().id(-1L).email(email).build())).when(userRepository).findById(-1L);

	    // when
	    final UserDetailResponse result = target.getUser(-1L);

	    // then
		assertThat(result).isNotNull();
		assertThat(result.getEmail()).isEqualTo(email);
	}

	// 회원 로그인...
	@Test
	public void 회원로그인실패_계정이존재하지않음() {
	    // given

	    // when

	    // then
	}

	// 회원 로그아웃...

}



















