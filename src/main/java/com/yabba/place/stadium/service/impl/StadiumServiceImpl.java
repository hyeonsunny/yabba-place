package com.yabba.place.stadium.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yabba.place.stadium.domain.dto.StadiumDTO;
import com.yabba.place.stadium.domain.entity.Stadium;
import com.yabba.place.stadium.repository.StadiumRepository;
import com.yabba.place.stadium.service.StadiumService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class StadiumServiceImpl implements StadiumService {
	private final StadiumRepository stadiumRepository;

	@Override
	public StadiumDTO create(StadiumDTO dto) {
		return StadiumDTO.fromStadium(stadiumRepository.save(dto.toStadium()));
	}

	@Override
	public StadiumDTO update(long id, StadiumDTO dto) {
		Stadium stadium = stadiumRepository.findByIdAndDeletedAtIsNull(id)
			.orElseThrow(() -> new IllegalArgumentException("경기장이 존재하지 않습니다. id: " + id));

		stadium.update(dto);

		return StadiumDTO.fromStadium(stadium);
	}

	@Override
	public void remove(long id) {
		Stadium stadium = stadiumRepository.findByIdAndDeletedAtIsNull(id)
			.orElseThrow(() -> new IllegalArgumentException("경기장이 존재하지 않습니다. id: " + id));

		stadium.softDelete();
	}

	@Override
	public StadiumDTO get(long id) {
		Stadium stadium = stadiumRepository.findByIdAndDeletedAtIsNull(id)
			.orElseThrow(() -> new IllegalArgumentException("경기장이 존재하지 않습니다. id: " + id));

		return StadiumDTO.fromStadium(stadium);
	}

	@Override
	public List<StadiumDTO> list() {
		return stadiumRepository.findAllByDeletedAtIsNull()
			.stream()
			.map(StadiumDTO::fromStadium)
			.toList();
	}
}
