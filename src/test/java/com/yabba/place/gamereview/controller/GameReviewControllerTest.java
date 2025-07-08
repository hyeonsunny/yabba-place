package com.yabba.place.gamereview.controller;

import static com.yabba.place.gamereview.domain.define.Team.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yabba.place.common.BaseIntegrationTest;
import com.yabba.place.gamereview.controller.request.GameReviewRequest;
import com.yabba.place.gamereview.domain.dto.GameReviewDTO;
import com.yabba.place.gamereview.service.GameReviewService;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class GameReviewControllerTest extends BaseIntegrationTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private GameReviewService gameReviewService;

	private final GameReviewRequest GAME_REVIEW_REQUEST = GameReviewRequest.builder()
		.userId(47L)
		.gameDate(LocalDate.of(2025, 7, 4))
		.homeTeam(DOOSAN)
		.awayTeam(KT)
		.homeScore(7)
		.awayScore(3)
		.seatGrade("네이비석")
		.seatSection("1루309블럭")
		.seatNumber("11열110번")
		.seatViewImageUrl("")
		.title("두산과 KT의 치열한 승부")
		.content("두산과 KT의 경기는 정말 치열했습니다. 두 팀 모두 최선을 다해 싸웠고, 관중들도 열광했습니다.")
		.rating(4)
		.build();

	private GameReviewDTO RESULT_GAME_REVIEW_DTO;
	@BeforeEach
	void setUp() {
		GameReviewDTO GameReviewDto = GameReviewDTO.builder()
			.userId(63L)
			.gameDate(LocalDate.of(2025, 7, 4))
			.homeTeam(DOOSAN)
			.awayTeam(KT)
			.homeScore(7)
			.awayScore(3)
			.seatGrade("네이비석")
			.seatSection("1루309블럭")
			.seatNumber("44열44번")
			.seatViewImageUrl("")
			.title("두산과 KT의 치열한 승부")
			.content("두산과 KT의 경기는 정말 치열했습니다. 두 팀 모두 최선을 다해 싸웠고, 관중들도 열광했습니다.")
			.rating(4)
			.build();
		RESULT_GAME_REVIEW_DTO = gameReviewService.create(GameReviewDto);
	}

	@Test
	@DisplayName("경기 리뷰 등록 성공")
	void test_create_success() throws Exception {
		mockMvc.perform(post("/api/game-reviews")
				.contentType(MediaType.APPLICATION_JSON.toString())
				.content(objectMapper.writeValueAsString(GAME_REVIEW_REQUEST)))
			.andExpect(status().isCreated());
	}

	@Test
	@DisplayName("경기 리뷰 수정 성공")
	void test_update_success() throws Exception {
		GameReviewRequest gameReviewRequest = GameReviewRequest.builder()
			.userId(63L)
			.gameDate(LocalDate.of(2025, 7, 5))
			.homeTeam(DOOSAN)
			.awayTeam(KT)
			.homeScore(763)
			.awayScore(3)
			.seatGrade("네이비석")
			.seatSection("1루309블럭")
			.seatNumber("44열44번")
			.seatViewImageUrl("")
			.title("두산과 KT의 치열한 승부")
			.content("두산과 KT의 경기는 정말 치열했습니다. 두 팀 모두 최선을 다해 싸웠고, 관중들도 열광했습니다.")
			.rating(4)
			.build();

		mockMvc.perform(put("/api/game-reviews/{id}", RESULT_GAME_REVIEW_DTO.id())
				.contentType(MediaType.APPLICATION_JSON.toString())
				.content(objectMapper.writeValueAsString(gameReviewRequest)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.gameDate").value("2025-07-05"));
	}

	@Test
	@DisplayName("경기 리뷰 삭제 성공")
	void test_remove_success() throws Exception {
		mockMvc.perform(delete("/api/game-reviews/{id}", RESULT_GAME_REVIEW_DTO.id()))
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("경기 리뷰 조회 성공")
	void test_get_success() throws Exception {
		mockMvc.perform(get("/api/game-reviews/{id}", RESULT_GAME_REVIEW_DTO.id()))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(RESULT_GAME_REVIEW_DTO.id()))
			.andExpect(jsonPath("$.userId").value(RESULT_GAME_REVIEW_DTO.userId()))
			.andExpect(jsonPath("$.gameDate").value(RESULT_GAME_REVIEW_DTO.gameDate().toString()))
			.andExpect(jsonPath("$.stadiumName").value(RESULT_GAME_REVIEW_DTO.homeTeam().getStadiumName()))
			.andExpect(jsonPath("$.homeTeamName").value(RESULT_GAME_REVIEW_DTO.homeTeam().getName()))
			.andExpect(jsonPath("$.awayTeamName").value(RESULT_GAME_REVIEW_DTO.awayTeam().getName()))
			.andExpect(jsonPath("$.homeScore").value(RESULT_GAME_REVIEW_DTO.homeScore()))
			.andExpect(jsonPath("$.awayScore").value(RESULT_GAME_REVIEW_DTO.awayScore()))
			.andExpect(jsonPath("$.seatGrade").value(RESULT_GAME_REVIEW_DTO.seatGrade()))
			.andExpect(jsonPath("$.seatSection").value(RESULT_GAME_REVIEW_DTO.seatSection()))
			.andExpect(jsonPath("$.seatNumber").value(RESULT_GAME_REVIEW_DTO.seatNumber()))
			.andExpect(jsonPath("$.seatViewImageUrl").value(RESULT_GAME_REVIEW_DTO.seatViewImageUrl()))
			.andExpect(jsonPath("$.title").value(RESULT_GAME_REVIEW_DTO.title()))
			.andExpect(jsonPath("$.content").value(RESULT_GAME_REVIEW_DTO.content()))
			.andExpect(jsonPath("$.rating").value(RESULT_GAME_REVIEW_DTO.rating()));
	}

	@Test
	@DisplayName("경기 리뷰 검색 성공")
	void test_search_success() throws Exception {
		mockMvc.perform(get("/api/game-reviews/search")
				.param("from", LocalDate.of(2025, 7, 1).toString())
				.param("to", LocalDate.of(2025, 7, 31).toString())
				.param("page", "0")
				.param("size", "10"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(1)));
	}
}
