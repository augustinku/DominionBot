package com.aku.dominion.strategy;

import java.util.List;

import com.aku.dominion.rule.BuyRule;

public interface Strategy {

	public List<BuyRule> getBuyRules();
	public String getName();
}
