package org.ahinds.moviegame.themoviegame.model.player.commands;

import org.ahinds.moviegame.themoviegame.model.player.PlayerImpl;

/* PlayerCommand.java
 * 
 * Player Command Contract. Player is passed in at execution.
 * 
 * FUTURE WORK: 
 * 		- potentially split out undo() and extend this with a new interface (UndoableCommand)
 *
 */
public interface PlayerCommand {	
	void execute(PlayerImpl player);
	void undo();
}