package org.ahinds.moviegame.themoviegame.model.player.commands;

import org.ahinds.moviegame.themoviegame.model.player.PlayerImpl;

/* PlayerSubmitCommand.java
 * 
 * Command representing player.submit() action.
 * 
 */
public final class PlayerSubmitCommand extends PlayerCommandBase{
	private String name;
	
	public PlayerSubmitCommand(String playerInput) {
		this.name = playerInput;
		type = ActionType.SUBMIT;
	}

	//PLAYERCOMMAND CONTRACT
	
	@Override
	protected void executeImpl(PlayerImpl player) {
		this.player = player;
		this.player.submit(name);
	}
	@Override
	protected void undoImpl() {
		player.getGame().rollback();
	}
	
	@Override
	public String toString() {
		return String.format("%s submitted '%s'", getPlayer(), name);
	}
}