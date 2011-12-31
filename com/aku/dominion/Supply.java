package com.aku.dominion;

import java.util.ArrayList;
import java.util.List;

import com.aku.dominion.card.Card;
import com.aku.dominion.card.Type;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

public class Supply {

	private static final int NUM_COPPER = 60;
	private static final int NUM_SILVER = 40;
	private static final int NUM_GOLD = 30;
	private static final int NUM_POTIONS = 16;
	
	private static final int NUM_VICTORY_CARDS_BASE = 8;
	private static final int NUM_VICTORY_CARDS_3P = 12;
	private static final int NUM_VICTORY_CARDS_4P = 12;

	private int NUM_NON_VICTORY_CARDS = 10;

	private Multiset<Card> cards;

	public Supply(int numPlayers, boolean isColonyGame) {
		cards = HashMultiset.create();
		int numVictoryCards = numVictoryCards(numPlayers);

		// for now add all of the kingdom cards
		for (Card card : Card.values()) {
			if (card.isType(Type.KINGDOM)) {
				cards.add(card, NUM_NON_VICTORY_CARDS);
			}
		}
		
		// base game
		cards.add(Card.COPPER, NUM_COPPER);
		cards.add(Card.SILVER, NUM_SILVER);
		cards.add(Card.GOLD, NUM_GOLD);
		cards.add(Card.ESTATE, numVictoryCards);
		cards.add(Card.DUCHY, numVictoryCards);
		cards.add(Card.PROVINCE, numVictoryCards);
		
		cards.add(Card.CURSE, NUM_NON_VICTORY_CARDS); // TODO fix
		cards.add(Card.POTION, NUM_POTIONS); // should only be used if a kingdom card uses potion
		
		if (isColonyGame) {
			cards.add(Card.COLONY, numVictoryCards);
			cards.add(Card.PLATINUM, NUM_NON_VICTORY_CARDS);
		}
	}	
		

	private int numVictoryCards(int numPlayers) {
		int numVictoryCards = 0;
		switch(numPlayers) {
		   case 0:
		   case 1:
		   case 2:
			   numVictoryCards = NUM_VICTORY_CARDS_BASE;
			   break;

		   case 3:
			   numVictoryCards = NUM_VICTORY_CARDS_3P;
			   break;

		   case 4:
			   numVictoryCards = NUM_VICTORY_CARDS_4P;
			   break;

		   	default:
		   		throw new IllegalArgumentException("5+ players not supported");
			   
		}
		
		return numVictoryCards;
	}

	public int numEmptyPiles() {
		int numEmptyPiles = 0;
		for (Multiset.Entry<Card> entry : cards.entrySet()) {
			if (entry.getCount() == 0)
				numEmptyPiles++;
		}
		return numEmptyPiles;
	}

	public List<Card> getEmptyPiles() {
		
		List<Card> list = new ArrayList<>();
		for (Multiset.Entry<Card> entry : cards.entrySet()) {
			if(entry.getCount() == 0) {
				list.add(entry.getElement());
			}
		}
	
		return list;
	}
		
	public boolean canBuy(Card card, int numCoins, int numPotions) {
		
		boolean canBuy = false;
		if( has(card) && 
			numCoins >= card.getCost() && 
			numPotions >= card.getPotionCost()) {
			
			canBuy = true;		
		}
		return canBuy;	
	}
	
	public int count(Card card) {
		return cards.count(card);
	}
	
	public boolean has(Card card) {
		return (count(card) > 0);
	}
	
	public void add(Card card) {
		cards.add(card);
	}
	
	public void remove(Card card) {
		if(cards.contains(card)) {
			cards.remove(card);
		} else {
			throw new IllegalArgumentException("no more of this in the supply: "+card);
		}
	}
		
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder("Supply: \n");

		for (Multiset.Entry<Card> entry : cards.entrySet()) {
			str.append(entry.getElement());
			str.append(" ");
			str.append(entry.getCount());
			str.append("\n");
		}

		return str.toString();
	}
}
