package com.yabba.place.team.controller;

import static com.yabba.place.team.domain.entry.TeamPrefix.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yabba.place.team.controller.request.TeamRequest;
import com.yabba.place.team.domain.dto.TeamDTO;
import com.yabba.place.team.service.TeamService;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class TeamControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private TeamService teamService;

	private final TeamRequest TEAM_REQUEST = TeamRequest.builder()
		.stadiumId(1L)
		.name("두산베어스")
		.prefix(DOOSAN)
		.build();

	private TeamDTO RESULT_TEAM_DTO;
	@BeforeEach
	void setUp() {
		TeamDTO teamDto = TeamDTO.builder()
			.stadiumId(2L)
			.name("KT위즈")
			.prefix(KT)
			.build();
		RESULT_TEAM_DTO = teamService.create(teamDto);
	}

	@Test
	@DisplayName("팀 등록 성공")
	void test_create_success() throws Exception {
		mockMvc.perform(post("/api/teams")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(TEAM_REQUEST)))
			.andExpect(status().isCreated());
	}

	@Test
	@DisplayName("팀 수정 성공")
	void test_update_success() throws Exception {
		TeamRequest teamRequest = TeamRequest.builder()
			.stadiumId(1L)
			.name("최강두산베어스")
			.prefix(DOOSAN)
			.build();

		mockMvc.perform(put("/api/teams/{id}", RESULT_TEAM_DTO.id())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(teamRequest)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name").value("최강두산베어스"));
	}

	@Test
	@DisplayName("팀 삭제 성공")
	void test_remove_success() throws Exception {
		mockMvc.perform(delete("/api/teams/{id}", RESULT_TEAM_DTO.id()))
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("팀 조회 성공")
	void test_get_success() throws Exception {
		mockMvc.perform(get("/api/teams/{id}", RESULT_TEAM_DTO.id()))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name").value("KT위즈"))
			.andExpect(jsonPath("$.prefix").value("KT"));
	}

	@Test
	@DisplayName("팀 목록 조회 성공")
	void test_list_success() throws Exception {
		mockMvc.perform(get("/api/teams"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(1)));
	}
}
