package com.aku.dominion.card.decorator;

import com.aku.dominion.card.Card;
import com.aku.dominion.player.DomPlayer;

public class Montebank extends AbstractDecorator {
	
	// TODO reveal a curse to stop the attack
	public void playOnOpponent(DomPlayer opponent) {
		opponent.gainCard(Card.CURSE);
		opponent.gainCard(Card.COPPER);
	}
}
