package com.aku.dominion.card.decorator;

import com.aku.dominion.player.Deck;
import com.aku.dominion.player.DomPlayer;


public interface DecoratorInterface {
	
	 // play the card and gain the effects
	 
	 
	public abstract void play(DomPlayer player);
	
	public abstract void playOnOpponent(DomPlayer player);
	
	public abstract void initPhase(DomPlayer player);
	
	public abstract void cleanupPhase(DomPlayer player);
	
	public int getVictoryPoints(Deck deck);

}
