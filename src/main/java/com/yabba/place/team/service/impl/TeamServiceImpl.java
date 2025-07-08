package com.yabba.place.team.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yabba.place.team.domain.dto.TeamDTO;
import com.yabba.place.team.domain.entity.Team;
import com.yabba.place.team.repository.TeamRepository;
import com.yabba.place.team.service.TeamService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TeamServiceImpl implements TeamService {
	private final TeamRepository teamRepository;

	@Override
	public TeamDTO create(TeamDTO dto) {
		return TeamDTO.fromTeam(teamRepository.save(dto.toTeam()));
	}

	@Override
	public TeamDTO update(long id, TeamDTO dto) {
		Team team = teamRepository.findByIdAndDeletedAtIsNull(id)
			.orElseThrow(() -> new IllegalArgumentException("팀이 존재하지 않습니다. id: " + id));

		team.update(dto);

		return TeamDTO.fromTeam(team);
	}

	@Override
	public void remove(long id) {
		Team team = teamRepository.findByIdAndDeletedAtIsNull(id)
			.orElseThrow(() -> new IllegalArgumentException("팀이 존재하지 않습니다. id: " + id));

		team.softDelete();
	}

	@Override
	public TeamDTO get(long id) {
		Team team = teamRepository.findByIdAndDeletedAtIsNull(id)
			.orElseThrow(() -> new IllegalArgumentException("팀이 존재하지 않습니다. id: " + id));

		return TeamDTO.fromTeam(team);
	}

	@Override
	public List<TeamDTO> list() {
		return teamRepository.findAllByDeletedAtIsNull()
			.stream()
			.map(TeamDTO::fromTeam)
			.collect(Collectors.toList());
	}
}
