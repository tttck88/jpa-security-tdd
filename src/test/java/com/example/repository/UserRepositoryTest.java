package com.example.repository;

import com.example.domain.User;
import com.example.enums.UserRole;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Test
	public void userRepository가null아님() {
		assertThat(userRepository).isNotNull();
	}
	
	@Test
	public void 회원가입성공() {
	    // given
		final User user = User.builder()
			.email("test")
			.pw("test")
			.role(UserRole.ROLE_ADMIN)
			.build();

		// when
		final User result = userRepository.save(user);

	    // then
		assertThat(result.getId()).isNotNull();
		assertThat(result.getEmail()).isEqualTo("test");
		assertThat(result.getRole()).isEqualTo(UserRole.ROLE_ADMIN);
	}
	
	@Test
	public void 회원조회() {
	    // given
		final User user = User.builder()
			.email("test")
			.pw("test")
			.role(UserRole.ROLE_ADMIN)
			.build();

		userRepository.save(user);
	    
	    // when
		Optional<User> result = userRepository.findByEmail("test");
	    
	    // then
		assertThat(result.get()).isNotNull();
		assertThat(result.get().getEmail()).isEqualTo("test");
	}
}