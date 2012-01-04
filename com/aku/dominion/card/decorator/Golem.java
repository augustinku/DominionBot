package com.aku.dominion.card.decorator;

import java.util.List;

import com.aku.dominion.card.Card;
import com.aku.dominion.card.Type;
import com.aku.dominion.player.Deck;
import com.aku.dominion.player.DeckArea;
import com.aku.dominion.player.DomPlayer;

public class Golem extends AbstractDecorator {
	
	public void play(DomPlayer player) {
		Deck deck = player.getDeck();
		List<Card> list = deck.revealUntil(Type.ACTION, 2, Card.GOLEM);
		deck.move(DeckArea.REVEALED, DeckArea.DISCARD);
		
		// TODO: should try to play them in some type of order
		for(Card card: list) {
			card.play(player);
		}

	}
}
