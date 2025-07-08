package com.yabba.place.team.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yabba.place.team.domain.entity.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
	Optional<Team> findByIdAndDeletedAtIsNull(Long id);

	List<Team> findAllByDeletedAtIsNull();
}
