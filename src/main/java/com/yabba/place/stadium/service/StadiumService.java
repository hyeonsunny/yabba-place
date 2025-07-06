package com.yabba.place.stadium.service;

import java.util.List;

import com.yabba.place.stadium.domain.dto.StadiumDTO;

public interface StadiumService {
	StadiumDTO create(StadiumDTO dto);

	StadiumDTO update(long id, StadiumDTO dto);

	void remove(long id);

	StadiumDTO get(long id);

	List<StadiumDTO> list();
}
