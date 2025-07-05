package com.yabba.place.gamereview.service.impl;

import static com.yabba.place.gamereview.domain.define.Team.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.yabba.place.gamereview.domain.define.Team;
import com.yabba.place.gamereview.domain.dto.GameReviewCondition;
import com.yabba.place.gamereview.domain.dto.GameReviewDTO;
import com.yabba.place.gamereview.domain.entity.GameReview;
import com.yabba.place.gamereview.repository.GameReviewRepository;

@ExtendWith(MockitoExtension.class)
class GameReviewServiceImplTest {
	@Mock
	private GameReviewRepository gameReviewRepository;

	@InjectMocks
	private GameReviewServiceImpl gameReviewService;

	private final String ID = "01226N0693HDH";

	private final GameReviewDTO GAME_REVIEW_DTO = GameReviewDTO.builder()
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

	private final GameReview GAME_REVIEW = GameReview.builder()
		.id("01226N0693HDH")
		.userId(47L)
		.gameDate(LocalDate.of(2025, 7, 4))
		.stadiumName(DOOSAN.getStadiumName())
		.homeTeamName(DOOSAN.getName())
		.awayTeamName(KT.getName())
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

	@Test
	@DisplayName("게시글 생성 성공")
	void test_create_success() {
		when(gameReviewRepository.save(any())).thenReturn(GAME_REVIEW);

		GameReviewDTO gameReviewDto = gameReviewService.create(GAME_REVIEW_DTO);

		assertThat(gameReviewDto).isNotNull();
		assertThat(gameReviewDto.homeTeam()).isEqualTo(Team.fromName(GAME_REVIEW.getHomeTeamName()));
		assertThat(gameReviewDto.awayTeam()).isEqualTo(Team.fromName(GAME_REVIEW.getAwayTeamName()));
	}

	@Test
	@DisplayName("게시글 수정 시 기존 게시글이 존재하지 않을 경우 예외 발생")
	void test_update_throwException() {
		when(gameReviewRepository.findByIdAndDeletedAtIsNull(anyString())).thenReturn(Optional.empty());

		assertThatThrownBy(() -> gameReviewService.update(ID, GAME_REVIEW_DTO))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("게시글이 존재하지 않습니다. id: " + ID);
	}

	@Test
	@DisplayName("게시글 수정 성공")
	void test_update_success() {
		when(gameReviewRepository.findByIdAndDeletedAtIsNull(anyString())).thenReturn(Optional.of(GAME_REVIEW));

		GameReviewDTO gameReviewUpdateDto = GameReviewDTO.builder()
			.gameDate(LocalDate.of(2025, 7, 5))
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

		GameReviewDTO gameReviewDto = gameReviewService.update(ID, gameReviewUpdateDto);

		assertThat(gameReviewDto).isNotNull();
		assertThat(gameReviewDto.gameDate()).isEqualTo(LocalDate.of(2025, 7, 5));
	}

	@Test
	@DisplayName("게시글 삭제 시 기존 게시글이 존재하지 않을 경우 예외 발생")
	void test_remove_throwException() {
		when(gameReviewRepository.findByIdAndDeletedAtIsNull(anyString())).thenReturn(Optional.empty());

		assertThatThrownBy(() -> gameReviewService.remove(ID))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("게시글이 존재하지 않습니다. id: " + ID);
	}

	@Test
	@DisplayName("게시글 삭제 성공")
	void test_remove_success() {
		when(gameReviewRepository.findByIdAndDeletedAtIsNull(anyString())).thenReturn(Optional.of(GAME_REVIEW));

		gameReviewService.remove(ID);

		gameReviewRepository.findByIdAndDeletedAtIsNull(ID)
			.ifPresent(gameReview -> assertThat(gameReview.getDeletedAt()).isNotNull());
	}

	@Test
	@DisplayName("게시글 조회 시 기존 게시글이 존재하지 않을 경우 예외 발생")
	void test_get_throwException() {
		when(gameReviewRepository.findByIdAndDeletedAtIsNull(anyString())).thenReturn(Optional.empty());

		assertThatThrownBy(() -> gameReviewService.get(ID))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("게시글이 존재하지 않습니다. id: " + ID);
	}

	@Test
	@DisplayName("게시글 조회 성공")
	void test_get_success() {
		when(gameReviewRepository.findByIdAndDeletedAtIsNull(anyString())).thenReturn(Optional.of(GAME_REVIEW));

		GameReviewDTO gameReviewDto = gameReviewService.get(ID);

		assertThat(gameReviewDto).isNotNull();
		assertThat(gameReviewDto.homeTeam()).isEqualTo(Team.fromName(GAME_REVIEW.getHomeTeamName()));
		assertThat(gameReviewDto.awayTeam()).isEqualTo(Team.fromName(GAME_REVIEW.getAwayTeamName()));
	}

	@Test
	@DisplayName("게시글 검색 시 결과 없음 반환")
	void test_search_returnPageEmpty() {
		when(gameReviewRepository.search(any(), any())).thenReturn(Page.empty());
		GameReviewCondition condition = GameReviewCondition.builder().build();

		Page<GameReviewDTO> gameReviewPage = gameReviewService.search(condition, PageRequest.of(0, 10));

		assertThat(gameReviewPage.getContent()).isEmpty();
		assertThat(gameReviewPage.getTotalElements()).isEqualTo(0);
	}

	@Test
	@DisplayName("게시글 검색 시 결과 반환")
	void test_search_returnPage() {
		when(gameReviewRepository.search(any(), any())).thenReturn(new PageImpl<>(Collections.singletonList(GAME_REVIEW)));

		GameReviewCondition condition = GameReviewCondition.builder()
			.from(LocalDate.of(2025, 7, 1))
			.to(LocalDate.of(2025, 7, 31))
			.build();

		Page<GameReviewDTO> gameReviewPage = gameReviewService.search(condition, PageRequest.of(0, 10));

		assertThat(gameReviewPage.getContent()).isNotEmpty();
		assertThat(gameReviewPage.getTotalElements()).isEqualTo(1);
	}
}
