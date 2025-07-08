package com.yabba.place.team.service;

import java.util.List;

import com.yabba.place.team.domain.dto.TeamDTO;

public interface TeamService {
	TeamDTO create(TeamDTO dto);

	TeamDTO update(long id, TeamDTO dto);

	void remove(long id);

	TeamDTO get(long id);

	List<TeamDTO> list();
}
