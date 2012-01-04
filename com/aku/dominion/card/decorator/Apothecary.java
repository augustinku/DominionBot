package com.aku.dominion.card.decorator;

import java.util.List;

import com.aku.dominion.card.Card;
import com.aku.dominion.player.Deck;
import com.aku.dominion.player.DeckArea;
import com.aku.dominion.player.DomPlayer;

public class Apothecary extends AbstractDecorator {
	
	public void play(DomPlayer player) {
		Deck deck = player.getDeck();
		List<Card> list = deck.revealUntil(4);
		for(Card card: list) {
			if(Card.COPPER == card || Card.POTION == card) {
				deck.move(DeckArea.REVEALED, DeckArea.HAND, card);
			}
				
		}
		deck.move(DeckArea.REVEALED, DeckArea.DRAW_PILE);
	}
}
