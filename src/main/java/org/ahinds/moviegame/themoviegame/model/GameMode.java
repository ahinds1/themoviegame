//----------------------------------------------------------
//  File Name
//  	desc
//  
// Author: Alex Hinds
// Date: 
// Issues: 
//  
// Description: 
//
// Assumptions:
//  
//  
//
//-----------------------------------------------------------  
package org.ahinds.moviegame.themoviegame.model;

/* GameMode.java
 * 	 
 * Enum representing the game's two modes. 
 *   
 * Competitive games use players lives to eliminate players and declare a winner / player rankings.
 * Casual games do not and simply iterate through rounds after every incorrect answer
 *   
 * COMPETITIVE GAMES ARE NOT CURRENTLY FUNCTIONAL. Need to continue refactoring.
 * 
 * Asset paths are included to practice using enums, in real game would remove to couple the scene backgrounds to 
 * player profile preferences
 * 
 */
 public enum GameMode {
	CASUAL("Casual", "/assets/images/batman-vs-joker-a1-1920x1080.jpg"), 
	COMPETITIVE("Competitive", "/assets/images/batman-comics-art-hc-1920x1080-flip.jpg");

	private String gameModeType;
	private String gameModeBackgroundPath;
	
	GameMode(String gameMode, String gameModeImagePath) {
		gameModeType = gameMode;
		gameModeBackgroundPath = gameModeImagePath;
	}

	void setGameModeBackgroundImage(String gameModeImage) {
		this.gameModeBackgroundPath = gameModeImage;

	}

	public static GameMode from(String gameMode) {
		if (gameMode.isEmpty() == false) {
			for (GameMode gameModeType : GameMode.values()) {
				if (gameMode.equalsIgnoreCase(gameModeType.getGameModeType())) { 
					return gameModeType;
				} 
			}
		}
		
		throw new IllegalArgumentException(String.format("'%s' is not a valid game mode", gameMode));
	}

	public String getGameModeType() {
		return gameModeType;
	}

	public String getBackgroundPath() {
		return gameModeBackgroundPath;
	}

	@Override
	public String toString() {
		return gameModeType;
	}
}