package com.aku.dominion.card.decorator;

import static com.aku.dominion.card.Card.COPPER;
import static com.aku.dominion.card.Card.GOLD;
import static com.aku.dominion.card.Card.PLATINUM;
import static com.aku.dominion.card.Type.ACTION;
import static com.aku.dominion.card.Type.VICTORY;
import static com.aku.dominion.player.DeckArea.DISCARD;
import static com.aku.dominion.player.DeckArea.DRAW_PILE;
import static com.aku.dominion.player.DeckArea.HAND;
import static com.aku.dominion.player.DeckArea.REVEALED;

import java.util.List;

import com.aku.dominion.card.Card;
import com.aku.dominion.player.Deck;
import com.aku.dominion.player.DeckArea;
import com.aku.dominion.player.DomPlayer;

public class ScryingPool extends AbstractDecorator {
	
	@Override
	public void play(DomPlayer player) {
		Deck deck = player.getDeck();
		Card card = deck.revealTopCard();
		if(card != null) {
			// TODO this decision is based on lots of factors
			DeckArea dest = DeckArea.DISCARD;
			if(card.isType(ACTION) || GOLD == card || PLATINUM == card ) {
				dest = DeckArea.DRAW_PILE;
			}
			deck.move(REVEALED, dest, card);
		}
		
		// add actions to hand
		List<Card> list = deck.revealUntilNot(ACTION);
		deck.move(REVEALED, HAND, list);
		deck.move(REVEALED, DISCARD);
	}
	
	@Override
	public void playOnOpponent(DomPlayer opponent) {
		Deck deck = opponent.getDeck();
		Card card = deck.revealTopCard();
		// TODO this decision is based on lots of factors
		DeckArea dest = DISCARD;
		if(card.isType(VICTORY) || COPPER == card ) {
			dest = DRAW_PILE;
		} 
		deck.move(REVEALED, dest, card);
	}
}
