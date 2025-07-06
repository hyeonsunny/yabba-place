package com.yabba.place.stadium.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yabba.place.stadium.controller.request.StadiumRequest;
import com.yabba.place.stadium.controller.response.StadiumResponse;
import com.yabba.place.stadium.service.StadiumService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Stadium API", description = "경기장 API")
@RestController
@RequestMapping("/api/stadiums")
@RequiredArgsConstructor
public class StadiumController {
	private final StadiumService stadiumService;

	@Operation(summary = "경기장 등록", description = "경기장을 등록합니다.")
	@PostMapping
	public ResponseEntity<StadiumResponse> create(
		@Valid @RequestBody StadiumRequest request
	) {
		return new ResponseEntity<>(
			StadiumResponse.fromStadiumDto(stadiumService.create(request.toStadiumDto())),
			HttpStatus.CREATED
		);
	}

	@Operation(summary = "경기장 수정", description = "경기장을 ID로 수정합니다.")
	@PutMapping("/{id}")
	public ResponseEntity<StadiumResponse> update(
		@PathVariable(name = "id") long id,
		@Valid @RequestBody StadiumRequest request
	) {
		return new ResponseEntity<>(
			StadiumResponse.fromStadiumDto(stadiumService.update(id, request.toStadiumDto())),
			HttpStatus.OK
		);
	}

	@Operation(summary = "경기장 삭제", description = "경기장을 ID로 삭제합니다.")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remove(
		@PathVariable(name = "id") long id
	) {
		stadiumService.remove(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Operation(summary = "경기장 단건 조회", description = "경기장을 ID로 조회합니다.")
	@GetMapping("/{id}")
	public ResponseEntity<StadiumResponse> get(
		@PathVariable(name = "id") long id
	) {
		return new ResponseEntity<>(
			StadiumResponse.fromStadiumDto(stadiumService.get(id)),
			HttpStatus.OK
		);
	}

	@Operation(summary = "경기장 목록 조회", description = "경기장 목록을 조회합니다.")
	@GetMapping
	public ResponseEntity<List<StadiumResponse>> list() {
		return new ResponseEntity<>(
			stadiumService.list()
				.stream()
				.map(StadiumResponse::fromStadiumDto)
				.toList(),
			HttpStatus.OK
		);
	}
}
