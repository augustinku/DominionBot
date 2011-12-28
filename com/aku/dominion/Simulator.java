package com.aku.dominion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;

import com.aku.dominion.strategy.BigMoneyUltimate;
import com.aku.dominion.strategy.Strategy;

public class Simulator {

	private static final int NUM_GAMES_TO_PLAY = 100_000;
	private static final boolean COLONY_GAME = false;

	public List<Future<GameResult>> runSim() throws InterruptedException,
			ExecutionException {
		List<Future<GameResult>> futures = new ArrayList<>();

		int numThreads = Runtime.getRuntime().availableProcessors();
		ExecutorService executor = Executors.newFixedThreadPool(numThreads);
		System.out.println("starting with threadcount: " + numThreads);
		for (int i = 0; i < NUM_GAMES_TO_PLAY; i++) {
			Callable<GameResult> game = new Game(createStrats(), COLONY_GAME,
					Level.OFF);

			Future<GameResult> future = executor.submit(game);
			futures.add(future);
		}

		for (Future<GameResult> future : futures) {
			// get() blocks
			future.get();
		}

		executor.shutdown();
		return futures;
	}

	// aggregates the data
	public void printWinners(List<Future<GameResult>> futureList)
			throws InterruptedException, ExecutionException {

		Map<String, Integer> numWinsMap = new HashMap<>();

		int ties = 0;
		int totalRoundsToWin = 0;
		for (Future<GameResult> future : futureList) {
			GameResult gameResult = future.get();

			List<String> names = gameResult.getWinnerNames();
			if (names.size() == 1) {
				String name = names.get(0);
				Integer value = numWinsMap.get(name);
				if (value == null)
					value = new Integer(0);
				value++;
				numWinsMap.put(name, value);

			} else {
				ties++;
			}

			totalRoundsToWin += gameResult.getNumTurns();
		}

		float avgRoundsToWin = totalRoundsToWin / futureList.size();
		System.out.println("");
		System.out.println("Average rounds to win: " + avgRoundsToWin);

		for (Entry<String, Integer> entry : numWinsMap.entrySet()) {
			System.out.println(entry.getKey() + " " + entry.getValue());
		}
		System.out.println("Ties: " + ties);
	}

	protected List<Strategy> createStrats() {

		List<Strategy> strat = new ArrayList<>();
		
	//	strat.add(new BigMoneyMonument()); // the best
	//	strat.add(new BigMoneyCouncilRoom()); // vs monument 
    //	strat.add(new BigMoneySmithy()); // vs monument 43/54
	//	strat.add(new BigMoneyWoodcutter()); // vs monument 32/64
	//	strat.add(new BigMoneyAlchemist()); // vs BigMoneyWoodcutter 23/70, vs lab 55/38
	//	strat.add(new BigMoneyLaboratory());  // vs monument 12/85
		
		strat.add(new BigMoneyUltimate()); // vs monument 12/85
//		strat.add(new BigMoneyBureaucrat()); 
		// strat.add(new BigMoneyCanonical());
		
		return strat;
	}

	public static void main(String[] args) {

		try {
			Simulator simulator = new Simulator();
			List<Future<GameResult>> list = simulator.runSim();
			simulator.printWinners(list);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	/*
	 * public void printWinners(List<GameResult> gameResults) {
	 * 
	 * Map<String, Integer> numWinsMap = new HashMap<>();
	 * 
	 * int ties = 0; int totalRoundsToWin = 0; for (GameResult gameResult :
	 * gameResults) { List<String> names = gameResult.getWinnerNames(); if
	 * (names.size() == 1) { String name = names.get(0); Integer value =
	 * numWinsMap.get(name); if (value == null) value = new Integer(0); value++;
	 * numWinsMap.put(name, value);
	 * 
	 * } else { ties++; }
	 * 
	 * totalRoundsToWin += gameResult.getNumRounds(); }
	 * 
	 * float avgRoundsToWin = totalRoundsToWin / gameResults.size();
	 * System.out.println("\n"); System.out.println("average rounds to win: " +
	 * avgRoundsToWin);
	 * 
	 * for (Entry<String, Integer> entry : numWinsMap.entrySet()) {
	 * System.out.println(entry.getKey() + " " + entry.getValue()); }
	 * System.out.println("ties: " + ties); }
	 */
}