package org.ahinds.moviegame.themoviegame.model.util;

import java.util.Objects;

import org.ahinds.moviegame.themoviegame.model.Name;
import org.ahinds.moviegame.themoviegame.model.player.PlayerImpl;
import org.javatuples.Pair;

/* MessageGenerator.java
 * 
 * Utility class responsible for generating messages to send to game controller.
 * 
 */
public class MessageGenerator {
	
	// Name Validation Messages
	public static String nameNotFoundMessage(Name name) {
		return String.format(
				"%s was not found. Did you name a movie instead of an actor (or vice versa)? " + 
				"Try again or ask others for spelling.", 
				
				Objects.requireNonNull(name)
		);
	}
	
	public static String nameAlreadyAssociatedMessage(String currentName, String prevName) {
		return String.format(
				"%s - %s already used this round. Try another name.", 
				
				Objects.requireNonNull(currentName), 
				Objects.requireNonNull(prevName)
		);
	}
	
	// Answer Evaluation Messages
	public static String incorrectAnswerMessage(Pair<String, String> association, PlayerImpl currentPlayer, boolean finished) {
		Objects.requireNonNull(association);
		
		return String.format(
				"%s is incorrect for previous answer (%s). \n\n" + 
						(finished ? "End of final round..."
								 : "Starting new round with %s..."), 
				
				association.getValue1(), 
				association.getValue0(),
				currentPlayer.getName()
		);
	}

	// Challenge Status Messages
	public static String challengeUnsuccessfulMessage(
		PlayerImpl prevPlayer, PlayerImpl currentPlayer,
		Pair<String, String> lastAssociation, boolean finished) {
		
		return String.format(
				"%1$s's Challenge:\tUnsuccessful\n\n"  + 
				"%2$s correctly played '%3$s' for '%4$s'.\n\n" +
				(finished ? "End of final round..."
						 : "Starting new round with %1$s..."),
				
				Objects.requireNonNull(prevPlayer).getName(),
				Objects.requireNonNull(currentPlayer).getName(),
				Objects.requireNonNull(lastAssociation).getValue1(),
				Objects.requireNonNull(lastAssociation).getValue0()
		);
	}
	
	public static String challengeSuccessfulMessage(
		PlayerImpl prevPlayer, PlayerImpl currentPlayer,
		Pair<String, String> lastAssociation, boolean finished) {

		return String.format(
				"%1$s's challenge:\tSuccessful\n\n" + 
				"%3$s incorrectly played '%4$s' for '%2$s'.\n\n" + 
						(finished ? "End of final round..."
										 : "Starting new round with %3$s..."),
				
				prevPlayer.getName(),
				lastAssociation.getValue0(), 
				currentPlayer.getName(),
				lastAssociation.getValue1()
		);
	}
}