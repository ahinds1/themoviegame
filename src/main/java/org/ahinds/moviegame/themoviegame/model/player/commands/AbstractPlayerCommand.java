package org.ahinds.moviegame.themoviegame.model.player.commands;

import org.ahinds.moviegame.themoviegame.model.player.PlayerImpl;

/* AbstractPlayerCommand.java
 * 
 * Blueprint for Player Commands. Player passed in at time of command execution.
 * 
 * 
 */
public abstract class AbstractPlayerCommand implements PlayerCommand {
	protected ActionType type; // tag to avoid calling .getClass();
	protected PlayerImpl player;
		
	public ActionType getType() {
		return type;
	}
	
	public String getPlayer() {
		return player.getName();
	}
}