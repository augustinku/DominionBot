package com.aku.dominion;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.aku.dominion.card.Card;

public class Trash {
	private HashMap<Card, Integer> cards = new HashMap<>();  
	
	public Trash() {
	}
	
	public void add(Card card)  {
		Integer count = cards.get(card);
		if(count != null) {
			cards.put(card, count+1);
		} else {
			cards.put(card, 1);
		}
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder("Trash: \n");
		for (Iterator<Map.Entry<Card,Integer>> it = cards.entrySet().iterator(); it.hasNext();) {
			Map.Entry<Card,Integer> entry =  it.next();	
			str.append( entry.getKey() + " " +entry.getValue() + "\n");
		}
		return str.toString();
	}
}
