package com.aku.dominion;

import java.util.List;

public class GameResult {
	private List<String> winnerNames;
	private int numTurns;
	
	public int getNumTurns() {
		return numTurns;
	}
	public void setNumTurns(int numTurns) {
		this.numTurns = numTurns;
	}
	public List<String> getWinnerNames() {
		return winnerNames;
	}
	public void setWinnerNames(List<String> winnerNames) {
		this.winnerNames = winnerNames;
	}
}
