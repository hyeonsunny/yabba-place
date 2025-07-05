package com.yabba.place.gamereview.domain.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.DynamicUpdate;

import com.github.f4b6a3.tsid.TsidCreator;
import com.yabba.place.gamereview.domain.dto.GameReviewDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@DynamicUpdate
@Entity
@Table(name = "game_review")
public class GameReview {
	@Id
	private String id;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "game_date")
	private LocalDate gameDate;

	@Column(name = "stadium_name")
	private String stadiumName;

	@Column(name = "home_team_name")
	private String homeTeamName;

	@Column(name = "away_team_name")
	private String awayTeamName;

	@Column(name = "home_score")
	private Integer homeScore;

	@Column(name = "away_score")
	private Integer awayScore;

	@Column(name = "seat_grade")
	private String seatGrade;

	@Column(name = "seat_section")
	private String seatSection;

	@Column(name = "seat_number")
	private String seatNumber;

	@Column(name = "seat_view_image_url")
	private String seatViewImageUrl;

	@Column(name = "title")
	private String title;

	@Column(name = "content")
	private String content;

	@Column(name = "rating")
	private Integer rating;

	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

	@PrePersist
	public void prePersist() {
		this.createdAt = LocalDateTime.now();
		this.id = TsidCreator.getTsid().toString();
	}

	@PreUpdate
	public void preUpdate() {
		this.updatedAt = LocalDateTime.now();
	}

	public void softDelete() {
		this.deletedAt = LocalDateTime.now();
	}

	public void update(GameReviewDTO dto) {
		this.gameDate = dto.gameDate();
		this.stadiumName = dto.homeTeam().getStadiumName();
		this.homeTeamName = dto.homeTeam().getName();
		this.awayTeamName = dto.awayTeam().getName();
		this.homeScore = dto.homeScore();
		this.awayScore = dto.awayScore();
		this.seatGrade = dto.seatGrade();
		this.seatSection = dto.seatSection();
		this.seatNumber = dto.seatNumber();
		this.seatViewImageUrl = dto.seatViewImageUrl();
		this.title = dto.title();
		this.content = dto.content();
		this.rating = dto.rating();
	}
}
