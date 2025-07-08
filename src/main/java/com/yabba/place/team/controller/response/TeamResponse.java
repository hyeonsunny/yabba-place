package com.yabba.place.team.controller.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yabba.place.team.domain.dto.TeamDTO;
import com.yabba.place.team.domain.entry.TeamPrefix;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "팀 응답 객체")
public record TeamResponse(
	@Schema(description = "팀 ID")
	long id,

	@Schema(description = "경기장 ID")
	Long stadiumId,

	@Schema(description = "팀 이름")
	String name,

	@Schema(description = "팀 코드")
	TeamPrefix prefix,

	@Schema(description = "등록 일시")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime createdAt
) {
	public static TeamResponse fromTeamDto(TeamDTO dto) {
		return new TeamResponse(
			dto.id(),
			dto.stadiumId(),
			dto.name(),
			dto.prefix(),
			dto.createdAt()
		);
	}
}
