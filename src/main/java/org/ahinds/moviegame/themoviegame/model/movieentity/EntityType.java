package org.ahinds.moviegame.themoviegame.model.movieentity;

/* EntityType.java
 * 
 * The movie entities named during a game. May be used in potential sorting.
 * 
 */
public enum EntityType {
	MOVIE("MOVIE"),
	ACTOR("ACTOR"),
	NONE("NONE");
	
	private String type;
	
	EntityType(String answerType) {
		type = answerType;
	}
	
	@Override
	public String toString() {
		return type;
	}
}