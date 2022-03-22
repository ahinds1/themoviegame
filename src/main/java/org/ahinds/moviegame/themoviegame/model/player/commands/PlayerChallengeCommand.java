package org.ahinds.moviegame.themoviegame.model.player.commands;

import java.util.Objects;

import org.ahinds.moviegame.themoviegame.model.player.PlayerImpl;

/* PlayerChallengeCommand.java
 * 
 * Command representing player.challenge().
 * 
 */
public final class PlayerChallengeCommand extends PlayerCommandBase {
	
	public PlayerChallengeCommand() {
		this.type = ActionType.CHALLENGE;
	}
	
	// PLAYERCOMMAND CONTRACT
	@Override
	protected void undoImpl() {
		return;
	}

	@Override
	protected void executeImpl(PlayerImpl player) {
		this.player = Objects.requireNonNull(player);
		this.player.challenge();
	}
	
	
	@Override 
	public String toString() {
		return String.format("%s challenged!", player);
	}
}