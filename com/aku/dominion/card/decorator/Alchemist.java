package com.aku.dominion.card.decorator;

import com.aku.dominion.card.Card;
import com.aku.dominion.player.Deck;
import com.aku.dominion.player.DomPlayer;

public class Alchemist extends AbstractDecorator {
		
	// if a potion has been played, return alchemist to the top of the deck
	public void cleanupPhase(DomPlayer player) {

		Deck deck = player.getDeck();
		if( deck.getPlayed().contains(Card.POTION)) {
			deck.returnToTopOfDeck(Card.ALCHEMIST);
		}
	}
}
