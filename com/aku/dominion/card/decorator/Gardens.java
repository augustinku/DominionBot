package com.aku.dominion.card.decorator;

import com.aku.dominion.player.Deck;

public class Gardens extends AbstractDecorator {
	
	public int numVictoryPoints(Deck deck) {		
		return deck.getNumInDeck() / 10;
	}
}
