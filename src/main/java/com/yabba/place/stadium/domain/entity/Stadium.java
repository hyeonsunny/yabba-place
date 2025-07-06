package com.yabba.place.stadium.domain.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.DynamicUpdate;

import com.yabba.place.stadium.domain.dto.StadiumDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicUpdate
@Entity
@Table(name = "stadium")
public class Stadium {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "team_id")
	private Long teamId;

	@Column(name = "name")
	private String name;

	@Column(name = "address")
	private String address;

	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

	@PrePersist
	public void prePersist() {
		this.createdAt = LocalDateTime.now();
	}

	@PreUpdate
	public void preUpdate() {
		this.updatedAt = LocalDateTime.now();
	}

	public void softDelete() {
		this.deletedAt = LocalDateTime.now();
	}

	public void update(StadiumDTO dto) {
		this.teamId = dto.teamId();
		this.name = dto.name();
		this.address = dto.address();
	}
}
