package com.example.domain;

import com.example.enums.UserRole;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "USER")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

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
	private Boolean isEnable = true;
}
