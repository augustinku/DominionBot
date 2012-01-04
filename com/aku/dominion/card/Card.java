package com.aku.dominion.card;

import static com.aku.dominion.card.Type.ACTION;
import static com.aku.dominion.card.Type.ATTACK;
import static com.aku.dominion.card.Type.KINGDOM;
import static com.aku.dominion.card.Type.TREASURE;
import static com.aku.dominion.card.Type.VICTORY;

import com.aku.dominion.card.decorator.Adventurer;
import com.aku.dominion.card.decorator.Alchemist;
import com.aku.dominion.card.decorator.Apothecary;
import com.aku.dominion.card.decorator.Bureaucrat;
import com.aku.dominion.card.decorator.Chancellor;
import com.aku.dominion.card.decorator.CouncilRoom;
import com.aku.dominion.card.decorator.DecoratorInterface;
import com.aku.dominion.card.decorator.Duke;
import com.aku.dominion.card.decorator.Fairgrounds;
import com.aku.dominion.card.decorator.Familiar;
import com.aku.dominion.card.decorator.Gardens;
import com.aku.dominion.card.decorator.Golem;
import com.aku.dominion.card.decorator.Loan;
import com.aku.dominion.card.decorator.Montebank;
import com.aku.dominion.card.decorator.Nobles;
import com.aku.dominion.card.decorator.ScryingPool;
import com.aku.dominion.card.decorator.SilkRoad;
import com.aku.dominion.card.decorator.Venture;
import com.aku.dominion.card.decorator.Vineyard;
import com.aku.dominion.card.decorator.Witch;
import com.aku.dominion.player.Deck;
import com.aku.dominion.player.DomPlayer;

public enum Card {
	
	// int cost, int potionCost, int plusCoins, int, plusPotions 
	// int victoryPoints, int plusActions, int plusCards, int plusBuys, plusVPs) 
    // name, decorator, cardTypes 
	CURSE    (0, 0, 0, 0, -1, 0, 0, 0, 0, "Curse", null, new Type[]{} ),
    ESTATE   (2, 0, 0, 0, 1, 0, 0, 0, 0, "Estate", null, new Type[]{VICTORY} ),
    DUCHY    (5, 0, 0, 0, 3, 0, 0, 0, 0, "Duchy", null, new Type[]{VICTORY} ),
    PROVINCE (8, 0, 0, 0, 6, 0, 0, 0, 0, "Province", null, new Type[]{VICTORY} ),
    COLONY  (11, 0, 0, 0, 10, 0, 0, 0, 0, "Colony", null, new Type[]{VICTORY} ),
	
    COPPER   (0, 0, 1, 0, 0, 0, 0, 0, 0, "Copper", null, new Type[]{TREASURE} ),
    SILVER   (3, 0, 2, 0, 0, 0, 0, 0, 0, "Silver", null, new Type[]{TREASURE} ),
    GOLD     (6, 0, 3, 0, 0, 0, 0, 0, 0, "Gold", null, new Type[]{TREASURE} ),
    PLATINUM (9, 0, 5, 0, 0, 0, 0, 0, 0, "Platinum", null, new Type[]{TREASURE} ),
    POTION   (4, 0, 0, 1, 0, 0, 0, 0, 0, "Potion", null, new Type[]{TREASURE} ),
    
    // kingdom cards
    GARDENS        (4, 0, 0, 0, 0, 0, 0, 0, 0, "Gardens", new Gardens(), new Type[]{KINGDOM, VICTORY}),
    SILK_ROAD      (4, 0, 0, 0, 0, 0, 0, 0, 0, "Silk_Road", new SilkRoad(), new Type[]{KINGDOM, VICTORY}),
    VINEYARD       (0, 1, 0, 0, 0, 0, 0, 0, 0, "Vineyard", new Vineyard(), new Type[]{KINGDOM, VICTORY}),
    FAIRGROUNDS    (6, 0, 0, 0, 0, 0, 0, 0, 0, "Fairgrounds", new Fairgrounds(), new Type[]{KINGDOM, VICTORY}),
    DUKE           (5, 0, 0, 0, 0, 0, 0, 0, 0, "Duke", new Duke(), new Type[]{KINGDOM, VICTORY}),
//  ISLAND          TODO
//  FARMLAND       (6, 0, 0, 0, 2, 0, 0, 0, 0, "Farmland", new Farmland(), new Type[]{KINGDOM, VICTORY}),
//  TUNNEL         (3, 0, 0, 0, 2, 0, 0, 0, 0, "Tunnel", new Tunnel(), new Type[]{KINGDOM, VICTORY, REACTION}),
    NOBLES         (6, 0, 0, 0, 2, 0, 0, 0, 0, "Nobles", new Nobles(), new Type[]{KINGDOM, VICTORY, ACTION}),
    GREAT_HALL     (3, 0, 0, 0, 1, 1, 1, 0, 0, "Great Hall", null, new Type[]{KINGDOM, VICTORY, ACTION}),
    HAREM          (6, 0, 2, 0, 2, 0, 0, 0, 0, "Harem", null, new Type[]{KINGDOM, VICTORY, TREASURE}),
    
