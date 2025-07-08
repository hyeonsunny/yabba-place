package com.yabba.place.team.controller.request;

import com.yabba.place.team.domain.dto.TeamDTO;
import com.yabba.place.team.domain.entry.TeamPrefix;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Schema(description = "팀 요청 객체")
@Builder
public record TeamRequest(
	@Schema(description = "경기장 ID", example = "1")
	@Min(value = 0, message = "경기장 ID는 0 이상이어야 합니다.")
	long stadiumId,

	@Schema(description = "팀 이름", example = "두산베어스")
	@NotNull(message = "팀 이름은 필수입니다.")
	String name,

	@Schema(description = "팀 코드", example = "두산")
	@NotNull(message = "팀 코드는 필수입니다.")
	TeamPrefix prefix
) {
	public TeamDTO toTeamDto() {
		return TeamDTO.builder()
			.stadiumId(stadiumId())
			.name(name())
			.prefix(prefix())
			.build();
	}
}
