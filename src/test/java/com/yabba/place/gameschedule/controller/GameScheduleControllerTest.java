package com.yabba.place.gameschedule.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.yabba.place.common.BaseIntegrationTest;
import com.yabba.place.gameschedule.domain.define.GameStatus;
import com.yabba.place.gameschedule.domain.dto.GameScheduleDTO;
import com.yabba.place.gameschedule.service.GameScheduleService;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class GameScheduleControllerTest extends BaseIntegrationTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private GameScheduleService gameScheduleService;

	private GameScheduleDTO RESULT_GAME_SCHEDULE;
	@BeforeEach
	void setUp() {
		GameScheduleDTO gameScheduleDto = GameScheduleDTO.builder()
			.homeTeamId(1L)
			.awayTeamId(2L)
			.status(GameStatus.READY)
			.startedAt(LocalDateTime.of(2025, 8, 24, 18, 0, 0))
			.build();

		RESULT_GAME_SCHEDULE = gameScheduleService.create(gameScheduleDto);
		System.out.println("RESULT_GAME_SCHEDULE = " + RESULT_GAME_SCHEDULE);
	}

	@Test
	@DisplayName("경기 일정 조회 성공")
	void test_get_success() throws Exception {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		mockMvc.perform(get("/api/game-schedules/{id}", RESULT_GAME_SCHEDULE.id()))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(RESULT_GAME_SCHEDULE.id()))
			.andExpect(jsonPath("$.homeTeamId").value(RESULT_GAME_SCHEDULE.homeTeamId()))
			.andExpect(jsonPath("$.awayTeamId").value(RESULT_GAME_SCHEDULE.awayTeamId()))
			.andExpect(jsonPath("$.status").value(RESULT_GAME_SCHEDULE.status().name()))
			.andExpect(jsonPath("$.startedAt").value(RESULT_GAME_SCHEDULE.startedAt().format(formatter)))
			.andExpect(jsonPath("$.homeTeamScore").value(RESULT_GAME_SCHEDULE.homeTeamScore()))
			.andExpect(jsonPath("$.awayTeamScore").value(RESULT_GAME_SCHEDULE.awayTeamScore()))
			.andExpect(jsonPath("$.winnerTeamId").value(RESULT_GAME_SCHEDULE.winnerTeamId()))
			.andExpect(jsonPath("$.createdAt").value(RESULT_GAME_SCHEDULE.createdAt().format(formatter)));
	}

	@Test
	@DisplayName("경기 일정 검색 성공")
	void test_search_success() throws Exception {
		mockMvc.perform(get("/api/game-schedules/search")
			.param("from", LocalDateTime.of(2025, 8, 1, 18, 0, 0).toString())
			.param("to", LocalDateTime.of(2025, 8, 31, 18, 0, 0).toString())
			.param("page", "0")
			.param("size", "10"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(1)));
	}
}
