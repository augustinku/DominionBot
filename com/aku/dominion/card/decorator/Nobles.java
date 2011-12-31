package com.aku.dominion.card.decorator;

import com.aku.dominion.card.Type;
import com.aku.dominion.player.DomPlayer;

public class Nobles extends AbstractDecorator {
	
	public void play(DomPlayer player) {
		if( player.getNumActions() == 0 && player.getDeck().getNumInHand(Type.ACTION) > 0) {
			player.addAction(2);
		} else {
			player.drawCards(3);
		}
	}
}
