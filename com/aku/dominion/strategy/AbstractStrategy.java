package com.aku.dominion.strategy;

import static com.aku.dominion.card.Card.COLONY;
import static com.aku.dominion.card.Card.PLATINUM;

import java.util.ArrayList;
import java.util.List;

import com.aku.dominion.rule.BuyRule;

public abstract class AbstractStrategy implements Strategy {

	protected List<BuyRule> rules;
		
	public abstract void addBuyRules();
	
	@Override
	public List<BuyRule> getBuyRules() {
		if(rules == null) {
			createBuyRules();
		}

		return rules;
	}

	private void createBuyRules() {
		rules = new ArrayList<>();

		rules.add(new BuyRule(COLONY));
		rules.add(new BuyRule(PLATINUM));
		addBuyRules();
	}
	
	public String getName() {
		return this.getClass().getSimpleName();
	}
}
