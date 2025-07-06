package com.yabba.place.stadium.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.yabba.place.stadium.domain.dto.StadiumDTO;
import com.yabba.place.stadium.domain.entity.Stadium;

@SpringBootTest
@Transactional
class StadiumRepositoryTest {
	@Autowired
	private StadiumRepository stadiumRepository;

	private Stadium STADIUM;
	@BeforeEach
	void setUp() {
		StadiumDTO stadiumDto = StadiumDTO.builder()
			.teamId(1L)
			.name("잠실야구장")
			.address("서울특별시 송파구 올림픽로 19-2")
			.build();

		STADIUM = stadiumRepository.save(stadiumDto.toStadium());
	}

	@Test
	@DisplayName("경기장 ID로 조회 - 삭제되지 않은 경우")
	void test_findByIdAndDeletedAtIsNull_success() {
		Optional<Stadium> stadiumOptional = stadiumRepository.findByIdAndDeletedAtIsNull(STADIUM.getId());

		assertThat(stadiumOptional).isPresent();
	}

	@Test
	@DisplayName("경기장 목록 조회 - 삭제되지 않은 경우")
	void test_findAllByDeletedAtIsNull_success() {
		List<Stadium> stadiums = stadiumRepository.findAllByDeletedAtIsNull();

		assertThat(stadiums).hasSize(1);
	}
}
