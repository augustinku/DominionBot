package com.aku.dominion.card.decorator;

import com.aku.dominion.player.DomPlayer;

public class AbstractDecorator implements DecoratorInterface {
	public void play(DomPlayer player) {		
	}
		
	public void playOnOpponent(DomPlayer opponent) {
	}
	
	public void initPhase(DomPlayer player) {
	}
	
	public void cleanupPhase(DomPlayer player) {
	}
}
