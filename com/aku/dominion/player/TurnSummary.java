package com.aku.dominion.player;

public class TurnSummary {
	private int moneyPlayed;
	private int vps;
	
	public TurnSummary(int moneyPlayed, int vps) {
		this.moneyPlayed = moneyPlayed;
		this.vps = vps;
	}
	
	public int getMoneyPlayed() {
		return moneyPlayed;
	}
	public void setMoneyPlayed(int moneyPlayed) {
		this.moneyPlayed = moneyPlayed;
	}
	public int getVps() {
		return vps;
	}
	public void setVps(int vps) {
		this.vps = vps;
	}
	
	
}
