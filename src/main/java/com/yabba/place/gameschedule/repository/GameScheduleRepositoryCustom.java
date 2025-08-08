package com.yabba.place.gameschedule.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.yabba.place.gameschedule.domain.dto.GameScheduleSearchDTO;
import com.yabba.place.gameschedule.domain.entity.GameSchedule;

public interface GameScheduleRepositoryCustom {
	Page<GameSchedule> search(GameScheduleSearchDTO dto, Pageable pageable);
}
