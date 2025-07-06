package com.yabba.place.stadium.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yabba.place.stadium.domain.entity.Stadium;

@Repository
public interface StadiumRepository extends JpaRepository<Stadium, Long> {
	Optional<Stadium> findByIdAndDeletedAtIsNull(long id);

	List<Stadium> findAllByDeletedAtIsNull();
}
