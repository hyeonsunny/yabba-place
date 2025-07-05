package com.yabba.place.gamereview.controller.request;

import java.time.LocalDate;

import com.yabba.place.gamereview.domain.define.Team;
import com.yabba.place.gamereview.domain.dto.GameReviewCondition;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "경기 리뷰 검색 요청 객체")
public record GameReviewSearchRequest(
	@Schema(description = "경기 날짜 시작", example = "2023-10-01")
	LocalDate from,

	@Schema(description = "경기 날짜 종료", example = "2023-10-31")
	LocalDate to,

	@Schema(description = "홈팀", example = "DOOSAN")
	Team homeTeam,

	@Schema(description = "좌석 등급", example = "네이비석")
	String seatGrade,

	@Schema(description = "좌석 구역", example = "1루")
	String seatSection,

	@Schema(description = "좌석 번호", example = "309블럭11열110번")
	String seatNumber,

	@Schema(description = "제목", example = "두산과 KT의 치열한 승부")
	String title,

	@Schema(description = "내용", example = "두산과 KT의 경기는 정말 치열했습니다. 두 팀 모두 최선을 다해 싸웠고, 관중들도 열광했습니다.")
	String content
) {
	public GameReviewCondition toGameReviewCondition() {
		return GameReviewCondition.builder()
			.from(from())
			.to(to())
			.homeTeam(homeTeam())
			.seatGrade(seatGrade())
			.seatSection(seatSection())
			.seatNumber(seatNumber())
			.title(title())
			.content(content())
			.build();
	}
}
