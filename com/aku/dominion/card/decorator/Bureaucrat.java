package com.aku.dominion.card.decorator;

import com.aku.dominion.card.Card;
import com.aku.dominion.card.Type;
import com.aku.dominion.player.DomPlayer;

public class Bureaucrat extends AbstractDecorator {
	
	public void play(DomPlayer player) {
		player.gainCardOnDeck(Card.SILVER);
	}
	
	public void playOnOpponent(DomPlayer opponent) {
		if(opponent.getDeck().getNumInHand(Type.VICTORY) == 0) {
			opponent.getDeck().revealHand();
		} else {
			opponent.putTypeOnDrawPile(Type.VICTORY);
			// put victory card on drawPile
		}
	}
}
