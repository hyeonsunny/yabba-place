package com.yabba.place.gamereview.controller.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.yabba.place.gamereview.domain.dto.GameReviewDTO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "경기 리뷰 응답 객체")
public record GameReviewResponse(
	@Schema(description = "경기 리뷰 ID")
	String id,

	@Schema(description = "사용자 ID")
	long userId,

	@Schema(description = "경기 날짜")
	LocalDate gameDate,

	@Schema(description = "경기장 이름")
	String stadiumName,

	@Schema(description = "홈팀 이름")
	String homeTeamName,

	@Schema(description = "원정팀 이름")
	String awayTeamName,

	@Schema(description = "홈팀 점수")
	int homeScore,

	@Schema(description = "원정팀 점수")
	int awayScore,

	@Schema(description = "좌석 등급")
	String seatGrade,

	@Schema(description = "좌석 구역")
	String seatSection,

	@Schema(description = "좌석 번호")
	String seatNumber,

	@Schema(description = "좌석 시야 이미지 URL")
	String seatViewImageUrl,

	@Schema(description = "제목")
	String title,

	@Schema(description = "내용")
	String content,

	@Schema(description = "평점")
	int rating,

	@Schema(description = "등록 일시")
	LocalDateTime createdAt
) {
	public static GameReviewResponse fromGameReviewDto(GameReviewDTO dto) {
		return new GameReviewResponse(
			dto.id(),
			dto.userId(),
			dto.gameDate(),
			dto.homeTeam().getStadiumName(),
			dto.homeTeam().getName(),
			dto.awayTeam().getName(),
			dto.homeScore(),
			dto.awayScore(),
			dto.seatGrade(),
			dto.seatSection(),
			dto.seatNumber(),
			dto.seatViewImageUrl(),
			dto.title(),
			dto.content(),
			dto.rating(),
			dto.createdAt()
		);
	}
}
