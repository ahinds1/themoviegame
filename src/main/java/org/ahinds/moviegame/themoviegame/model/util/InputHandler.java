package org.ahinds.moviegame.themoviegame.model.util;

import java.util.Objects;

import org.ahinds.moviegame.themoviegame.model.player.commands.PlayerChallengeCommand;
import org.ahinds.moviegame.themoviegame.model.player.commands.PlayerCommandBase;
import org.ahinds.moviegame.themoviegame.model.player.commands.PlayerRejectCommand;
import org.ahinds.moviegame.themoviegame.model.player.commands.PlayerSubmitCommand;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.InputEvent;

/* InputHandler.java
 * 
 * Utility class. Generates a PlayerCommand from InputEvents passed 
 * from the game (which receives the event from JavaFx App Thread)
 * 
 * FUTURE WORK:
 * 		- Consider implementing Null Command Object
 */
public class InputHandler {

	public static PlayerCommandBase handle(InputEvent event) {
		final Object src = Objects.requireNonNull(event).getSource();
	
		if (src.getClass() == Button.class) {
			final Button srcButton = (Button) src;
			final String id = srcButton.getId();
			
			if (id.equals("rejectButton")) { // reject
				return new PlayerRejectCommand();
			} else if (id.equals("challengeButton")) { // challenge
				return new PlayerChallengeCommand();
			} else {
				throw new IllegalArgumentException("unexpected event source (Button): " + id);
			}
		} else if (src.getClass() == TextField.class) { // submit
			final TextField playerInput = (TextField) src;
			return new PlayerSubmitCommand(playerInput.getText()); 
		} 
			
		return null;
	}
}