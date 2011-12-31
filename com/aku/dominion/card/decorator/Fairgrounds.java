package com.aku.dominion.card.decorator;

import com.aku.dominion.player.Deck;

public class Fairgrounds extends AbstractDecorator {
	
	public int numVictoryPoints(Deck deck) {		
		return deck.getNumUniqueInDeck() / 5;
	}
}
