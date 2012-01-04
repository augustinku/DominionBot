package com.aku.dominion.card.decorator;

import com.aku.dominion.player.DeckArea;
import com.aku.dominion.player.DomPlayer;

public class Chancellor extends AbstractDecorator {
	
	public void play(DomPlayer player) {
		player.getDeck().move(DeckArea.DRAW_PILE, DeckArea.DISCARD);
	}
}
