package org.ahinds.moviegame.themoviegame.model;

/* GameState.java
 * 
 * Collection for all game related data. Used as the 
 * underlying data model for GameViewModel. 
 * 
 * Includes current game state objects that are used to update the UI 
 * as well the general data store (GameData). 
 * 
 * GameData currently is not fully used but included to allow 
 * for initializing future view models;
 * 
 */
public class GameState {
	private GameObjects gameObjects;
	private GameData gameData;
	
	public GameObjects getGameObjects() {
		return gameObjects;
	}
	public GameData getGameData() {
		return gameData;
	}
	
	public static GameState newState(GameObjects objects, GameData data) {
		return new GameState(objects, data);
	}
	
	private GameState(GameObjects objects, GameData data) {
		this.gameObjects = objects;
		this.gameData = data;
	}
}