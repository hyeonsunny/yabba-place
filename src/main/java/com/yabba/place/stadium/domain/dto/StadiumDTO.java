package com.yabba.place.stadium.domain.dto;

import java.time.LocalDateTime;

import com.yabba.place.stadium.domain.entity.Stadium;

import lombok.Builder;

@Builder
public record StadiumDTO(
	long id,
	long teamId,
	String name,
	String address,
	LocalDateTime createdAt
) {
	public Stadium toStadium() {
		return Stadium.builder()
			.teamId(teamId())
			.name(name())
			.address(address())
			.build();
	}

	public static StadiumDTO fromStadium(Stadium stadium) {
		return new StadiumDTO(
			stadium.getId(),
			stadium.getTeamId(),
			stadium.getName(),
			stadium.getAddress(),
			stadium.getCreatedAt()
		);
	}
}