	ADVENTURER     (6, 0, 0, 0, 0, 0, 0, 0, 0, "Adventurer", new Adventurer(), new Type[]{KINGDOM, ACTION}),
	GOLEM          (4, 1, 0, 0, 0, 0, 0, 0, 0, "Golem", new Golem(), new Type[]{KINGDOM, ACTION}),
	LOAN           (3, 0, 1, 0, 0, 0, 0, 0, 0, "Loan", new Loan(), new Type[]{KINGDOM, TREASURE}),
	VENTURE        (5, 0, 2, 0, 0, 0, 0, 0, 0, "Venture", new Venture(), new Type[]{KINGDOM, TREASURE}),
	SCRYING_POOL   (2, 1, 0, 0, 0, 0, 0, 0, 0, "Scrying Pool", new ScryingPool(), new Type[]{KINGDOM, ACTION}),
	APOTHECARY     (2, 1, 0, 0, 0, 0, 0, 0, 0, "Apothecary", new Apothecary(), new Type[]{KINGDOM, ACTION}),
//	THIEF          (4, 0, 0, 0, 0, 0, 0, 0, 0, "Thief", new Thief(), new Type[]{KINGDOM, ACTION, ATTACK}),
	
	
    SMITHY         (4, 0, 0, 0, 0, 0, 3, 0, 0, "Smithy", null, new Type[]{KINGDOM, ACTION}),
    COUNCIL_ROOM   (5, 0, 0, 0, 0, 0, 4, 1, 0, "Council Room", new CouncilRoom(), new Type[]{KINGDOM, ACTION}),
    MARGRAVE       (5, 0, 0, 0, 0, 0, 3, 0, 0, "Margrave", null, new Type[]{KINGDOM, ACTION, ATTACK}),
    MONUMENT       (4, 0, 2, 0, 0, 0, 0, 0, 1, "Monument", null, new Type[]{KINGDOM, ACTION}),
    VILLAGE        (3, 0, 0, 0, 0, 2, 1, 0, 0, "Village", null, new Type[]{KINGDOM, ACTION}),
    WORKERS_VILLAGE(4, 0, 0, 0, 0, 2, 1, 1, 0, "Worker's Village", null, new Type[]{KINGDOM, ACTION}),
    BAZAAR         (5, 0, 1, 0, 0, 2, 1, 0, 0, "Bazaar", null, new Type[]{KINGDOM, ACTION}),
    FESTIVAL       (5, 0, 2, 0, 0, 2, 0, 1, 0, "Festival", null, new Type[]{KINGDOM, ACTION}),
    MARKET         (5, 0, 1, 0, 0, 1, 1, 1, 0, "Market", null, new Type[]{KINGDOM, ACTION}),
    LABORATORY     (5, 0, 0, 0, 0, 1, 2, 0, 0, "Laboratory", null, new Type[]{KINGDOM, ACTION}),
    ALCHEMIST      (3, 1, 0, 0, 0, 1, 2, 0, 0, "Alchemist", new Alchemist(), new Type[]{KINGDOM, ACTION}),
    WOODCUTTER     (3, 0, 2, 0, 0, 0, 0, 1, 0, "Woodcutter", null, new Type[]{KINGDOM, ACTION}),
    CHANCELLOR     (3, 0, 2, 0, 0, 0, 0, 0, 0, "Chancellor", new Chancellor(), new Type[]{KINGDOM, ACTION}),
    WITCH          (5, 0, 0, 0, 0, 0, 2, 0, 0, "Witch", new Witch(), new Type[]{KINGDOM, ACTION, ATTACK}),
//    SEA_HAG        (4, 0, 0, 0, 0, 0, 0, 0, 0, "Sea Hag", new SeaHag(), new Type[]{KINGDOM, ACTION, ATTACK}),
	FAMILIAR       (3, 1, 0, 0, 0, 1, 1, 0, 0, "Familiar", new Familiar(), new Type[]{KINGDOM, ACTION, ATTACK}),
	MONTEBANK      (5, 0, 2, 0, 0, 0, 0, 0, 0, "Montebank", new Montebank(), new Type[]{KINGDOM, ACTION, ATTACK}),
	BUREAUCRAT     (4, 0, 0, 0, 0, 0, 0, 0, 0, "Bureaucrat", new Bureaucrat(), new Type[]{KINGDOM, ACTION, ATTACK});
	 
