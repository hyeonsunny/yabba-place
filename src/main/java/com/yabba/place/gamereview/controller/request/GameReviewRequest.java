package com.yabba.place.gamereview.controller.request;

import java.time.LocalDate;

import com.yabba.place.gamereview.domain.define.Team;
import com.yabba.place.gamereview.domain.dto.GameReviewDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Schema(description = "경기 리뷰 요청 객체")
@Builder
public record GameReviewRequest(
	@Schema(description = "사용자 ID", example = "1")
	@Min(value = 0, message = "사용자 ID는 0 이상이어야 합니다.")
	long userId,

	@Schema(description = "경기 날짜", example = "2023-10-01")
	@NotNull(message = "경기 날짜는 필수입니다.")
	LocalDate gameDate,

	@Schema(description = "홈팀", example = "DOOSAN")
	@NotNull(message = "홈팀은 필수입니다.")
	Team homeTeam,

	@Schema(description = "원정팀", example = "KT")
	@NotNull(message = "원정팀은 필수입니다.")
	Team awayTeam,

	@Schema(description = "홈팀 점수", example = "5")
	@NotNull(message = "홈팀 점수는 필수입니다.")
	int homeScore,

	@Schema(description = "원정팀 점수", example = "3")
	@NotNull(message = "원정팀 점수는 필수입니다.")
	int awayScore,

	@Schema(description = "좌석 등급", example = "네이비석")
	@NotNull(message = "좌석 등급은 필수입니다.")
	String seatGrade,

	@Schema(description = "좌석 구역", example = "1루")
	@NotNull(message = "좌석 구역은 필수입니다.")
	String seatSection,

	@Schema(description = "좌석 번호", example = "309블럭11열110번")
	@NotNull(message = "좌석 번호는 필수입니다.")
	String seatNumber,

	@Schema(description = "좌석 시야 이미지 URL", example = "https://example.com/seat-view.jpg")
	String seatViewImageUrl,

	@Schema(description = "제목", example = "두산과 KT의 치열한 승부")
	@NotNull(message = "제목은 필수입니다.")
	String title,

	@Schema(description = "내용", example = "두산과 KT의 경기는 정말 치열했습니다. 두 팀 모두 최선을 다해 싸웠고, 관중들도 열광했습니다.")
	@NotNull(message = "내용은 필수입니다.")
	String content,

	@Schema(description = "평점", example = "4")
	int rating
) {
	public GameReviewDTO toGameReviewDto() {
		return GameReviewDTO.builder()
			.userId(userId())
			.gameDate(gameDate())
			.homeTeam(homeTeam())
			.awayTeam(awayTeam())
			.homeScore(homeScore())
			.awayScore(awayScore())
			.seatGrade(seatGrade())
			.seatSection(seatSection())
			.seatNumber(seatNumber())
			.seatViewImageUrl(seatViewImageUrl())
			.title(title())
			.content(content())
			.rating(rating())
			.build();
	}
}
