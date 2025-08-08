package com.yabba.place.gameschedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yabba.place.gameschedule.domain.entity.GameSchedule;

@Repository
public interface GameScheduleRepository extends JpaRepository<GameSchedule, Long>, GameScheduleRepositoryCustom {
}
