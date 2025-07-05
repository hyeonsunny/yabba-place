package com.yabba.place.gamereview.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.yabba.place.gamereview.domain.dto.GameReviewCondition;
import com.yabba.place.gamereview.domain.entity.GameReview;

public interface GameReviewRepositoryCustom {
	Page<GameReview> search(GameReviewCondition condition, Pageable pageable);
}
