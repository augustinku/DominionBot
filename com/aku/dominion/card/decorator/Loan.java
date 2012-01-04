package com.aku.dominion.card.decorator;

import java.util.List;

import com.aku.dominion.card.Card;
import com.aku.dominion.card.Type;
import com.aku.dominion.player.Deck;
import com.aku.dominion.player.DeckArea;
import com.aku.dominion.player.DomPlayer;
import com.aku.dominion.util.DomLogger;

public class Loan extends AbstractDecorator {
	
	private static final DomLogger LOG = new DomLogger();

	
	public void play(DomPlayer player) {
		Deck deck = player.getDeck();
		List<Card> list = deck.revealUntil(Type.TREASURE, 1);
		
		if(list.size() > 0) {
			Card card = list.get(0);
			if(Card.COPPER == card ) {
				deck.getRevealed().remove(card);
				player.getGame().getTrash().add(card);
				LOG.info("... trashing a %s ", card);
			} else {
				deck.move(DeckArea.REVEALED, DeckArea.DISCARD, list);
			}
		}
		deck.move(DeckArea.REVEALED, DeckArea.DISCARD);
	}
}
