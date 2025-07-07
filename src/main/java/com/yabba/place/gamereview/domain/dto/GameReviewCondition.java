package com.yabba.place.gamereview.domain.dto;

import java.time.LocalDate;

import com.yabba.place.gamereview.domain.define.Team;

import lombok.Builder;

@Builder
public record GameReviewCondition(
	LocalDate from,
	LocalDate to,
	Team homeTeam,
	String seatGrade,
	String seatSection,
	String seatNumber,
	String title,
	String content,
	int rating
) {
}
