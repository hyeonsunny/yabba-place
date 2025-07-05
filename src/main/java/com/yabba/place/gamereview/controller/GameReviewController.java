package com.yabba.place.gamereview.controller;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yabba.place.gamereview.controller.request.GameReviewRequest;
import com.yabba.place.gamereview.controller.request.GameReviewSearchRequest;
import com.yabba.place.gamereview.controller.response.GameReviewResponse;
import com.yabba.place.gamereview.service.GameReviewService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "GameReview API", description = "경기 리뷰 API")
@RestController
@RequestMapping("/api/game-reviews")
@RequiredArgsConstructor
public class GameReviewController {
	private final GameReviewService gameReviewService;

	@Operation(summary = "경기 리뷰 등록", description = "경기 리뷰를 등록합니다.")
	@PostMapping
	public ResponseEntity<GameReviewResponse> create(
		@Valid @RequestBody GameReviewRequest request
	) {
		return new ResponseEntity<>(
			GameReviewResponse.fromGameReviewDto(gameReviewService.create(request.toGameReviewDto())),
			HttpStatus.CREATED
		);
	}

	@Operation(summary = "경기 리뷰 수정", description = "경기 리뷰를 ID로 수정합니다.")
	@PutMapping("/{id}")
	public ResponseEntity<GameReviewResponse> update(
		@PathVariable(name = "id") String id,
		@Valid @RequestBody GameReviewRequest request
	) {
		return new ResponseEntity<>(
			GameReviewResponse.fromGameReviewDto(gameReviewService.update(id, request.toGameReviewDto())),
			HttpStatus.OK
		);
	}

	@Operation(summary = "경기 리뷰 삭제", description = "경기 리뷰를 ID로 삭제합니다.")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remove(
		@PathVariable(name = "id") String id
	) {
		gameReviewService.remove(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Operation(summary = "경기 리뷰 단건 조회", description = "경기 리뷰를 ID로 조회합니다.")
	@GetMapping("/{id}")
	public ResponseEntity<GameReviewResponse> get(
		@PathVariable(name = "id") String id
	) {
		return new ResponseEntity<>(
			GameReviewResponse.fromGameReviewDto(gameReviewService.get(id)),
			HttpStatus.OK
		);
	}

	@Operation(summary = "경기 리뷰 목록 조회", description = "검색 조건과 페이징으로 경기 리뷰 목록을 조회합니다.")
	@GetMapping("/search")
	public ResponseEntity<List<GameReviewResponse>> search(
		@ParameterObject @ModelAttribute GameReviewSearchRequest request,
		@ParameterObject Pageable pageable
	) {
		return new ResponseEntity<>(
			gameReviewService.search(request.toGameReviewCondition(), pageable)
				.map(GameReviewResponse::fromGameReviewDto)
				.stream().toList()
			, HttpStatus.OK
		);
	}
}
