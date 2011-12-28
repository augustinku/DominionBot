package com.aku.dominion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.aku.dominion.card.Card;
import com.aku.dominion.card.Type;

public class Supply {

	private static final int NUM_COPPER = 60;
	private static final int NUM_SILVER = 40;
	private static final int NUM_GOLD = 30;
	private static final int NUM_POTIONS = 16;
	
	private static final int NUM_VICTORY_CARDS_BASE = 8;
	private static final int NUM_VICTORY_CARDS_3P = 12;
	private static final int NUM_VICTORY_CARDS_4P = 12;

	private int NUM_NON_VICTORY_CARDS = 10;

	private HashMap<Card, Integer> cards;

	public Supply(int numPlayers, boolean isColonyGame) {
		cards = new HashMap<>();
		int numVictoryCards = numVictoryCards(numPlayers);

		// for now add all of the kingdom cards
		for (Card card : Card.values()) {
			if (card.isType(Type.KINGDOM)) {
				cards.put(card, NUM_NON_VICTORY_CARDS);
			}
		}
		
		// base game
		cards.put(Card.COPPER, NUM_COPPER);
		cards.put(Card.SILVER, NUM_SILVER);
		cards.put(Card.GOLD, NUM_GOLD);
		cards.put(Card.ESTATE, numVictoryCards);
		cards.put(Card.DUCHY, numVictoryCards);
		cards.put(Card.PROVINCE, numVictoryCards);
		
		cards.put(Card.CURSE, NUM_NON_VICTORY_CARDS); // TODO fix
		cards.put(Card.POTION, NUM_POTIONS); // should only be used if a kingdom card uses potion
		
		if (isColonyGame) {
			cards.put(Card.COLONY, numVictoryCards);
			cards.put(Card.PLATINUM, NUM_NON_VICTORY_CARDS);
		}
	}	
		

	private int numVictoryCards(int numPlayers) {
		int numVictoryCards = 0;
		if ( numPlayers <= 2) {
			numVictoryCards = NUM_VICTORY_CARDS_BASE;
		} else if (numPlayers == 3) {
			numVictoryCards = NUM_VICTORY_CARDS_3P;
		} else if (numPlayers == 4) {
			numVictoryCards = NUM_VICTORY_CARDS_4P;
		} else {
			throw new IllegalArgumentException("5+ players not supported");
		}
		return numVictoryCards;
	}

	public int numEmptyPiles() {
		int numEmptyPiles = 0;
		for (int count : cards.values()) {
			if (count == 0)
				numEmptyPiles++;
		}
		return numEmptyPiles;
	}

	public List<Card> getEmptyPiles() {
		
		List<Card> list = new ArrayList<>();
		for(Entry<Card, Integer> entry: cards.entrySet() ) {
			if(entry.getValue() == 0) {
				list.add(entry.getKey());
			}
		}
	
		return list;
	}
	
	public int numLeft(Card card) {
		
		int count = 0;
		Integer num = cards.get(card);
		if(num != null ) {
			count = num;
		} else {
		//	System.out.println("card unavailable: "+card);		
		}
				
		return count;
	}
	
	public boolean canBuy(Card card, int numCoins, int numPotions) {
		
		boolean bought = false;
		if( has(card) && 
			numCoins >= card.getCost() && 
			numPotions >= card.getPotionCost()) {
			
			bought = true;		
		}
		return bought;	
	}
	
	public boolean has(Card card) {
		return ( numLeft(card) > 0 );
	}
	
	public void takeCard(Card card) {
		Integer count = cards.get(card);
		if(count != null && count > 0) {
			cards.put(card, --count);
		} else {
			throw new IllegalArgumentException("no more of this in the supply: "+card);
		}
	}
	
	public void returnCard(Card card) {
		Integer count = cards.get(card);
		if(count != null) {
			cards.put(card, ++count);
		} else {
			throw new IllegalArgumentException("this card not in supply: "+card);
		}
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder("Supply: \n");
		for (Iterator<Map.Entry<Card,Integer>> it = cards.entrySet().iterator(); it.hasNext();) {
			Map.Entry<Card,Integer> entry =  it.next();	
			str.append( entry.getKey() + " " +entry.getValue() + "\n");
		}
		return str.toString();
	}
}
