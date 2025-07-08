package com.yabba.place.stadium.controller;

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
import com.yabba.place.common.BaseIntegrationTest;
import com.yabba.place.stadium.controller.request.StadiumRequest;
import com.yabba.place.stadium.domain.dto.StadiumDTO;
import com.yabba.place.stadium.service.StadiumService;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class StadiumControllerTest extends BaseIntegrationTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private StadiumService stadiumService;

	private final StadiumRequest STADIUM_REQUEST = StadiumRequest.builder()
		.teamId(1L)
		.name("잠실야구장")
		.address("서울특별시 송파구 올림픽로 19-2")
		.build();

	private StadiumDTO RESULT_STADIUM_DTO;
	@BeforeEach
	void setUp() {
		StadiumDTO stadiumDto = StadiumDTO.builder()
			.teamId(2L)
			.name("수원KT위즈파크")
			.address("경기도 수원시 장안구 경수대로 893")
			.build();
		RESULT_STADIUM_DTO = stadiumService.create(stadiumDto);
	}

	@Test
	@DisplayName("경기장 등록 성공")
	void test_create_success() throws Exception {
		mockMvc.perform(post("/api/stadiums")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(STADIUM_REQUEST)))
			.andExpect(status().isCreated());
	}

	@Test
	@DisplayName("경기장 수정 성공")
	void test_update_success() throws Exception {
		StadiumRequest stadiumRequest = StadiumRequest.builder()
			.teamId(1L)
			.name("잠실종합야구장")
			.address("서울특별시 송파구 올림픽로 19-2")
			.build();

		mockMvc.perform(put("/api/stadiums/{id}", RESULT_STADIUM_DTO.id())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(stadiumRequest)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name").value("잠실종합야구장"));
	}

	@Test
	@DisplayName("경기장 삭제 성공")
	void test_remove_success() throws Exception {
		mockMvc.perform(delete("/api/stadiums/{id}", RESULT_STADIUM_DTO.id()))
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("경기장 조회 성공")
	void test_get_success() throws Exception {
		mockMvc.perform(get("/api/stadiums/{id}", RESULT_STADIUM_DTO.id()))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name").value("수원KT위즈파크"))
			.andExpect(jsonPath("$.address").value("경기도 수원시 장안구 경수대로 893"));
	}

	@Test
	@DisplayName("경기장 목록 조회 성공")
	void test_list_success() throws Exception {
		mockMvc.perform(get("/api/stadiums"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(1)));
	}
}
