package com.yabba.place.stadium.controller.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yabba.place.stadium.domain.dto.StadiumDTO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "경기장 응답 객체")
public record StadiumResponse(
	@Schema(description = "경기장 ID")
	long id,

	@Schema(description = "소속팀 ID")
	long teamId,

	@Schema(description = "경기장 이름")
	String name,

	@Schema(description = "경기장 주소")
	String address,

	@Schema(description = "등록 일시")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime createdAt
) {
	public static StadiumResponse fromStadiumDto(StadiumDTO dto) {
		return new StadiumResponse(
			dto.id(),
			dto.teamId(),
			dto.name(),
			dto.address(),
			dto.createdAt()
		);
	}
}
