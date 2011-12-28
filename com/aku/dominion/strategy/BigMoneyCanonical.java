package com.aku.dominion.strategy;

import static com.aku.dominion.card.Card.GOLD;
import static com.aku.dominion.card.Card.PROVINCE;
import static com.aku.dominion.card.Card.SILVER;

import com.aku.dominion.rule.BuyRule;

/**
 * @author Augustin
 * Big Money Canonical
 */
public class BigMoneyCanonical extends AbstractStrategy {
	
	@Override
	public void addBuyRules() {	
		rules.add(new BuyRule(PROVINCE));
		rules.add(new BuyRule(GOLD));
		rules.add(new BuyRule(SILVER));
	}	
}
