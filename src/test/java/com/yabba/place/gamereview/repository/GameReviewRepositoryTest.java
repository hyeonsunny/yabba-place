package com.yabba.place.gamereview.repository;

import static com.yabba.place.gamereview.domain.define.Team.*;
import static com.yabba.place.gamereview.domain.define.Team.KT;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.yabba.place.gamereview.domain.dto.GameReviewDTO;
import com.yabba.place.gamereview.domain.entity.GameReview;

@SpringBootTest
@Transactional
class GameReviewRepositoryTest {
	@Autowired
	private GameReviewRepository gameReviewRepository;

	private GameReview GAME_REVIEW;
	@BeforeEach
	void setUp() {
		GameReviewDTO gameReviewDto = GameReviewDTO.builder()
			.userId(47L)
			.gameDate(LocalDate.of(2025, 7, 4))
			.homeTeam(DOOSAN)
			.awayTeam(KT)
			.homeScore(7)
			.awayScore(3)
			.seatGrade("네이비석")
			.seatSection("1루309블럭")
			.seatNumber("11열110번")
			.seatViewImageUrl("")
			.title("두산과 KT의 치열한 승부")
			.content("두산과 KT의 경기는 정말 치열했습니다. 두 팀 모두 최선을 다해 싸웠고, 관중들도 열광했습니다.")
			.rating(4)
			.build();
		GAME_REVIEW = gameReviewRepository.save(gameReviewDto.toGameReview());
	}

	@Test
	@DisplayName("게임 리뷰 ID로 조회 - 삭제되지 않은 경우")
	void test_findByIdAndDeletedAtIsNull_success() {
		Optional<GameReview> gameReviewOptional = gameReviewRepository.findByIdAndDeletedAtIsNull(GAME_REVIEW.getId());

		assertThat(gameReviewOptional).isPresent();
	}
}
