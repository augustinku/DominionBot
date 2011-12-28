package com.aku.dominion.rule;

import com.aku.dominion.card.Card;

public class BuyRule {
	private Card targetCard;
	private BuyCondition buyCondition;
	private int operand;
	private Card conditionCard;
	
	// ----------- constructors ---------------
	public BuyRule(Card targetCard) {
		this.targetCard = targetCard;
	}
	
	public BuyRule(Card targetCard, BuyCondition buyCondition, 
			Card conditionCard, int operand) {
		this.targetCard = targetCard;
		this.buyCondition = buyCondition;
		this.conditionCard = conditionCard;
		this.operand = operand;
	}

	// ----------- accessors ---------------
	public Card getTargetCard() {
		return targetCard;
	}

	public void setTargetCard(Card targetCard) {
		this.targetCard = targetCard;
	}

	public BuyCondition getBuyCondition() {
		return buyCondition;
	}

	public void setBuyCondition(BuyCondition buyCondition) {
		this.buyCondition = buyCondition;
	}

	public int getOperand() {
		return operand;
	}

	public void setOperand(int operand) {
		this.operand = operand;
	}

	public Card getConditionCard() {
		return conditionCard;
	}

	public void setConditionCard(Card conditionCard) {
		this.conditionCard = conditionCard;
	}
}