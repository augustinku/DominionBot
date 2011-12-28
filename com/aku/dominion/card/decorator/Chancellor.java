package com.aku.dominion.card.decorator;

import com.aku.dominion.player.DomPlayer;

public class Chancellor extends AbstractDecorator {
	
	public void play(DomPlayer player) {
		player.getDeck().putDrawPileIntoDiscard();
	}
}
