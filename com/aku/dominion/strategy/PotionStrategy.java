package com.aku.dominion.strategy;

import static com.aku.dominion.card.Card.ALCHEMIST;
import static com.aku.dominion.card.Card.APOTHECARY;
import static com.aku.dominion.card.Card.DUCHY;
import static com.aku.dominion.card.Card.ESTATE;
import static com.aku.dominion.card.Card.GOLD;
import static com.aku.dominion.card.Card.GOLEM;
import static com.aku.dominion.card.Card.POTION;
import static com.aku.dominion.card.Card.PROVINCE;
import static com.aku.dominion.card.Card.SCRYING_POOL;
import static com.aku.dominion.card.Card.SILVER;
import static com.aku.dominion.card.Card.SMITHY;

import com.aku.dominion.rule.BuyCondition;
import com.aku.dominion.rule.BuyRule;
import com.aku.dominion.rule.DeckMaxCond;
import com.aku.dominion.rule.SupplyLessThanCond;

public class PotionStrategy extends AbstractStrategy {

	@Override
	public void addBuyRules() {
		BuyCondition supplyCond = new SupplyLessThanCond();
		BuyCondition deckMax = new DeckMaxCond();
		
		rules.add(new BuyRule(PROVINCE));
		rules.add(new BuyRule(DUCHY, supplyCond, PROVINCE, 5));
		rules.add(new BuyRule(ESTATE, supplyCond, PROVINCE, 2));
		rules.add(new BuyRule(GOLD));
		rules.add(new BuyRule(GOLEM));
		rules.add(new BuyRule(ALCHEMIST));
		rules.add(new BuyRule(SCRYING_POOL, deckMax, SCRYING_POOL, 1 ));
		rules.add(new BuyRule(APOTHECARY, deckMax, APOTHECARY, 1 ));
		rules.add(new BuyRule(POTION, deckMax, POTION, 2 ));
		rules.add(new BuyRule(SMITHY, deckMax, SMITHY, 1 ));

		rules.add(new BuyRule(SILVER));
	}
}
