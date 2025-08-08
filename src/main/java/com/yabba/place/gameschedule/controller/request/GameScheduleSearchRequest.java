package com.yabba.place.gameschedule.controller.request;

import java.time.LocalDateTime;

import com.yabba.place.gameschedule.domain.dto.GameScheduleSearchDTO;

public record GameScheduleSearchRequest(
	LocalDateTime from,
	LocalDateTime to,
	Long homeTeamId,
	Long awayTeamId
) {
	public GameScheduleSearchDTO toGameScheduleSearchDto() {
		return GameScheduleSearchDTO.builder()
			.from(from())
			.to(to())
			.homeTeamId(homeTeamId())
			.awayTeamId(awayTeamId())
			.build();
	}
}
