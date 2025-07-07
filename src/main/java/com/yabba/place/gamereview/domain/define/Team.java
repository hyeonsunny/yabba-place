package com.yabba.place.gamereview.domain.define;

import lombok.Getter;

@Getter
public enum Team {
	KIA("기아타이거즈", "광주기아챔피언스필드"),
	SAMSUNG("삼성라이온즈", "대구삼성라이온즈파크"),
	LG("LG트윈스", "잠실야구장"),
	DOOSAN("두산베어스", "잠실야구장"),
	KT("KT위즈", "수원KT위즈파크"),
	SSG("SSG랜더스", "인천SSG랜더스필드"),
	LOTTE("롯데자이언츠", "부산사직야구장"),
	HANWHA("한화이글스", "대전한화생명이글스파크"),
	NC("NC다이노스", "창원NC파크"),
	KIWOOM("키움히어로즈", "고척스카이돔")
	;

	private final String name;
	private final String stadiumName;

	Team(String name, String stadiumName) {
		this.name = name;
		this.stadiumName = stadiumName;
	}

	public static Team fromName(String name) {
		for (Team team : Team.values()) {
			if (team.getName().equals(name)) {
				return team;
			}
		}
		throw new IllegalArgumentException("해당 이름의 구단이 존재하지 않습니다. name: " + name);
	}
}
