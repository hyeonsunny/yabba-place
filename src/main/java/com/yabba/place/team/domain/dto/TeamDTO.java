package com.yabba.place.team.domain.dto;

import java.time.LocalDateTime;

import com.yabba.place.team.domain.entity.Team;
import com.yabba.place.team.domain.entry.TeamPrefix;

import lombok.Builder;

@Builder
public record TeamDTO(
	long id,
	long stadiumId,
	String name,
	TeamPrefix prefix,
	LocalDateTime createdAt
) {
	public Team toTeam() {
		return Team.builder()
			.stadiumId(stadiumId())
			.name(name())
			.prefix(prefix())
			.build();
	}

	public static TeamDTO fromTeam(Team team) {
		return TeamDTO.builder()
			.id(team.getId())
			.stadiumId(team.getStadiumId())
			.name(team.getName())
			.prefix(team.getPrefix())
			.createdAt(team.getCreatedAt())
			.build();
	}
}
