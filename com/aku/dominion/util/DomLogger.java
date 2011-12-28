package com.aku.dominion.util;

import java.util.logging.Level;

/*
 * In a production environment, I'd replace this Logger with Log4j.
 * The logging that comes with java writes logs that span 2 lines
 * and it isn't easy to change the formatting.
 */

public class DomLogger {

	private static Level level = Level.INFO;
	
	public void info(String message) {
		if(level == Level.INFO) {
			System.out.println(message);
		}
	}
	
	public void info(String message, Object... objects ) {
		if(level == Level.INFO) {
			System.out.printf(message+"\n", objects);
		}
	}
	
	public static void setLevel(Level logLevel) {
		level = logLevel;
	}
}
