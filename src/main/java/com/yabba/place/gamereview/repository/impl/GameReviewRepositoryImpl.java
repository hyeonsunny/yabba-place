package com.yabba.place.gamereview.repository.impl;

import static com.yabba.place.gamereview.domain.entity.QGameReview.*;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yabba.place.gamereview.domain.define.Team;
import com.yabba.place.gamereview.domain.dto.GameReviewCondition;
import com.yabba.place.gamereview.domain.entity.GameReview;
import com.yabba.place.gamereview.repository.GameReviewRepositoryCustom;

import jakarta.persistence.EntityManager;

public class GameReviewRepositoryImpl implements GameReviewRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	public GameReviewRepositoryImpl(EntityManager entityManager) {
		this.queryFactory = new JPAQueryFactory(entityManager);
	}

	@Override
	public Page<GameReview> search(GameReviewCondition condition, Pageable pageable) {
		List<GameReview> content = queryFactory
			.selectFrom(gameReview)
			.where(
				gameDateBetween(condition.from(), condition.to()),
				homeTeamNameEq(condition.homeTeam()),
				seatGradeContains(condition.seatGrade()),
				seatSectionContains(condition.seatSection()),
				seatNumberContains(condition.seatNumber()),
				titleContains(condition.title()),
				contentContains(condition.content())
			)
			.orderBy(gameReview.gameDate.desc(), gameReview.createdAt.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		JPAQuery<Long> countQuery = queryFactory
			.select(gameReview.count())
			.from(gameReview)
			.where(
				gameDateBetween(condition.from(), condition.to()),
				homeTeamNameEq(condition.homeTeam()),
				seatGradeContains(condition.seatGrade()),
				seatSectionContains(condition.seatSection()),
				seatNumberContains(condition.seatNumber()),
				titleContains(condition.title()),
				contentContains(condition.content())
			);

		return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
	}

	private BooleanExpression gameDateBetween(LocalDate from, LocalDate to) {
		return (from == null || to == null) ? null : gameReview.gameDate.between(from, to);
	}

	private BooleanExpression homeTeamNameEq(Team homeTeam) {
		return (homeTeam == null) ? null : gameReview.homeTeamName.eq(homeTeam.getName());
	}

	private BooleanExpression seatGradeContains(String seatGrade) {
		return (seatGrade == null || seatGrade.isBlank()) ? null : gameReview.seatGrade.contains(seatGrade);
	}

	private BooleanExpression seatSectionContains(String seatSection) {
		return (seatSection == null || seatSection.isBlank()) ? null : gameReview.seatSection.contains(seatSection);
	}

	private BooleanExpression seatNumberContains(String seatNumber) {
		return (seatNumber == null || seatNumber.isBlank()) ? null : gameReview.seatNumber.contains(seatNumber);
	}

	private BooleanExpression titleContains(String title) {
		return (title == null || title.isBlank()) ? null : gameReview.title.contains(title);
	}

	private BooleanExpression contentContains(String content) {
		return (content == null || content.isBlank()) ? null : gameReview.content.contains(content);
	}
}
