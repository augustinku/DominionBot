package com.aku.dominion.card.decorator;

import com.aku.dominion.card.Type;
import com.aku.dominion.player.Deck;

public class Vineyard extends AbstractDecorator {
	
	public int numVictoryPoints(Deck deck) {		
		return deck.getNumInDeck(Type.ACTION) / 3;
	}
}
