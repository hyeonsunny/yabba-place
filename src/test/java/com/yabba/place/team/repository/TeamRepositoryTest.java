package com.yabba.place.team.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.yabba.place.team.domain.dto.TeamDTO;
import com.yabba.place.team.domain.entity.Team;
import com.yabba.place.team.domain.entry.TeamPrefix;

@SpringBootTest
@Transactional
class TeamRepositoryTest {
	@Autowired
	private TeamRepository teamRepository;

	private Team TEAM;
	@BeforeEach
	void setup() {
		TeamDTO teamDto = TeamDTO.builder()
			.stadiumId(1L)
			.name("두산베어스")
			.prefix(TeamPrefix.DOOSAN)
			.build();
		TEAM = teamRepository.save(teamDto.toTeam());
	}

	@Test
	@DisplayName("팀 ID로 조회 - 삭제되지 않은 경우")
	void test_findByIdAndDeletedAtIsNull_success() {
		Optional<Team> teamOptional = teamRepository.findByIdAndDeletedAtIsNull(TEAM.getId());

		assertThat(teamOptional).isPresent();
	}

	@Test
	@DisplayName("팀 목록 조회 - 삭제되지 않은 경우")
	void test_findAllByDeletedAtIsNull_success() {
		List<Team> teams = teamRepository.findAllByDeletedAtIsNull();

		assertThat(teams).hasSize(1);
	}
}
