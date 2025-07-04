package com.yabba.place.gamereview.domain.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.yabba.place.gamereview.domain.define.Team;
import com.yabba.place.gamereview.domain.entity.GameReview;

import lombok.Builder;

@Builder
public record GameReviewDTO(
	String id,
	long userId,
	LocalDate gameDate,
	Team homeTeam,
	Team awayTeam,
	int homeScore,
	int awayScore,
	String seatGrade,
	String seatSection,
	String seatNumber,
	String seatViewImageUrl,
	String title,
	String content,
	int rating,
	LocalDateTime createdAt
) {
	public GameReview toGameReview() {
		return GameReview.builder()
			.userId(userId())
			.gameDate(gameDate())
			.stadiumName(homeTeam().getStadiumName())
			.homeTeamName(homeTeam().getName())
			.awayTeamName(awayTeam().getName())
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

	public static GameReviewDTO fromGameReview(GameReview gameReview) {
		return new GameReviewDTO(
			gameReview.getId(),
			gameReview.getUserId(),
			gameReview.getGameDate(),
			Team.fromName(gameReview.getHomeTeamName()),
			Team.fromName(gameReview.getAwayTeamName()),
			gameReview.getHomeScore(),
			gameReview.getAwayScore(),
			gameReview.getSeatGrade(),
			gameReview.getSeatSection(),
			gameReview.getSeatNumber(),
			gameReview.getSeatViewImageUrl(),
			gameReview.getTitle(),
			gameReview.getContent(),
			gameReview.getRating(),
			gameReview.getCreatedAt()
		);
	}
}
