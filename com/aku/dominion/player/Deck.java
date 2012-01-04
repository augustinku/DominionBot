package com.aku.dominion.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.aku.dominion.DomConstants;
import com.aku.dominion.Supply;
import com.aku.dominion.card.Card;
import com.aku.dominion.card.Type;
import com.aku.dominion.util.DomLogger;
import com.aku.dominion.util.SpaceJoiner;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multiset.Entry;
import com.google.common.collect.TreeMultiset;

public class Deck {
	
	private static final DomLogger LOG = new DomLogger(); 

	private List<Collection<Card>> allCards;
	
	protected List<Card> hand = new LinkedList<>();
	protected LinkedList<Card> played = new LinkedList<>();
	protected List<Card> discard = new ArrayList<>();
	protected LinkedList<Card> drawPile = new LinkedList<>();
	protected List<Card> revealed = new LinkedList<>();
	
	private DomPlayer player;
	
	public Deck(DomPlayer player, Supply supply) {
		this.player = player;
		
		allCards = new ArrayList<>();
		for(DeckArea deckArea: DeckArea.values()) {
			allCards.add(toCollection(deckArea));
		}
		
		initDeck(supply);
		Collections.shuffle(drawPile);
	}
	
	private Collection<Card> toCollection(DeckArea deckArea) {
		Collection<Card> collection = null;
		switch (deckArea) {
		case HAND:
			collection = hand;
			break;
		case PLAYED:
			collection = played;
			break;
		case DISCARD:
			collection = discard;
			break;
		case DRAW_PILE:
			collection = drawPile;
			break;
		case REVEALED:
			collection = revealed;
			break;
		default:
			throw new IllegalArgumentException("deckArea not supported: "
					+ deckArea);
		}
		return collection;
	}
	
	public Card revealTopCard() {
		Card card = null;
		if(drawPile.size() == 0) shuffle();
		if(drawPile.size() > 0) {
			card = drawPile.remove(0);
			revealed.add(card);
		}
			
		return card;	
	}
	
	/**
	 * 
	 * @param type
	 * @param numOfThatType
	 * @param excluding
	 * @return
	 */
	public List<Card> revealUntil(Type type, int numOfThatType, Card excluding) {
		
		List<Card> list = new ArrayList<>();
		Card card = null;
		while(numOfThatType > 0 && (card = revealTopCard()) != null ) {
			if( excluding != null && excluding.equals(card)) {
				continue;
			}
			
			if(card.isType(type)) {
				numOfThatType--;
				list.add(card);
			}
		}
		
		String revealedStr = Deck.collectionToStr(revealed);
		LOG.info("... revealing %s" , revealedStr);
		
		return list;
	}

	
	public List<Card> revealUntil(Type type, int numOfThatType) {
		return revealUntil(type, numOfThatType, null);
	}	
	
	public List<Card> revealUntilNot(Type type) {
		
		List<Card> list = new ArrayList<>();
		Card card = null;
		while((card = revealTopCard()) != null ) {
			list.add(card);
			if(!card.equals(type)) {
				break;
			} 	
		}
		
		String revealedStr = Deck.collectionToStr(revealed);
		LOG.info("%s reveals: %s" , player.getName(), revealedStr);
		
		return list;
	}

	
	public List<Card> revealUntil(int num) {
		
		List<Card> list = new ArrayList<>();
		for(int i=0; i<num; i++) {
			Card card = revealTopCard();
			list.add(card);
		}			
		String revealedStr = Deck.collectionToStr(revealed);
		LOG.info("%s reveals: %s" , player.getName(), revealedStr);
		return list;
	}
	

	public void move(DeckArea src, DeckArea dest) {
		Collection<Card> srcCollection = toCollection(src);
		if (srcCollection.size() > 0) {
			toCollection(dest).addAll(srcCollection);
			srcCollection.clear();
			LOG.info("... putting %s into %s", src.getName(), dest.getName());
		}
	}	
	
	public void move(DeckArea src, DeckArea dest, Card card) {
		if(card != null) { 
			toCollection(src).remove(card);
			toCollection(dest).add(card);
			LOG.info("... putting %s into %s", card, dest.getName());
		}
	}
	
	public void move(DeckArea src, DeckArea dest, Collection<Card> collection) {
		for(Card card: collection) {
			move(src, dest, card);
		}
	}
	
	public void cleanupPhase() {
		discard.addAll(hand);
		for(int i = played.size() - 1; i >= 0; i--) {
			Card card = played.get(i);
			card.cleanupPhase(player);
		}
		discard.addAll(played);
		hand.clear();
		played.clear();
		
		drawNewHand();
	}

	public void playCard(int index) {
		Card card = hand.get(index);
		hand.remove(index);
		played.add(card);
		card.play(player);
	}
	
