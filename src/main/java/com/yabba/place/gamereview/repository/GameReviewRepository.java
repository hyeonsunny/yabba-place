package com.yabba.place.gamereview.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yabba.place.gamereview.domain.entity.GameReview;

@Repository
public interface GameReviewRepository extends JpaRepository<GameReview, String>, GameReviewRepositoryCustom {
	Optional<GameReview> findByIdAndDeletedAtIsNull(String id);
}
