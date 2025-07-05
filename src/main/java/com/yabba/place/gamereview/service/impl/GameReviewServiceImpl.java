package com.yabba.place.gamereview.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yabba.place.gamereview.domain.dto.GameReviewDTO;
import com.yabba.place.gamereview.domain.dto.GameReviewCondition;
import com.yabba.place.gamereview.domain.entity.GameReview;
import com.yabba.place.gamereview.repository.GameReviewRepository;
import com.yabba.place.gamereview.service.GameReviewService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class GameReviewServiceImpl implements GameReviewService {
	private final GameReviewRepository gameReviewRepository;

	@Override
	public GameReviewDTO create(GameReviewDTO dto) {
		return GameReviewDTO.fromGameReview(gameReviewRepository.save(dto.toGameReview()));
	}

	@Override
	public GameReviewDTO update(String id, GameReviewDTO dto) {
		GameReview gameReview = gameReviewRepository.findByIdAndDeletedAtIsNull(id)
			.orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다. id: " + id));

		gameReview.update(dto);

		return GameReviewDTO.fromGameReview(gameReview);
	}

	@Override
	public void remove(String id) {
		GameReview gameReview = gameReviewRepository.findByIdAndDeletedAtIsNull(id)
			.orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다. id: " + id));

		gameReview.softDelete();
	}

	@Override
	public GameReviewDTO get(String id) {
		GameReview gameReview = gameReviewRepository.findByIdAndDeletedAtIsNull(id)
			.orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다. id: " + id));

		return GameReviewDTO.fromGameReview(gameReview);
	}

	@Override
	public Page<GameReviewDTO> search(GameReviewCondition condition, Pageable pageable) {
		return gameReviewRepository.search(condition, pageable)
			.map(GameReviewDTO::fromGameReview);
	}
}
