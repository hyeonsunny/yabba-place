package com.yabba.place.gamereview.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.yabba.place.gamereview.domain.dto.GameReviewDTO;
import com.yabba.place.gamereview.domain.dto.GameReviewCondition;

public interface GameReviewService {
	GameReviewDTO create(GameReviewDTO dto);

	GameReviewDTO update(String id, GameReviewDTO dto);

	void remove(String id);

	GameReviewDTO get(String id);

	Page<GameReviewDTO> search(GameReviewCondition condition, Pageable pageable);
}
