package com.yabba.place.gameschedule.service.impl;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import com.yabba.place.common.BaseIntegrationTest;
import com.yabba.place.gameschedule.domain.define.GameStatus;
import com.yabba.place.gameschedule.domain.dto.GameScheduleSearchDTO;
import com.yabba.place.gameschedule.domain.dto.GameScheduleDTO;
import com.yabba.place.gameschedule.repository.GameScheduleRepository;
import com.yabba.place.gameschedule.service.GameScheduleService;

@SpringBootTest
@Transactional
class GameScheduleServiceImplTest extends BaseIntegrationTest {
	@Autowired
	private GameScheduleRepository gameScheduleRepository;

	@Autowired
	private GameScheduleService gameScheduleService;

	private GameScheduleDTO RESULT_GAME_SCHEDULE;
	@BeforeEach
	void setUp() {
		GameScheduleDTO gameScheduleDto = GameScheduleDTO.builder()
			.homeTeamId(1L)
			.awayTeamId(2L)
			.status(GameStatus.READY)
			.startedAt(LocalDateTime.of(2025, 8, 24, 18, 0))
			.build();

		RESULT_GAME_SCHEDULE = gameScheduleService.create(gameScheduleDto);
	}

	@Test
	@DisplayName("게임 일정 생성 성공")
	void test_create_success() {
		GameScheduleDTO gameScheduleCreateDto = GameScheduleDTO.builder()
			.homeTeamId(1L)
			.awayTeamId(2L)
			.status(GameStatus.READY)
			.startedAt(LocalDateTime.of(2025, 8, 23, 18, 0))
			.build();

		GameScheduleDTO gameScheduleDto = gameScheduleService.create(gameScheduleCreateDto);

		assertThat(gameScheduleDto).isNotNull();
		assertThat(gameScheduleDto.homeTeamId()).isEqualTo(gameScheduleCreateDto.homeTeamId());
		assertThat(gameScheduleDto.awayTeamId()).isEqualTo(gameScheduleCreateDto.awayTeamId());
		assertThat(gameScheduleDto.status()).isEqualTo(gameScheduleCreateDto.status());
		assertThat(gameScheduleDto.startedAt()).isEqualTo(gameScheduleCreateDto.startedAt());
	}

	@Test
	@DisplayName("경기 일정 수정시 기존 경기 일정이 존재하지 않을 경우 예외 발생")
	void test_update_throwException() {
		assertThatThrownBy(() -> gameScheduleService.update(4L, GameScheduleDTO.builder().build()))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("게임 일정이 존재하지 않습니다. id: " + 4L);
	}

	@Test
	@DisplayName("경기 일정 수정 성공")
	void test_update_success() {
		GameScheduleDTO gameScheduleUpdateDto = GameScheduleDTO.builder()
			.homeTeamId(1L)
			.awayTeamId(2L)
			.status(GameStatus.READY)
			.startedAt(LocalDateTime.of(2025, 8, 22, 18, 0))
			.build();

		GameScheduleDTO gameScheduleDto = gameScheduleService.update(RESULT_GAME_SCHEDULE.id(), gameScheduleUpdateDto);

		assertThat(gameScheduleDto).isNotNull();
		assertThat(gameScheduleDto.startedAt()).isEqualTo(LocalDateTime.of(2025, 8, 22, 18, 0));
	}

	@Test
	@DisplayName("경기 일정 삭제시 기존 경기 일정이 존재하지 않을 경우 예외 발생")
	void test_remove_throwException() {
		assertThatThrownBy(() -> gameScheduleService.remove(4L))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("게임 일정이 존재하지 않습니다. id: " + 4L);
	}

	@Test
	@DisplayName("경기 일정 삭제 성공")
	void test_remove_success() {
		gameScheduleService.remove(RESULT_GAME_SCHEDULE.id());

		gameScheduleRepository.findById(RESULT_GAME_SCHEDULE.id())
			.ifPresent(gameSchedule -> assertThat(gameSchedule.getDeletedAt()).isNotNull());
	}

	@Test
	@DisplayName("경기 일정 조회 성공")
	void test_get_success() {
		GameScheduleDTO gameScheduleDto = gameScheduleService.get(RESULT_GAME_SCHEDULE.id());

		assertThat(gameScheduleDto).isNotNull();
		assertThat(gameScheduleDto.homeTeamId()).isEqualTo(RESULT_GAME_SCHEDULE.homeTeamId());
		assertThat(gameScheduleDto.awayTeamId()).isEqualTo(RESULT_GAME_SCHEDULE.awayTeamId());
		assertThat(gameScheduleDto.status()).isEqualTo(RESULT_GAME_SCHEDULE.status());
		assertThat(gameScheduleDto.startedAt()).isEqualTo(RESULT_GAME_SCHEDULE.startedAt());
	}

	@Test
	@DisplayName("게임 일정 검색 시 결과 없음 반환")
	void test_search_returnPageEmpty() {
		GameScheduleSearchDTO gameScheduleSearchDto = GameScheduleSearchDTO.builder()
			.from(LocalDateTime.of(2025, 9, 24, 18, 0))
			.to(LocalDateTime.of(2025, 10, 24, 18, 0))
			.build();

		Page<GameScheduleDTO> gameSchedulePage = gameScheduleService.search(gameScheduleSearchDto, PageRequest.of(0, 10));

		assertThat(gameSchedulePage.getContent()).isEmpty();
		assertThat(gameSchedulePage.getTotalElements()).isEqualTo(0);
	}

	@Test
	@DisplayName("게임 일정 검색 시 결과 반환")
	void test_search_returnPage() {
		GameScheduleSearchDTO gameScheduleSearchDto = GameScheduleSearchDTO.builder()
			.from(LocalDateTime.of(2025, 8, 24, 18, 0))
			.to(LocalDateTime.of(2025, 8, 24, 18, 0))
			.build();

		Page<GameScheduleDTO> gameSchedulePage = gameScheduleService.search(gameScheduleSearchDto, PageRequest.of(0, 10));

		assertThat(gameSchedulePage.getContent()).isNotEmpty();
		assertThat(gameSchedulePage.getTotalElements()).isEqualTo(1);
	}
}
