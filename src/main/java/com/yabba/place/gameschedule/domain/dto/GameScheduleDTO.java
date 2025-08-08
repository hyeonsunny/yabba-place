package com.yabba.place.gameschedule.domain.dto;

import java.time.LocalDateTime;

import com.yabba.place.gameschedule.domain.define.GameStatus;
import com.yabba.place.gameschedule.domain.entity.GameSchedule;

import lombok.Builder;

@Builder
public record GameScheduleDTO(
	long id,
	long homeTeamId,
	long awayTeamId,
	GameStatus status,
	LocalDateTime startedAt,
	int homeTeamScore,
	int awayTeamScore,
	long winnerTeamId,
	LocalDateTime createdAt
) {
	public GameSchedule toGameSchedule() {
		return GameSchedule.builder()
			.homeTeamId(homeTeamId())
			.awayTeamId(awayTeamId())
			.status(status())
			.startedAt(startedAt())
			.homeTeamScore(homeTeamScore())
			.awayTeamScore(awayTeamScore())
			.winnerTeamId(winnerTeamId())
			.build();
	}

	public static GameScheduleDTO fromGameSchedule(GameSchedule gameSchedule) {
		return new GameScheduleDTO(
			gameSchedule.getId(),
			gameSchedule.getHomeTeamId(),
			gameSchedule.getAwayTeamId(),
			gameSchedule.getStatus(),
			gameSchedule.getStartedAt(),
			gameSchedule.getHomeTeamScore(),
			gameSchedule.getAwayTeamScore(),
			gameSchedule.getWinnerTeamId(),
			gameSchedule.getCreatedAt()
		);
	}
}
