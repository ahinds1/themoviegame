package org.ahinds.moviegame.themoviegame.model.movieentity;

/* AbstractMovieEntity
 * 
 * Blueprint for movie entity domain objects (Movie, Actor). 
 * 
 * (NOT USED IN CURRENT IMPLEMENTATION)
 * 
 * Includes id for data look up via TMDB.
 * 
 */
public abstract class AbstractMovieEntity {
	
	private String name;
	private EntityType type;
	private int id; // id for api lookup
	
	public AbstractMovieEntity(String name, EntityType type, int apiId) {
		this.name = name;
		this.type = type;
		this.id = apiId;
	}

	public String getName() {
		return name;
	}

	public EntityType getType() {
		return type;
	}
	
	public int getId() {
		return id;
	}	
}