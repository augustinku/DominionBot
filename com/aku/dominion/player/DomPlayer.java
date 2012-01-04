package com.aku.dominion.player;

import java.util.ArrayList;
import java.util.List;

import com.aku.dominion.Game;
import com.aku.dominion.Supply;
import com.aku.dominion.card.Card;
import com.aku.dominion.card.Type;
import com.aku.dominion.rule.BuyCondition;
import com.aku.dominion.rule.BuyRule;
import com.aku.dominion.strategy.Strategy;
import com.aku.dominion.util.DomLogger;
import com.google.common.collect.Multiset;
import com.google.common.collect.TreeMultiset;

public class DomPlayer {

	private static final DomLogger LOG = new DomLogger();

	private Deck deck;
	private Strategy strategy;
	private List<TurnSummary> turns;
	private Game game;
	private int numVPs;

	// need to be reset every turn
	private int numActions;
	private int numCoins;
	private int numPotions;
	private int numBuys;

	public DomPlayer(Game game, Strategy strategy) {
		this.game = game;
		this.strategy = strategy;

		deck = new Deck(this, game.getSupply());
		deck.drawNewHand();

		turns = new ArrayList<>();
	}

	public void takeTurn() {
		initPhase();
		actionPhase();
		treasurePhase();
		buyPhase();
		deck.cleanupPhase();

		turns.add(new TurnSummary(numCoins, getScore()));
	}

	protected void actionPhase() {
		
		List<Card> hand = deck.getHand();
	
		// play cards that give you more actions
		for (int i = hand.size() - 1; i >= 0; i--) {
			Card card = hand.get(i);
			int plusActions = card.getPlusActions();
			if (plusActions > 0) {
				playAction(i);
			}
		}
		
		
		while(numActions > 0 && deck.getNumInHand(Type.ACTION) > 0 ) { 
	
			// play terminal actions
			// it may discard the whole hand
			int index = 0;
			for (int i = hand.size() - 1; i >= 0 && numActions > 0; i--) {
				Card card = hand.get(i);
				if (card.isType(Type.ACTION)) {
					index = i;
					break;
				}
			}
			playAction(index);
		}
	}

	private void treasurePhase() {
		playBaseTreasures();
		
		while( deck.getNumInHand(Type.TREASURE) > 0 ) {
			playKingdomTreasures();
		}
	}

	private void playBaseTreasures() {
		List<Card> hand = deck.getHand();

		Multiset<Card> bag = TreeMultiset.create();
		for (int i = hand.size() - 1; i >= 0; i--) {
			Card card = hand.get(i);
			if (card.isType(Type.TREASURE) && !card.isType(Type.KINGDOM)) {
				numCoins += card.getPlusCoins();
				numPotions += card.getPlusPotions();
				hand.remove(i);
				deck.getDiscard().add(card);
	//			deck.playCard(i);
				bag.add(card);
			}
		}

		// create a one line log entry for all of the treasures played
		StringBuilder str = new StringBuilder();
		deck.bagToStr(str, bag);
		LOG.info("%s plays %s ", getName(), str);
	}

	private void playKingdomTreasures() {
		List<Card> hand = deck.getHand();
		for (int i = hand.size() - 1; i >= 0; i--) {
			Card card = hand.get(i);
			if (card.isType(Type.TREASURE)) {
				playCard(i);
			}
		}
	}

	
	private void buyPhase() {
		Supply supply = game.getSupply();
		for (int i = 0; i < numBuys; i++) {
			Card card = findCardToBuy(supply, strategy.getBuyRules());
			gainCard(card, false);
			// pay for card
			if (card != null) {
				numCoins -= card.getCost();
				numPotions -= card.getPotionCost();
				LOG.info("%s buys %s ", getName(), card);
			}
		}
	}

	public void gainCard(Card card) {
		gainCard(card, true);
	}

	public void gainCard(Card card, boolean log) {
		if (card != null) {
			Supply supply = game.getSupply();
			if (supply.has(card)) {
				supply.remove(card);
				deck.discard.add(card);
				if (log)
					LOG.info("%s gains: %s", getName(), card);
			} else {
				if (log)
					LOG.info("%s gains nothing)", getName());
			}
		}
	}

