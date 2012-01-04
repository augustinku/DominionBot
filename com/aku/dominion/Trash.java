package com.aku.dominion;

import com.aku.dominion.card.Card;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

public class Trash {
	private Multiset<Card> cards = HashMultiset.create();
	
	public Trash() {
	}
	
	public void add(Card card) {
		cards.add(card);
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder("Trash: ");
		for ( Multiset.Entry<Card> entry : cards.entrySet()) {
			str.append( entry.getElement());
			str.append( " " );
			str.append(entry.getCount());
			str.append("\n");
		}
		return str.toString();
	}
}