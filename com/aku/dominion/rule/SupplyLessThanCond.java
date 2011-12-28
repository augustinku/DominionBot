package com.aku.dominion.rule;

import com.aku.dominion.Supply;
import com.aku.dominion.card.Card;
import com.aku.dominion.player.DomPlayer;

public class SupplyLessThanCond implements BuyCondition {
	public boolean execute(DomPlayer player, Supply supply, Card card, int num) {
		boolean condition = supply.numLeft(card) <= num;
		
		return condition;
	}
}
