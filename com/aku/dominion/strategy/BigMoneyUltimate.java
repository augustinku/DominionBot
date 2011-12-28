package com.aku.dominion.strategy;

import static com.aku.dominion.card.Card.DUCHY;
import static com.aku.dominion.card.Card.ESTATE;
import static com.aku.dominion.card.Card.GOLD;
import static com.aku.dominion.card.Card.PROVINCE;
import static com.aku.dominion.card.Card.SILVER;

import com.aku.dominion.rule.BuyCondition;
import com.aku.dominion.rule.BuyRule;
import com.aku.dominion.rule.SupplyLessThanCond;

public class BigMoneyUltimate extends AbstractStrategy {

	private final static int NUM_PROVNCES_TO_BUY_DUTCHIES = 5;
	private final static int NUM_PROVNCES_TO_BUY_ESTATES = 2;
	
	private int numProvincesToBuyDutchies = 5;
	private int numProvincesToBuyEstates = 2;

	public BigMoneyUltimate() {
		this(NUM_PROVNCES_TO_BUY_DUTCHIES, NUM_PROVNCES_TO_BUY_ESTATES);
	}
	
	public BigMoneyUltimate(int numProvincesToBuyDutchies, int numProvincesToBuyEstates) {
		this.numProvincesToBuyDutchies = numProvincesToBuyDutchies;
		this.numProvincesToBuyEstates = numProvincesToBuyEstates;
	}
	
	@Override
	public void addBuyRules() {
		BuyCondition supplyCond = new SupplyLessThanCond();
		
		rules.add(new BuyRule(PROVINCE));
		rules.add(new BuyRule(DUCHY, supplyCond, PROVINCE, numProvincesToBuyDutchies));
		rules.add(new BuyRule(ESTATE, supplyCond, PROVINCE, numProvincesToBuyEstates));
		rules.add(new BuyRule(GOLD));
		
		addOtherBuyRules();
		
		rules.add(new BuyRule(SILVER));
	}
	
	public void addOtherBuyRules() {
	}
	
}
