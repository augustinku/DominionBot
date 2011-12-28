package com.aku.dominion.strategy;

import static com.aku.dominion.card.Card.ALCHEMIST;
import static com.aku.dominion.card.Card.POTION;
import static com.aku.dominion.card.Card.WOODCUTTER;

import com.aku.dominion.rule.BuyCondition;
import com.aku.dominion.rule.BuyRule;
import com.aku.dominion.rule.DeckMaxCond;

// alchemist needs some type of terminal
public class BigMoneyAlchemist extends BigMoneyUltimate {
	
	@Override
	public void addOtherBuyRules() {
		BuyCondition deckMax = new DeckMaxCond();
		rules.add(new BuyRule(ALCHEMIST));
		rules.add(new BuyRule(POTION, deckMax, POTION, 1 ));
		rules.add(new BuyRule(WOODCUTTER, deckMax, WOODCUTTER, 1 ));
	}
}