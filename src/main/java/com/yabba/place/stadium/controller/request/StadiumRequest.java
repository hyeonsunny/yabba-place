package com.yabba.place.stadium.controller.request;

import com.yabba.place.stadium.domain.dto.StadiumDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Schema(description = "경기장 요청 객체")
@Builder
public record StadiumRequest(
	@Schema(description = "소속팀 ID", example = "1")
	@Min(value = 0, message = "소속팀 ID는 0 이상이어야 합니다.")
	long teamId,

	@Schema(description = "경기장 이름", example = "잠실야구장")
	@NotNull(message = "경기장 이름은 필수입니다.")
	String name,

	@Schema(description = "경기장 주소", example = "서울특별시 송파구 올림픽로 25")
	String address
) {
	public StadiumDTO toStadiumDto() {
		return StadiumDTO.builder()
			.teamId(teamId())
			.name(name())
			.address(address())
			.build();
	}
}
