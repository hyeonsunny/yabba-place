package com.yabba.place.gameschedule.domain.dto;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record GameScheduleSearchDTO(
	LocalDateTime from,
	LocalDateTime to,
	Long homeTeamId,
	Long awayTeamId
) {
}
