package org.ahinds.moviegame.themoviegame.model.movieentity;

/* BaseMovieEntity.java
 * 
 * Base implementation of movie entities (Actor, Movie) 
 * 
 * (NOT USED IN CURRENT IMPLEMENTATION)
 * 
 */
 class MovieEntityBase extends AbstractMovieEntity {
	
	public MovieEntityBase(String name, EntityType type, int lookupId) {
		super(name, type, lookupId);
	}
	
	public static boolean isActor(MovieEntityBase entity) {
		return entity instanceof Actor;
	}
	
	public static boolean isMovie(MovieEntityBase entity) {
		return entity instanceof Movie;
	}
}