	public void gainCardOnDeck(Card card) {
		if (card != null) {
			Supply supply = game.getSupply();
			if (supply.has(card)) {
				supply.remove(card);
				deck.drawPile.addFirst(card);
				LOG.info("%s gains: %s", getName(), card);
			} else {
				LOG.info("%s gains nothing)", getName());
			}
		}
	}

	public void putTypeOnDrawPile(Type type) {
		List<Card> hand = deck.getHand();
		Card card = null;
		for (int i = hand.size() - 1; i >= 0; i--) {
			card = hand.get(i);
			if (card.isType(type)) {
				hand.remove(i);
				break;
			}
		}

		deck.drawPile.addFirst(card);
		LOG.info("%s puts %s on draw pile", getName(), card);
	}

	protected Card findCardToBuy(Supply supply, List<BuyRule> rules) {
		Card card = null;
		for (BuyRule buyRule : rules) {
			card = buyRule.getTargetCard();
			BuyCondition condition = buyRule.getBuyCondition();

			if (condition != null) {
				if (!condition.execute(this, supply,
						buyRule.getConditionCard(), buyRule.getOperand())) {
					continue;
				}
			}

			if (supply.canBuy(card, numCoins, numPotions)) {
				break;
			} else {
				card = null;
			}
		}
		return card;
	}

	private void playAction(int index) {
		numActions--;
		Card card = playCard(index);
		game.actionToOpponents(card);
	}

	private Card playCard(int index) {
		List<Card> hand = deck.getHand();
		Card card = hand.get(index);
		LOG.info("%s plays %s", getName(), card);
		deck.playCard(index);
		return card;
	}
	
	private void initPhase() {
		numActions = 1;
		numBuys = 1;
		numCoins = 0;
		numPotions = 0;

		for (Card card : deck.played) {
			card.initPhase(this);
		}
	}

	public void drawCards(int numCards) {
		if (numCards > 0) {
			LOG.info("... drawing %d cards", numCards);
			deck.drawCards(numCards);
		}
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aku.dominion.player.Player#toString()
	 */
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(getName()).append(": ");
		str.append(getScore()).append(" points");
		str.append(" (").append(getVPs()).append(" VPs); ");

		str.append(turns.size());
		str.append(" turns");

		str.append(" \n");
		str.append(deck.toString());

		return str.toString();
	}

	public String getName() {
		return getStrategy().getName();
	}

	public int getScore() {
		return deck.scoreFromCards() + getVPs();
	}

	public int getVPs() {
		return numVPs;
	}

	public void addVPs(int numVPs) {
		if (numVPs != 0) {
			LOG.info("... getting +%d VPs", numVPs);
			this.numVPs += numVPs;
		}
	}

	public void addBuy(int numBuys) {
		if (numBuys != 0) {
			LOG.info("... getting +%d buys", numBuys);
			this.numBuys += numBuys;
		}
	}

	public void addPotion(int numPotions) {
		if (numPotions != 0) {
			LOG.info("... getting +%d potions", numPotions);
			this.numPotions += numPotions;
		}
	}

	public void addCoin(int numCoins) {
		if (numCoins != 0) {
			LOG.info("... getting +%d coins", numCoins);
			this.numCoins += numCoins;
		}
	}

	public void addAction(int numActions) {
		if (numActions != 0) {
			LOG.info("... getting +%d actions", numActions);
			this.numActions += numActions;
		}
	}

	public Strategy getStrategy() {
		return strategy;
	}

	public List<TurnSummary> getTurns() {
		return turns;
	}

	public Deck getDeck() {
		return deck;
	}

	public int getNumVPs() {
		return numVPs;
	}
	
	public int getNumActions() {
		return numActions;
	}

	public int getNumCoins() {
		return numCoins;
	}

	public int getNumPotions() {
		return numPotions;
	}

	public int getNumBuys() {
		return numBuys;
	}

	public Game getGame() {
		return game;
	}
	
	
}
