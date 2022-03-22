package org.ahinds.moviegame.themoviegame.model.player.commands;

import org.ahinds.moviegame.themoviegame.model.player.PlayerImpl;

/* PlayerCommandBase.java
 * 
 * Base Implementation of Player Commands to inherit from. 
 * Player Command contract enforced on subclasses via IllegalStateException.
 * 
 */
public class PlayerCommandBase extends AbstractPlayerCommand {
	
	@Override
	public void execute(PlayerImpl player) {
		executeImpl(player);
	}
 
	// subclass implementation 
	protected void executeImpl(PlayerImpl player) {
		throw new IllegalStateException("method must be implemented by subclass");
	}

	@Override
	public void undo() {
		undoImpl();	
	}
	
	// subclass implementation 
	protected void undoImpl() {
		throw new IllegalStateException("method must be implemented by subclass");
	}
}