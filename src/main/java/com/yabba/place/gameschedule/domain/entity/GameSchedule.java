package com.yabba.place.gameschedule.domain.entity;

import java.time.LocalDateTime;

import com.yabba.place.gameschedule.domain.define.GameStatus;
import com.yabba.place.gameschedule.domain.dto.GameScheduleDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Entity
@Table(name = "game_schedule")
public class GameSchedule {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "home_team_id", nullable = false)
	private Long homeTeamId;

	@Column(name = "away_team_id", nullable = false)
	private Long awayTeamId;

	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	private GameStatus status;

	@Column(name = "started_at", nullable = false)
	private LocalDateTime startedAt;

	@Column(name = "home_team_score")
	@Builder.Default
	private Integer homeTeamScore = 0;

	@Column(name = "away_team_score")
	@Builder.Default
	private Integer awayTeamScore = 0;

	@Column(name = "winner_team_id")
	@Builder.Default
	private Long winnerTeamId = 0L;

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

	public void update(GameScheduleDTO dto) {
		this.startedAt = dto.startedAt();
		this.homeTeamScore = dto.homeTeamScore();
		this.awayTeamScore = dto.awayTeamScore();
		this.winnerTeamId = dto.winnerTeamId();
	}
}
