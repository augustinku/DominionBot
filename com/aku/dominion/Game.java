package com.aku.dominion;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Level;

import com.aku.dominion.card.Card;
import com.aku.dominion.player.Deck;
import com.aku.dominion.player.DomPlayer;
import com.aku.dominion.strategy.BigMoneyUltimate;
import com.aku.dominion.strategy.BigMoneyX;
import com.aku.dominion.strategy.Strategy;
import com.aku.dominion.util.DomLogger;
import com.aku.dominion.util.SpaceJoiner;

public class Game implements Callable<GameResult> {

	private static final DomLogger LOG = new DomLogger(); 

	int currPlayerNum = -1;
	
	private List<DomPlayer> players;
	private Supply supply;
//	private Trash trash = new Trash(); // currently unused
	
	private int numPilesToEndGame;
	private boolean isColonyGame;
	
	public Game(List<Strategy> strats, boolean isColonyGame, Level logLevel) {
		
		this.isColonyGame = isColonyGame;
		DomLogger.setLevel(logLevel);
		
		supply = new Supply(strats.size(), isColonyGame);
		
		players = new ArrayList<>();
		for(Strategy strategy: strats) {
			DomPlayer player = new DomPlayer(this, strategy);
			players.add(player);
		}
	
		numPilesToEndGame = DomConstants.NUM_PILES_TO_END_GAME;
		if(players.size() >= 5) {
			numPilesToEndGame = DomConstants.NUM_PILES_TO_END_GAME_5P;
		}
	
		DomLogger.setLevel(logLevel);
	}
	
	@Override
	public GameResult call() {
		return play();
	}
	
	public GameResult play() {
		
		int numTurns = 0;		
		while(continueGame()) {
			currPlayerNum = ( currPlayerNum + 1 ) % players.size();
			if(currPlayerNum == 0) {
				numTurns++;
			}
			
			DomPlayer currPlayer = players.get(currPlayerNum); 
			
			LOG.info("");
			LOG.info("--- %s's turn %d ---", currPlayer.getName(), numTurns );
			currPlayer.takeTurn();
		}

		GameResult gameResult = makeGameResult(numTurns);
		logResults(gameResult);
		return gameResult;
	}
		
	private GameResult makeGameResult(int numTurns) {
		
		GameResult gameResult = new GameResult();
		gameResult.setNumTurns(numTurns);
		
		List<String> winners = new ArrayList<>();
		gameResult.setWinnerNames(winners);
		
		DomPlayer currWinner = null;
		int maxScore = Integer.MIN_VALUE;
		for(DomPlayer player: players) {
					
			int score = player.getScore();			
			if(score > maxScore) {
				maxScore = score;
				currWinner = player;
				winners.clear();
				winners.add(player.getName());
			} else if(score == maxScore) {
				
				// player i took fewer turns, so should be the only winner
				if(player.getTurns().size() < currWinner.getTurns().size()) {
					winners.clear();	
				} 				

				winners.add(player.getName());
			}
		}
		
		return gameResult;
	}

	private void logResults(GameResult gameResult) {
		
		List<String> winners = gameResult.getWinnerNames();
		if(winners.size() == 1) {
			LOG.info("%s wins!", winners.get(0));
		} else {
			String nameStr = SpaceJoiner.JOINER.join(winners);
			LOG.info("%s rejoice in their shared victory!", nameStr);
		}
		
		for(int i=0; i<players.size(); i++) {
			LOG.info(players.get(i).toString());
		} 
	}
	
	public void actionToOpponents(Card card) {
		if(players.size() == 0) return;
		
		for(int i=0; i<players.size(); i++) {
			if(i == currPlayerNum ) continue;
			
			DomPlayer opponent = players.get(i);
			// check for actions in play
			// check for reactions
			// check for way to stop attack
		//	if(card == card.MONTEBANK) {
		//		opponent.getDeck().getHand().contains(card.CURSE);
		//		player discard...
		//	}
			card.playOnOpponent(opponent);
		}
	}
	
	private boolean continueGame() {
		boolean continueGame = true;
		
		if(supply.numEmptyPiles() >= numPilesToEndGame	) {
			List<Card> list = supply.getEmptyPiles();
			String emptyPiles = Deck.collectionToStr(list);
			LOG.info("\n%s are all gone.\n", emptyPiles);
			continueGame = false;
		} else if(supply.numLeft(Card.PROVINCE) == 0 ) {
			LOG.info("\nAll provinces are gone.\n");
			continueGame = false;
		} else if(isColonyGame && supply.numLeft(Card.COLONY) == 0) {
			LOG.info("\nAll colonies are gone.\n");
			continueGame = false;
		}		
		
		return continueGame;
	}

	public static void main(String[] args) {
		
		List<Strategy> strat = new ArrayList<>();
//		strat.add(new WitchStrategy());
		
		strat.add(new BigMoneyX(Card.CHANCELLOR, 1 ));
//		strat.add(new BigMoneyBureaucrat());
//		strat.add(new BigMoneyCouncilRoom());		
//		strat.add(new BigMoneySmithy());
//		strat.add(new BigMoneyAlchemist());	
//		strat.add(new BigMoneyLaboratory());
//		strat.add(new BigMoneyWoodcutter());
		strat.add(new BigMoneyUltimate());
		//strat.add(new BigMoneyCanonical());
		
		boolean colonyGame = false;
		Game game = new Game(strat, colonyGame, Level.INFO);
		game.play();
	}

	public Supply getSupply() {
		return supply;
	}

	public void setSupply(Supply supply) {
		this.supply = supply;
	}	
}
