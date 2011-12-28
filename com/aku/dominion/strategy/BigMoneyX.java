package com.aku.dominion.strategy;

import com.aku.dominion.card.Card;
import com.aku.dominion.rule.BuyCondition;
import com.aku.dominion.rule.BuyRule;
import com.aku.dominion.rule.DeckMaxCond;

public class BigMoneyX extends BigMoneyUltimate {
	
	private Card card;
	private int max = 1;
	
	public BigMoneyX(Card card) {
		super();
		this.card = card;
	}
	
	public BigMoneyX(Card card, int max) {
		super();
		this.card = card;
		this.max = max;
	}
	
	@Override
	public void addOtherBuyRules() {
		BuyCondition deckMax = new DeckMaxCond();
		rules.add(new BuyRule(card, deckMax, card, max ));
	}
	
	public String getName() {
		return super.getName() + "+" + card;
	}
}