package org.ahinds.moviegame.themoviegame.model.player.commands;

/* ActionType.java
 * 
 * Enum representing player actions. A tag for player commands.
 * 
 */
public enum ActionType {
	CHALLENGE("CHALLENGE"),
	SUBMIT("SUBMIT"), 
	REJECT("REJECT"),
	NONE("NONE");

	private String type;
	
	ActionType(String string) {
		type = string;
	}
	
	@Override
	public String toString() {
		return type;
	}
	
	public static ActionType from(String s) {
		for (ActionType type : ActionType.values()) {
			if (s.equals(type.toString())) {
				return type;
			}
		}
		throw new IllegalArgumentException("No action of type: " + s);
	}
}