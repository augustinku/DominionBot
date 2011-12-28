package com.aku.dominion.strategy;

import static com.aku.dominion.card.Card.DUCHY;
import static com.aku.dominion.card.Card.ESTATE;
import static com.aku.dominion.card.Card.GOLD;
import static com.aku.dominion.card.Card.MONTEBANK;
import static com.aku.dominion.card.Card.PROVINCE;
import static com.aku.dominion.card.Card.SILVER;

import com.aku.dominion.rule.BuyCondition;
import com.aku.dominion.rule.BuyRule;
import com.aku.dominion.rule.DeckMaxCond;
import com.aku.dominion.rule.SupplyLessThanCond;

public class MontebankStrategy extends AbstractStrategy {

	@Override
	public void addBuyRules() {
		BuyCondition supplyCond = new SupplyLessThanCond();
		BuyCondition deckMax = new DeckMaxCond();
		
		rules.add(new BuyRule(PROVINCE));
		rules.add(new BuyRule(DUCHY, supplyCond, PROVINCE, 5));
		rules.add(new BuyRule(ESTATE, supplyCond, PROVINCE, 2));
		rules.add(new BuyRule(MONTEBANK, deckMax, MONTEBANK, 1 ));
		rules.add(new BuyRule(GOLD));
		rules.add(new BuyRule(MONTEBANK, deckMax, MONTEBANK, 3 ));
		rules.add(new BuyRule(SILVER));
	}
}
