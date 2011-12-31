package com.aku.dominion.card.decorator;

import com.aku.dominion.card.Card;
import com.aku.dominion.player.Deck;

public class Duke extends AbstractDecorator {
	
	public int numVictoryPoints(Deck deck) {		
		return deck.getNumInDeck(Card.DUKE);
	}
}