	private int cost;
	private int potionCost; // usually 1 or 0
	private int victoryPoints;
	private int plusCoins; // copper is 1, silver is 2..
	private int plusPotions; 
	private int plusActions;
	private int plusCards;
	private int plusBuys;
	private int plusVPs; // monument is +1 
	private String name;
	private DecoratorInterface decorator;

	private Type[] types;
	
	private Card(int cost, int potionCost, int plusCoins, int plusPotions, int victoryPoints, 
			int plusActions, int plusCards, int plusBuys, int plusVPs, String name, DecoratorInterface decorator, Type[] types ) {
	
		this.cost = cost;
		this.potionCost = potionCost; // usually 1 or 0
		this.plusCoins = plusCoins; // copper is 1, silver is 2..
		this.plusPotions = plusPotions;
		this.victoryPoints = victoryPoints;
		
		this.plusActions = plusActions;
		this.plusCards = plusCards;
		this.plusBuys = plusBuys;
		this.plusVPs = plusVPs;
				
		this.name = name;
		this.decorator = decorator;
		
		this.types = types;
	}

	public void play(DomPlayer player) {
	
		player.drawCards(getPlusCards());
		player.addVPs(getPlusVPs());
		player.addBuy(getPlusBuys());
		player.addAction(getPlusActions());
		player.addCoin(getPlusCoins());
		
		if(getPlusPotions() > 0) {
			player.addPotion(getPlusPotions());
		}
		
		if(decorator != null) {
			decorator.play(player);
		}
		// opponent side effects
	}
	
	public void playOnOpponent(DomPlayer player) {
		if(decorator != null) {
			decorator.playOnOpponent(player);
		}
	}
	
	public void initPhase(DomPlayer player) {
		if(decorator != null) {
			decorator.initPhase(player);
		}
	}
	
	public void cleanupPhase(DomPlayer player) {
		if(decorator != null) {
			decorator.cleanupPhase(player);
		}
	}
	
	
	
	
	
	/* (non-Javadoc)
	 * @see com.aku.dominion.cards.Cardss#getCost()
	 */
	
	public int getCost() {
		return cost;
	}


	/* (non-Javadoc)
	 * @see com.aku.dominion.cards.Cardss#getPotionCost()
	 */
	
	public int getPotionCost() {
		return potionCost;
	}


	/* (non-Javadoc)
	 * @see com.aku.dominion.cards.Cardss#getCoinValue()
	 */
	
	public int getPlusCoins() {
		return plusCoins;
	}

	
	public int getPlusPotions() {
		return plusPotions;
	}
	
	/* (non-Javadoc)
	 * @see com.aku.dominion.cards.Cardss#getVictoryPoints()
	 */
	
	public int getVictoryPoints() {
		return victoryPoints;
	}


	public int getVictoryPoints(Deck deck) {
		return decorator.getVictoryPoints(deck);
	}
	
	/* (non-Javadoc)
	 * @see com.aku.dominion.cards.Cardss#getPlusActions()
	 */
	
	public int getPlusActions() {
		return plusActions;
	}


	/* (non-Javadoc)
	 * @see com.aku.dominion.cards.Cardss#getPlusCards()
	 */
	
	public int getPlusCards() {
		return plusCards;
	}

	/* (non-Javadoc)
	 * @see com.aku.dominion.cards.Cardss#getPlusBuys()
	 */
	
	public int getPlusBuys() {
		return plusBuys;
	}

	public int getPlusVPs() {
		return plusVPs;
	}	
	
	public String toString() {
		return name;
	}
	
	public boolean isType(Type cardType) {
		boolean result = false;
		for(Type type: types) {
			if( type == cardType ) result = true;
		}
		return result;
	}
}
