package com.aku.dominion.card.decorator;

import com.aku.dominion.player.DomPlayer;

public class CouncilRoom extends AbstractDecorator {
		
	public void playOnOpponent(DomPlayer opponent) {
		opponent.drawCards(1);
	}
}
