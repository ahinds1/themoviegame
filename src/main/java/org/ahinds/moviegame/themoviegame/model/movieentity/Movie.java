package org.ahinds.moviegame.themoviegame.model.movieentity;

/* Movie.java
 * 
 * Movie entity (NOT USED IN CURRENT IMPLEMENTATION)
 * 
 * FUTURE WORK: 
 * 		- implement in game
 * 
 */
public final class Movie extends MovieEntityBase {
	public Movie(String name, EntityType type, int lookupId) {
		super(name, type, lookupId);
	}

	public static Movie from(String name, EntityType type, int lookupId) {
		return new Movie(name, type, lookupId);
	}
}