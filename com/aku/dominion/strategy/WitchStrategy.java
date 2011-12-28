package com.aku.dominion.strategy;

import static com.aku.dominion.card.Card.*;

import com.aku.dominion.rule.BuyCondition;
import com.aku.dominion.rule.BuyRule;
import com.aku.dominion.rule.DeckMaxCond;
import com.aku.dominion.rule.SupplyLessThanCond;

public class WitchStrategy extends AbstractStrategy {

	@Override
	public void addBuyRules() {
		BuyCondition supplyCond = new SupplyLessThanCond();
		BuyCondition deckMax = new DeckMaxCond();
		
		rules.add(new BuyRule(PROVINCE));
		rules.add(new BuyRule(DUCHY, supplyCond, PROVINCE, 5));
		rules.add(new BuyRule(ESTATE, supplyCond, PROVINCE, 2));
		rules.add(new BuyRule(WITCH, deckMax, WITCH, 1 ));
		rules.add(new BuyRule(GOLD));
		rules.add(new BuyRule(WITCH, deckMax, WITCH, 3 ));
		rules.add(new BuyRule(SILVER));
	}
}