package com.aku.dominion.rule;

import com.aku.dominion.Supply;
import com.aku.dominion.card.Card;
import com.aku.dominion.player.DomPlayer;

public class DeckMaxCond implements BuyCondition {
	public boolean execute(DomPlayer player, Supply supply, Card card, int num) {
		boolean condition = player.getDeck().getNumInDeck(card) < num;
		
		return condition;
	}
}
