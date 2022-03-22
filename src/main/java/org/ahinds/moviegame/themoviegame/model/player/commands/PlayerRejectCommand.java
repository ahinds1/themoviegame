package org.ahinds.moviegame.themoviegame.model.player.commands;

import org.ahinds.moviegame.themoviegame.model.player.PlayerImpl;

/* PlayerRejectCommand.java
 * 
 * Command representing player.reject() action.
 * 
 */
public final class PlayerRejectCommand extends PlayerCommandBase {
	
	public PlayerRejectCommand() {
		type = ActionType.REJECT;
	}
	
	// PLAYERCOMMAND CONTRACT
	
	@Override
	protected void executeImpl(PlayerImpl player) {
		this.player = player;
		this.player.reject();
	}
	
	@Override
	protected void undoImpl() {
		return;
	}
	
	@Override
	public String toString() {
		return String.format("%s rejected!", player);
	}

}