package com.yabba.place.gameschedule.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.yabba.place.gameschedule.domain.dto.GameScheduleSearchDTO;
import com.yabba.place.gameschedule.domain.dto.GameScheduleDTO;
import com.yabba.place.gameschedule.domain.entity.GameSchedule;
import com.yabba.place.gameschedule.repository.GameScheduleRepository;
import com.yabba.place.gameschedule.service.GameScheduleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GameScheduleServiceImpl implements GameScheduleService {
	private final GameScheduleRepository gameScheduleRepository;

	@Override
	public GameScheduleDTO create(GameScheduleDTO dto) {
		return GameScheduleDTO.fromGameSchedule(gameScheduleRepository.save(dto.toGameSchedule()));
	}

	@Override
	public GameScheduleDTO update(long id, GameScheduleDTO dto) {
		GameSchedule gameSchedule = gameScheduleRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("게임 일정이 존재하지 않습니다. id: " + id));

		gameSchedule.update(dto);

		return GameScheduleDTO.fromGameSchedule(gameSchedule);
	}

	@Override
	public void remove(long id) {
		GameSchedule gameSchedule = gameScheduleRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("게임 일정이 존재하지 않습니다. id: " + id));

		gameSchedule.softDelete();
	}

	@Override
	public GameScheduleDTO get(long id) {
		GameSchedule gameSchedule = gameScheduleRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("게임 일정이 존재하지 않습니다. id: " + id));

		return GameScheduleDTO.fromGameSchedule(gameSchedule);
	}

	@Override
	public Page<GameScheduleDTO> search(GameScheduleSearchDTO dto, Pageable pageable) {
		return gameScheduleRepository.search(dto, pageable)
			.map(GameScheduleDTO::fromGameSchedule);
	}
}
