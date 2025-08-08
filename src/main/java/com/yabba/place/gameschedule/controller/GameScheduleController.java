package com.yabba.place.gameschedule.controller;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yabba.place.gameschedule.controller.request.GameScheduleSearchRequest;
import com.yabba.place.gameschedule.controller.response.GameScheduleResponse;
import com.yabba.place.gameschedule.service.GameScheduleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "GameSchedule API", description = "경기 일정 API")
@RestController
@RequestMapping("/api/game-schedules")
@RequiredArgsConstructor
public class GameScheduleController {
	private final GameScheduleService gameScheduleService;

	@Operation(summary = "경기 일정 단건 조회", description = "경기 일정을 ID로 조회합니다.")
	@GetMapping("/{id}")
	public ResponseEntity<GameScheduleResponse> get(
		@PathVariable(name = "id") long id
	) {
		return new ResponseEntity<>(
			GameScheduleResponse.fromGameScheduleDto(gameScheduleService.get(id)),
			HttpStatus.OK
		);
	}

	@Operation(summary = "경기 일정 목록 조회", description = "검색 조건과 페이징으로 경기 일정 목록을 조회합니다.")
	@GetMapping("/search")
	public ResponseEntity<List<GameScheduleResponse>> search(
		@ParameterObject @ModelAttribute GameScheduleSearchRequest request,
		@ParameterObject Pageable pageable
	) {
		return new ResponseEntity<>(
			gameScheduleService.search(request.toGameScheduleSearchDto(), pageable)
				.map(GameScheduleResponse::fromGameScheduleDto)
				.stream().toList(),
			HttpStatus.OK
		);
	}
}
