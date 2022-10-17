package com.example.domain;

import com.example.enums.UserRole;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Table(name = "USER")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;                                        // 고유번호

	@Column(nullable = false, unique = true, length = 50)
	private String email;                                   // 이메일

	@Column(nullable = false)
	private String pw;

	@Setter
	@Column(nullable = false, length = 50)
	@Enumerated(EnumType.STRING)
	private UserRole role;

	@CreationTimestamp
	@Column(nullable = false, length = 20, updatable = false)
	private LocalDateTime createdAt;                        // 등록 일자

	@UpdateTimestamp
	@Column(length = 20)
	private LocalDateTime updatedAt;                        // 수정 일자

	@Setter
	@Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
	private Boolean isEnable = true;                        // 사용 여부

	private Collection<? extends GrantedAuthority> authorities;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new SimpleGrantedAuthority(role.getValue());
	}

	@Override
	public String getPassword() {
		return pw;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public void updateUser(String email, String pw, UserRole role) {
		this.email = email;
		this.pw = pw;
		this.role = role;
	}
}































