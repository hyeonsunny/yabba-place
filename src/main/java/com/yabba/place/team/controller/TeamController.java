package com.yabba.place.team.controller;

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

import com.yabba.place.team.controller.request.TeamRequest;
import com.yabba.place.team.controller.response.TeamResponse;
import com.yabba.place.team.service.TeamService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Team API", description = "팀 API")
@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class TeamController {
	private final TeamService teamService;

	@Operation(summary = "팀 등록", description = "팀을 등록합니다.")
	@PostMapping
	public ResponseEntity<TeamResponse> create(
		@Valid @RequestBody TeamRequest request
	) {
		return new ResponseEntity<>(
			TeamResponse.fromTeamDto(teamService.create(request.toTeamDto())),
			HttpStatus.CREATED
		);
	}

	@Operation(summary = "팀 수정", description = "팀을 ID로 수정합니다.")
	@PutMapping("/{id}")
	public ResponseEntity<TeamResponse> update(
		@PathVariable(name = "id") long id,
		@Valid @RequestBody TeamRequest request
	) {
		return new ResponseEntity<>(
			TeamResponse.fromTeamDto(teamService.update(id, request.toTeamDto())),
			HttpStatus.OK
		);
	}

	@Operation(summary = "팀 삭제", description = "팀을 ID로 삭제합니다.")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remove(
		@PathVariable(name = "id") long id
	) {
		teamService.remove(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Operation(summary = "팀 단건 조회", description = "팀을 ID로 조회합니다.")
	@GetMapping("/{id}")
	public ResponseEntity<TeamResponse> findById(
		@PathVariable(name = "id") long id
	) {
		return new ResponseEntity<>(
			TeamResponse.fromTeamDto(teamService.get(id)),
			HttpStatus.OK
		);
	}

	@Operation(summary = "팀 목록 조회", description = "팀 목록을 조회합니다.")
	@GetMapping
	public ResponseEntity<List<TeamResponse>> list() {
		return new ResponseEntity<>(
			teamService.list().stream()
				.map(TeamResponse::fromTeamDto)
				.toList(),
			HttpStatus.OK
		);
	}
}
