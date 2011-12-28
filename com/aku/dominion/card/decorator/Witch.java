package com.aku.dominion.card.decorator;

import com.aku.dominion.card.Card;
import com.aku.dominion.player.DomPlayer;

public class Witch extends AbstractDecorator {
		
	public void playOnOpponent(DomPlayer opponent) {
		opponent.gainCard(Card.CURSE);	
	}
}
