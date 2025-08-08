package com.yabba.place.gameschedule.controller.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yabba.place.gameschedule.domain.define.GameStatus;
import com.yabba.place.gameschedule.domain.dto.GameScheduleDTO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "경기 일정 응답 객체")
public record GameScheduleResponse(
	@Schema(description = "경기 일정 ID")
	long id,

	@Schema(description = "홈팀 ID")
	long homeTeamId,

	@Schema(description = "원정팀 ID")
	long awayTeamId,

	@Schema(description = "경기 상태")
	GameStatus status,

	@Schema(description = "경기 시작 일시")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime startedAt,

	@Schema(description = "홈팀 스코어")
	int homeTeamScore,

	@Schema(description = "원정팀 스코어")
	int awayTeamScore,

	@Schema(description = "승리팀 ID")
	long winnerTeamId,

	@Schema(description = "등록 일시")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime createdAt
) {
	public static GameScheduleResponse fromGameScheduleDto(GameScheduleDTO dto) {
		return new GameScheduleResponse(
			dto.id(),
			dto.homeTeamId(),
			dto.awayTeamId(),
			dto.status(),
			dto.startedAt(),
			dto.homeTeamScore(),
			dto.awayTeamScore(),
			dto.winnerTeamId(),
			dto.createdAt()
		);
	}
}
