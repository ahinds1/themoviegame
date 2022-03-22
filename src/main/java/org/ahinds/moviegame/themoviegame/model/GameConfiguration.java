package org.ahinds.moviegame.themoviegame.model;

import java.time.Duration;

/* GameConfiguration.java (aka GameOptions)
 * 
 * POJO collection for a game's configuration.
 * 
 * Default Builder Options are Casual Game settings and 
 * used to initialize options shown in NewGameView.fxml.
 * 
 * FUTURE WORK:
 * 		- Add more game modifiers here when added to the game
 * 
 */

public class GameConfiguration {
	public static GameConfiguration DEFAULT_CASUAL_OPTIONS = GameConfiguration.valueOf(new Builder());
	public static GameConfiguration DEFAULT_COMPETITIVE_OPTIONS = 
			GameConfiguration.valueOf(
				new Builder()
						.gameMode(GameMode.COMPETITIVE)
						.turnTimeLimit(true)
						.turnTimeDuration(30)
			);

	private final GameMode gameMode;
	private final int numberOfRounds;
	private final int baseLives;
	private final String saveFileName;
	private final boolean answerLookupAuto;
	private final boolean turnTimeLimit;
	private final Duration turnTimeDuration;
	
	public static class Builder {
		public static final GameMode DEFAULT_GAMEMODE = GameMode.CASUAL;
		public static final int DEFAULT_ROUNDS = 3;
		public static final int DEFAULT_LIVES = 3;
		public static final boolean DEFAULT_ANSWERLOOKUP = true;
		public static final boolean DEFAULT_TIMELIMIT = false;
		public static final Duration DEFAULT_TURN_DURATION = Duration.ZERO; // seconds
		
		private GameMode gameMode = DEFAULT_GAMEMODE;
		private int numberOfRounds = DEFAULT_ROUNDS;
		private int baseLives = DEFAULT_LIVES;
		private String saveFileName;
		private boolean answerLookupAuto = DEFAULT_ANSWERLOOKUP;
		private boolean turnTimeLimit = DEFAULT_TIMELIMIT;
		private Duration turnTimeDuration = DEFAULT_TURN_DURATION;
		 
		public Builder() {
			
		}
		 
		public Builder gameMode(GameMode gameMode) {
			 this.gameMode = gameMode;
			 return this;
		}
		public Builder numberOfRounds(int numberOfRounds) {
			 this.numberOfRounds = numberOfRounds;
			 return this;
		}
		public Builder baseLives(int baseLives) {
			 this.baseLives = baseLives;
			 return this;
		}
		public Builder saveFileName(String saveFileName) {
			 this.saveFileName = saveFileName;
			 return this;
		}
		public Builder answerLookupAuto(boolean answerLookupAuto) {
			 this.answerLookupAuto = answerLookupAuto;
			 return this;
		}
		 
		public Builder turnTimeLimit(boolean turnTimeLimit) {
			 this.turnTimeLimit = turnTimeLimit;
			 return this;
		}
		
		public Builder turnTimeDuration(int turnTimeDuration) {
			 this.turnTimeDuration = Duration.ofSeconds(turnTimeDuration);
			 return this;
		}
		 
		private GameConfiguration build() {
			 return new GameConfiguration(this);
		}
	
		public GameMode getGameMode() {
			return gameMode;
		}
	
		public int getNumberOfRounds() {
			return numberOfRounds;
		}
		
		public int getBaseLives() {
			return baseLives;
		}
		
		public String getSaveFileName() {
			return saveFileName;
		}
		
		public boolean getAnswerLookUpAuto() {
			return answerLookupAuto;
		}
		
		public boolean getTurnTimeLimit() {
			return turnTimeLimit;
		}
		
		public Duration getTurnTimeDuration() {
			return turnTimeDuration;
		}
	}
	
	public static GameConfiguration defaultOptions() {
		return DEFAULT_CASUAL_OPTIONS;
	}
	
	public static GameConfiguration defaultCasualOptions() {
		return DEFAULT_CASUAL_OPTIONS;
	}

	public static GameConfiguration defaultCompetitiveOptions() {
		return DEFAULT_COMPETITIVE_OPTIONS;
	}
	public static GameConfiguration valueOf(Builder builder) {
		return builder.build();
	}
	
	private  GameConfiguration(Builder builder) {
		this.gameMode = builder.gameMode;
		this.numberOfRounds = builder.numberOfRounds;
		this.baseLives = builder.baseLives;
		this.saveFileName = builder.saveFileName;
		this.answerLookupAuto = builder.answerLookupAuto;
		this.turnTimeLimit = builder.turnTimeLimit;
		this.turnTimeDuration = builder.turnTimeDuration;
		 
	 }
	
	public GameMode getGameMode() {
		return gameMode;
	}
	
	public int getNumberOfRounds() {
		return numberOfRounds;
	}
	
	public int getBaseLives() {
		return baseLives;
	}
	
	public String getSaveFileName() {
		return saveFileName;
	}
	
	public boolean getAnswerLookupAuto() {
		return answerLookupAuto;
	}
	
	public boolean getTurnTimeLimit() {
		return turnTimeLimit;
	}
	
	public Duration getTurnTimeDuration() {
		return turnTimeDuration;
	}
}