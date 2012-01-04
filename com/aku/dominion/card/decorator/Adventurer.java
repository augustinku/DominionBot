package com.aku.dominion.card.decorator;

import java.util.List;

import com.aku.dominion.card.Card;
import com.aku.dominion.card.Type;
import com.aku.dominion.player.Deck;
import com.aku.dominion.player.DeckArea;
import com.aku.dominion.player.DomPlayer;

public class Adventurer extends AbstractDecorator {
	
	public void play(DomPlayer player) {
		Deck deck = player.getDeck();
		List<Card> list = deck.revealUntil(Type.TREASURE, 2);

		deck.move(DeckArea.REVEALED, DeckArea.HAND, list);
		deck.move(DeckArea.REVEALED, DeckArea.DISCARD);
	}
}
