package com.yabba.place.gameschedule.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.yabba.place.common.BaseIntegrationTest;
import com.yabba.place.gameschedule.domain.define.GameStatus;
import com.yabba.place.gameschedule.domain.dto.GameScheduleSearchDTO;
import com.yabba.place.gameschedule.domain.dto.GameScheduleDTO;
import com.yabba.place.gameschedule.domain.entity.GameSchedule;

@DataJpaTest
class GameScheduleRepositoryTest extends BaseIntegrationTest {
	@Autowired
	private GameScheduleRepository gameScheduleRepository;

	@BeforeEach
	void setUp() {
		GameScheduleDTO gameScheduleDto = GameScheduleDTO.builder()
			.homeTeamId(1L)
			.awayTeamId(2L)
			.status(GameStatus.READY)
			.startedAt(LocalDateTime.of(2025, 8, 24, 18, 0))
			.build();

		gameScheduleRepository.save(gameScheduleDto.toGameSchedule());
	}

	@Test
	@DisplayName("게임 일정 검색 성공")
	void test_search_success() {
		GameScheduleSearchDTO gameScheduleSearchDto = GameScheduleSearchDTO.builder()
			.homeTeamId(1L)
			.build();

		Page<GameSchedule> gameSchedulePage = gameScheduleRepository.search(gameScheduleSearchDto, PageRequest.of(0, 10));

		assertThat(gameSchedulePage).hasSize(1);
	}
}
