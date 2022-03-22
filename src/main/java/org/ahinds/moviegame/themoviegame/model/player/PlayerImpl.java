package org.ahinds.moviegame.themoviegame.model.player;

import java.util.Objects;

import org.ahinds.moviegame.themoviegame.model.Game;

/* PlayerImpl.java 
 * 
 * 	Player entity within a game, different from PlayerProfile;
 *
 * 	A player takes action during their turn.
 *
 * 
 * FUTURE WORK: 
 * 		- convert to abstract class and use concrete casual/competitive players
 *
 */
public class PlayerImpl implements Player, Comparable<PlayerImpl> {

	private String name;
	private Game game;
	
	public PlayerImpl(String playerName, Game gameInstance) {
		this.name = playerName;
		this.game = gameInstance;
	}

	// PUBLIC CONTRACT
	@Override 
	public void submit(String playerInput) {
		game.submit(playerInput);
	}

	@Override 
	public void reject() {		
		game.reject();
	}
	
	@Override
	public void challenge() {
		game.challenge();
	}
	
	@Override
	public String toString() {
		return name;
	}

	@Override
	public int compareTo(PlayerImpl o) {
		return this.getName().compareTo(Objects.requireNonNull(o).getName());
	}

	// GETTER AND SETTERS
	public String getName() {
		return name;
	}
	
	public Game getGame() {
		return game;
	}
}