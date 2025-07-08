package com.yabba.place.team.service.impl;

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

import com.yabba.place.team.domain.dto.TeamDTO;
import com.yabba.place.team.domain.entity.Team;
import com.yabba.place.team.domain.entry.TeamPrefix;
import com.yabba.place.team.repository.TeamRepository;

@ExtendWith(MockitoExtension.class)
class TeamServiceImplTest {
	@Mock
	private TeamRepository teamRepository;

	@InjectMocks
	private TeamServiceImpl teamService;

	private final long ID = 1L;

	private final TeamDTO TEAM_DTO = TeamDTO.builder()
		.stadiumId(1L)
		.name("두산베어스")
		.prefix(TeamPrefix.DOOSAN)
		.build();

	private final Team TEAM = Team.builder()
		.id(1L)
		.stadiumId(1L)
		.name("두산베어스")
		.prefix(TeamPrefix.DOOSAN)
		.build();

	@Test
	@DisplayName("팀 생성 성공")
	void test_create_success() {
		when(teamRepository.save(any())).thenReturn(TEAM);

		TeamDTO teamDto = teamService.create(TEAM_DTO);

		assertThat(teamDto).isNotNull();
		assertThat(teamDto.stadiumId()).isEqualTo(TEAM_DTO.stadiumId());
		assertThat(teamDto.name()).isEqualTo(TEAM_DTO.name());
		assertThat(teamDto.prefix()).isEqualTo(TEAM_DTO.prefix());
	}

	@Test
	@DisplayName("팀 수정시 기존 팀이 존재하지 않을 경우 예외 발생")
	void test_update_throwException() {
		when(teamRepository.findByIdAndDeletedAtIsNull(anyLong())).thenReturn(Optional.empty());

		assertThatThrownBy(() -> teamService.update(ID, TEAM_DTO))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("팀이 존재하지 않습니다. id: " + ID);
	}

	@Test
	@DisplayName("팀 수정 성공")
	void test_update_success() {
		when(teamRepository.findByIdAndDeletedAtIsNull(anyLong())).thenReturn(Optional.of(TEAM));

		TeamDTO teamUpdateDto = TeamDTO.builder()
			.stadiumId(1L)
			.name("최강두산베어스")
			.prefix(TeamPrefix.DOOSAN)
			.build();

		TeamDTO teamDto = teamService.update(ID, teamUpdateDto);

		assertThat(teamDto).isNotNull();
		assertThat(teamDto.name()).isEqualTo("최강두산베어스");
	}

	@Test
	@DisplayName("팀 삭제시 기존 팀이 존재하지 않을 경우 예외 발생")
	void test_remove_throwException() {
		when(teamRepository.findByIdAndDeletedAtIsNull(anyLong())).thenReturn(Optional.empty());

		assertThatThrownBy(() -> teamService.remove(ID))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("팀이 존재하지 않습니다. id: " + ID);
	}

	@Test
	@DisplayName("팀 삭제 성공")
	void test_remove_success() {
		when(teamRepository.findByIdAndDeletedAtIsNull(anyLong())).thenReturn(Optional.of(TEAM));

		teamService.remove(ID);

		teamRepository.findByIdAndDeletedAtIsNull(ID)
			.ifPresent(team -> assertThat(team.getDeletedAt()).isNotNull());
	}

	@Test
	@DisplayName("팀 조회시 기존 팀이 존재하지 않을 경우 예외 발생")
	void test_get_throwException() {
		when(teamRepository.findByIdAndDeletedAtIsNull(anyLong())).thenReturn(Optional.empty());

		assertThatThrownBy(() -> teamService.get(ID))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("팀이 존재하지 않습니다. id: " + ID);
	}

	@Test
	@DisplayName("팀 조회 성공")
	void test_get_success() {
		when(teamRepository.findByIdAndDeletedAtIsNull(anyLong())).thenReturn(Optional.of(TEAM));

		TeamDTO teamDto = teamService.get(ID);

		assertThat(teamDto).isNotNull();
		assertThat(teamDto.stadiumId()).isEqualTo(TEAM.getStadiumId());
		assertThat(teamDto.name()).isEqualTo(TEAM.getName());
		assertThat(teamDto.prefix()).isEqualTo(TEAM.getPrefix());
	}

	@Test
	@DisplayName("팀 목록 조회시 데이터가 없을 경우 빈 리스트 반환")
	void test_list_empty() {
		when(teamRepository.findAllByDeletedAtIsNull()).thenReturn(List.of());

		List<TeamDTO> teams = teamService.list();

		assertThat(teams).isEmpty();
	}

	@Test
	@DisplayName("팀 목록 조회 성공")
	void test_list_success() {
		when(teamRepository.findAllByDeletedAtIsNull()).thenReturn(List.of(TEAM));

		List<TeamDTO> teams = teamService.list();

		assertThat(teams).isNotEmpty();
		assertThat(teams).hasSize(1);
	}
}
