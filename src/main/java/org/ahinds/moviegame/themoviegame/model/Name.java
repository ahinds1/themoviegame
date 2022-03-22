package org.ahinds.moviegame.themoviegame.model;

import org.ahinds.moviegame.themoviegame.model.movieentity.EntityType;

/* Name.java
 * 
 * The name of an movie entity entered by a player, paired with its type (Actor/Movie). 
 * 
 * Created from player.submit() action. Validated by NameValidator via TMDB API.
 */

 public final class Name implements Validatable {
	private String name;
	private EntityType type;
	
	public Name(String name, EntityType entityType) {
		this.name = name;
		this.type = entityType;
	}
	
	public static Name from(String playerInput, EntityType entityType) {
		return new Name(playerInput, entityType);
	}
	
	public String getName() {
		return name;
	}
	 
	public EntityType getType() {
		return type;
	}

	@Override 
	public String toString() {
		return name;
	}
 }