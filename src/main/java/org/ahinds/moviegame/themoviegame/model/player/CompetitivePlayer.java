package org.ahinds.moviegame.themoviegame.model.player;

import org.ahinds.moviegame.themoviegame.model.Game;

/* CompetitivePlayer.java
 * 
 * (NOT USED IN CURRENT IMPLEMENTATION)
 * 
 * Player in a competitive game. Lives used to determine ranking/result in a competitive game.
 * 
 * FUTURE WORK:
 * 		- add status/state enum for when player is eliminated
 *
 */
 public final class CompetitivePlayer extends PlayerImpl {
	private int baseLives;  // specified in game options
	private int extraLives; // to be specified when adding a player (not implemented)
	private int totalLives;
	
	public CompetitivePlayer(String playerName, Game game, int baseLives, int extraLives) {
		super(playerName, game);

		this.baseLives = baseLives;
		this.extraLives = extraLives;
		this.totalLives = baseLives + extraLives;
	}
	
	public void loseLife() {
		loseLifeImpl();
	}

	private void loseLifeImpl() {
		totalLives--;
	}

	public Integer getExtraLives() {
		return extraLives;
	}

	public Integer getBaseLives() {
		return baseLives;
	}

	public Integer getTotalLives() {
		return totalLives;
	}
}