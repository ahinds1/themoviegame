package org.ahinds.moviegame.themoviegame.model.player;

/* Player.java
 * 
 * Player contract, set of actions a player should be able to take during a game.
 * 
 */
public interface Player {
	// challenge the previous player to answer with a name of their own. 
	void challenge();
	
	// reject name played by first player. only allowed for second turn of the round.
	void reject();
	
	// submit name to be potentially used as their answer.
	void submit(String playerInput);
}