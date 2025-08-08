package com.yabba.place.gameschedule.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.yabba.place.gameschedule.domain.dto.GameScheduleSearchDTO;
import com.yabba.place.gameschedule.domain.dto.GameScheduleDTO;

public interface GameScheduleService {
	GameScheduleDTO create(GameScheduleDTO dto);

	GameScheduleDTO update(long id, GameScheduleDTO dto);

	void remove(long id);

	GameScheduleDTO get(long id);

	Page<GameScheduleDTO> search(GameScheduleSearchDTO dto, Pageable pageable);
}
