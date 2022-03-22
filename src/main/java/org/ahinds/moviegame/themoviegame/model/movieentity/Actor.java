package org.ahinds.moviegame.themoviegame.model.movieentity;

/* Actor.java
 * 
 * Represents a movie actor within the game. 
 * 
 * (NOT USED IN CURRENT IMPLEMENTATION)
 * 
 * FUTURE WORK: 
 * 		- implement in game.
 */
public final class Actor extends MovieEntityBase {
	
	public Actor(String name, EntityType type, int lookupId) {
		super(name, type, lookupId);
	}
	
	public static Actor from(String name, EntityType type, int lookupId) {
		return new Actor(name, type, lookupId);
	}
}