package org.ahinds.moviegame.themoviegame.model;

import java.util.Objects;

import org.ahinds.moviegame.themoviegame.model.player.commands.PlayerChallengeCommand;
import org.ahinds.moviegame.themoviegame.model.player.commands.PlayerCommandBase;


/* Turn.java 
 * 
 * (NOT USED IN CURRENT IMPLEMENTATION)
 * 
 * A turn encapsulates a player's action and associated change in game state. 
 * 
 * In a competitive game (currently needs refactoring), turns may have time limits.
 * 
 * @author Alex
 *
 */
 public final class Turn {
	private int id;
	private PlayerCommandBase playerCommand;
	private GameState state;

	private Turn(PlayerCommandBase command) {
		playerCommand = Objects.requireNonNull(command);
	}

	Turn(Turn turn) {
		this.id = Objects.requireNonNull(turn).getId();
		this.playerCommand = Objects.requireNonNull(turn.getPlayerCommand());
		this.state = Objects.requireNonNull(turn.getState());
	}
	
	public static Turn from(PlayerCommandBase command) {
		return new Turn(command);
	}
	
	public static Turn from(Turn turn) {
		return new Turn(turn);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public static boolean isChallenge(Turn turn) {
		return turn.getPlayerCommand() instanceof PlayerChallengeCommand;
	}

	public PlayerCommandBase getPlayerCommand() {
		return playerCommand;
	}
	
	public GameState getState() {
		return state;
	}
	
	@Override
	public String toString() {
		return playerCommand.toString();
	}
}