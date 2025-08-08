package com.yabba.place.gameschedule.repository.impl;

import static com.yabba.place.gameschedule.domain.entity.QGameSchedule.*;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yabba.place.gameschedule.domain.dto.GameScheduleSearchDTO;
import com.yabba.place.gameschedule.domain.entity.GameSchedule;
import com.yabba.place.gameschedule.repository.GameScheduleRepositoryCustom;

import jakarta.persistence.EntityManager;

public class GameScheduleRepositoryImpl implements GameScheduleRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	public GameScheduleRepositoryImpl(EntityManager entityManager) {
		this.queryFactory = new JPAQueryFactory(entityManager);
	}

	@Override
	public Page<GameSchedule> search(GameScheduleSearchDTO dto, Pageable pageable) {
		List<GameSchedule> content = queryFactory
			.selectFrom(gameSchedule)
			.where(
				startedAtBetween(dto.from(), dto.to()),
				homeTeamIdEq(dto.homeTeamId()),
				awayTeamIdEq(dto.awayTeamId())
			)
			.orderBy(gameSchedule.startedAt.desc(), gameSchedule.createdAt.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		JPAQuery<Long> countQuery = queryFactory
			.select(gameSchedule.count())
			.from(gameSchedule)
			.where(
				startedAtBetween(dto.from(), dto.to()),
				homeTeamIdEq(dto.homeTeamId()),
				awayTeamIdEq(dto.awayTeamId())
			);

		return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
	}

	private BooleanExpression startedAtBetween(LocalDateTime from, LocalDateTime to) {
		return (from == null || to == null) ? null : gameSchedule.startedAt.between(from, to);
	}

	private BooleanExpression homeTeamIdEq(Long homeTeamId) {
		return (homeTeamId == null) ? null : gameSchedule.homeTeamId.eq(homeTeamId);
	}

	private BooleanExpression awayTeamIdEq(Long awayTeamId) {
		return (awayTeamId == null) ? null : gameSchedule.awayTeamId.eq(awayTeamId);
	}
}
