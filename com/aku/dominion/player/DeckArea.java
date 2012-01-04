package com.aku.dominion.player;


public enum DeckArea {
	HAND("hand") ,
	PLAYED("played"),
	DISCARD("discard"),
	DRAW_PILE("draw pile"),
	REVEALED("revealed");
	
	private String name;
	
	private DeckArea(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