	protected void initDeck(Supply supply) {
		for(int i=0; i<DomConstants.START_ESTATE; i++) {
			// 3 extra estates per player
			drawPile.add(Card.ESTATE);
		}
		for(int i=0; i<DomConstants.START_COPPER; i++) {
			supply.remove(Card.COPPER);
			drawPile.add(Card.COPPER);
		}
	}
	
	public void shuffle() {
		if (discard.size() > 0) {
			LOG.info("(%s reshuffles.)", player.getName());
			Collections.shuffle(discard);
			drawPile.addAll(discard);
			discard.clear();
		}
	}
		
	public void drawNewHand() {
		drawCards(DomConstants.DEFAULT_HAND_SIZE);
		String handStr = Deck.collectionToStr(hand);
		LOG.info("(%s draws: %s)" , player.getName(), handStr);
	}
	
	public void revealHand() {
		String handStr = Deck.collectionToStr(hand);
		LOG.info("(%s reveals: %s)" , player.getName(), handStr);
	}
	
	protected int moneyInHand() {
		int money = 0;
		for(Card card: hand) {
			money += card.getPlusCoins();
		}
		return money;
	}

	public void drawCards(int numCards) {
		for(int i=0; i<numCards; i++) {
			if(drawPile.size() == 0) {
				shuffle();
			}	
			
			if(drawPile.size() > 0) {
				Card card = drawPile.removeFirst();
				hand.add(card);
			}
		}
	}

	
	/* (non-Javadoc)
	 * @see com.aku.dominion.player.Player#scoreFromCards()
	 */
	public int scoreFromCards() {
		int total = 0;
		for(Collection<Card> list: allCards ) {
			for(Card card: list ) {
				if(card.isType(Type.VICTORY)) {
					int score =  card.getVictoryPoints();
					if(score == 0) {
						card.getVictoryPoints(this);
					}
					total += score;
				}
			}
		}
		
		return total; 
	}


	// you can't just add up all the uniques in each list,
	// since there might be duplicates
	public int getNumUniqueInDeck() {		
		Set<Card> set = new HashSet<Card>();
		for(Collection<Card> list: allCards ) {
			for(Card card: list ) {
				set.add(card);
			}
		}
		
		return set.size(); 
	}

	
	public int getNumInDeck(Type type) {		
		int num = 0;
		for(Collection<Card> list: allCards ) {
			for(Card card: list ) {
				if(card.isType(type)) {
					num++;
				}
			}
		}
		
		return num; 
	}
		
	public int getNumInDeck(Card card) {
		int numCopies = 0;
		for(Collection<Card> list: allCards ) {
			numCopies += Collections.frequency(list, card);
		}
		return numCopies;
	}
	
	public int getNumInDeck() {
		int count = 0;
		for(Collection<Card> list: allCards ) {
			count += list.size();
		}
		return count;
	}
	
	
	public int getNumInHand(Type type) {
		int num = 0;
		for(Card card: hand ) {
			if(card.isType(type)) {
				num++;
			}
		}
		return num;
	}
		
	public Multiset<Card> summarizeDeck() {
		Multiset<Card> bag = TreeMultiset.create();
		for(Collection<Card> list: allCards ) {
			bag.addAll(list);
		}
		return bag;
	}
	
	public void returnToTopOfDeck(Card card) {
		LOG.info("%s returns %s to top of deck" , player.getName(), card);
		played.removeLastOccurrence(Card.ALCHEMIST);
		drawPile.addFirst(Card.ALCHEMIST);
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();

		Multiset<Card> bag = summarizeDeck();
		str.append("[");
		str.append(bag.size());
		str.append(" cards");
		str.append("] ");
		bagToStr(str, bag);
		
		/*
		System.out.print("hand\n");
		System.out.print(JOINER.join(hand));

		System.out.print("\ndrawPile\n");
		System.out.print(JOINER.join(drawPile));

		System.out.print("\ndiscard\n");
		System.out.print(JOINER.join(discard));
				
		System.out.print("\nplayed\n");
		System.out.print(JOINER.join(played));
		*/
		
		return str.toString();
	}

	public void bagToStr(StringBuilder str, Multiset<Card> bag) {
		boolean needsSpace = false;
		for (Entry<Card> entry : bag.entrySet()) {
			
			if(needsSpace) {
				str.append(" ");
			} else {
				needsSpace = true;
			}
			
			str.append(entry.getCount());
			str.append(" ");
			str.append(entry.getElement());	
		}
	}
	
	public static String collectionToStr(Collection<Card> collection) {
	   return SpaceJoiner.JOINER.join(collection);
	}

	public List<Card> getHand() {
		return hand;
	}

	public List<Card> getPlayed() {
		return played;
	}

	public List<Card> getDiscard() {
		return discard;
	}

	public LinkedList<Card> getDrawPile() {
		return drawPile;
	}
	
	public List<Card> getRevealed() {
		return revealed;
	}
}
