package com.yabba.place.stadium.service.impl;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.yabba.place.stadium.domain.dto.StadiumDTO;
import com.yabba.place.stadium.domain.entity.Stadium;
import com.yabba.place.stadium.repository.StadiumRepository;

@ExtendWith(MockitoExtension.class)
class StadiumServiceImplTest {
	@Mock
	private StadiumRepository stadiumRepository;

	@InjectMocks
	private StadiumServiceImpl stadiumService;

	private final StadiumDTO STADIUM_DTO = StadiumDTO.builder()
		.teamId(1L)
		.name("잠실야구장")
		.address("서울특별시 송파구 올림픽로 19-2")
		.build();

	private final Stadium STADIUM = Stadium.builder()
		.id(1L)
		.teamId(1L)
		.name("잠실야구장")
		.address("서울특별시 송파구 올림픽로 19-2")
		.build();

	@Test
	@DisplayName("경기장 생성 성공")
	void test_create_success() {
		when(stadiumRepository.save(any())).thenReturn(STADIUM);

		StadiumDTO stadiumDto = stadiumService.create(STADIUM_DTO);

		assertThat(stadiumDto).isNotNull();
		assertThat(stadiumDto.teamId()).isEqualTo(STADIUM_DTO.teamId());
		assertThat(stadiumDto.name()).isEqualTo(STADIUM_DTO.name());
		assertThat(stadiumDto.address()).isEqualTo(STADIUM_DTO.address());
	}

	@Test
	@DisplayName("경기장 수정시 기존 경기장이 존재하지 않을 경우 예외 발생")
	void test_update_throwException() {
		when(stadiumRepository.findByIdAndDeletedAtIsNull(anyLong())).thenReturn(Optional.empty());

		assertThatThrownBy(() -> stadiumService.update(1L, STADIUM_DTO))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("경기장이 존재하지 않습니다. id: 1");
	}

	@Test
	@DisplayName("경기장 수정 성공")
	void test_update_success() {
		when(stadiumRepository.findByIdAndDeletedAtIsNull(anyLong())).thenReturn(Optional.of(STADIUM));

		StadiumDTO stadiumUpdateDto = StadiumDTO.builder()
			.teamId(1L)
			.name("잠실종합야구장")
			.address("서울특별시 송파구 올림픽로 19-2")
			.build();

		StadiumDTO stadiumDto = stadiumService.update(1L, stadiumUpdateDto);

		assertThat(stadiumDto).isNotNull();
		assertThat(stadiumDto.name()).isEqualTo("잠실종합야구장");
	}

	@Test
	@DisplayName("경기장 삭제시 기존 경기장이 존재하지 않을 경우 예외 발생")
	void test_remove_throwException() {
		when(stadiumRepository.findByIdAndDeletedAtIsNull(anyLong())).thenReturn(Optional.empty());

		assertThatThrownBy(() -> stadiumService.remove(1L))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("경기장이 존재하지 않습니다. id: 1");
	}

	@Test
	@DisplayName("경기장 삭제 성공")
	void test_remove_success() {
		when(stadiumRepository.findByIdAndDeletedAtIsNull(anyLong())).thenReturn(Optional.of(STADIUM));

		stadiumService.remove(1L);

		stadiumRepository.findByIdAndDeletedAtIsNull(1L)
			.ifPresent(stadium -> assertThat(stadium.getDeletedAt()).isNotNull());
	}

	@Test
	@DisplayName("경기장 조회시 기존 경기장이 존재하지 않을 경우 예외 발생")
	void test_get_throwException() {
		when(stadiumRepository.findByIdAndDeletedAtIsNull(anyLong())).thenReturn(Optional.empty());

		assertThatThrownBy(() -> stadiumService.get(1L))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("경기장이 존재하지 않습니다. id: 1");
	}

	@Test
	@DisplayName("경기장 조회 성공")
	void test_get_success() {
		when(stadiumRepository.findByIdAndDeletedAtIsNull(anyLong())).thenReturn(Optional.of(STADIUM));

		StadiumDTO stadiumDto = stadiumService.get(1L);

		assertThat(stadiumDto).isNotNull();
		assertThat(stadiumDto.name()).isEqualTo(STADIUM.getName());
		assertThat(stadiumDto.address()).isEqualTo(STADIUM.getAddress());
	}

	@Test
	@DisplayName("경기장 목록 조회시 데이터가 없을 경우 빈 리스트 반환")
	void test_list_empty() {
		when(stadiumRepository.findAllByDeletedAtIsNull()).thenReturn(List.of());

		List<StadiumDTO> stadiums = stadiumService.list();

		assertThat(stadiums).isEmpty();
	}

	@Test
	@DisplayName("경기장 목록 조회 성공")
	void test_list_success() {
		when(stadiumRepository.findAllByDeletedAtIsNull()).thenReturn(List.of(STADIUM));

		List<StadiumDTO> stadiums = stadiumService.list();

		assertThat(stadiums).isNotEmpty();
		assertThat(stadiums).hasSize(1);
	}
}
