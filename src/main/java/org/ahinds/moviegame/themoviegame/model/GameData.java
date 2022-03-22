package org.ahinds.moviegame.themoviegame.model;

import java.util.List;
import java.util.ListIterator;

import org.ahinds.moviegame.themoviegame.model.player.PlayerImpl;
import org.ahinds.moviegame.themoviegame.model.player.commands.PlayerCommand;

/* GameData.java
 * 
 * POJO collection to store game data; Uses Java Builder pattern for construction. 
 * 
 * Note: playersIterator and players are not used in current implementation, they are instance fields of Game.java
 * 
 * FUTURE WORK: 
 * 		- potentially include GameObjects as another instance field 
 * 		- add game commands
 *  	- optimize data structures
 *  		- array deques for some collections for undo implementation. 
 *  		- maps for some collections for quicker lookups
 *  
 */
public class GameData {
	
	private boolean finished;
	private ListIterator<PlayerImpl> playersIterator;
	private List<PlayerImpl> players;
	private List<Round> rounds; // rounds contain answers & answer associations
	private List<PlayerCommand> commands;
	private List<PlayerImpl> results;
	
	public static class Builder {
		private boolean finished;
		private List<Round> rounds;
		private List<PlayerCommand> commands;
		private List<PlayerImpl> players;
		private ListIterator<PlayerImpl> playersIterator;
		private List<PlayerImpl> results;
		
		public Builder finished(Boolean finished) {
			this.finished = finished;
			return this;
		}
		
		public Builder rounds(List<Round> rounds) {
			this.rounds = rounds;
			return this;
		}
	
		public Builder commands(List<PlayerCommand> commands) {
			this.commands = commands;
			return this;
		}
		
		public Builder players(List<PlayerImpl> players) {
			this.players = players;
			this.playersIterator = this.players.listIterator();
			return this;
		}
		
		public Builder results (List<PlayerImpl> results) {
			this.results = results;
			return this;
		}
		
		public Builder playerIterator(ListIterator<PlayerImpl> playerIterator) {
			this.playersIterator = playerIterator;
			return this;
		}
		
		private GameData build() {
			return new GameData(this);
		}
	}
	
	private GameData(Builder builder) {
		this.finished = builder.finished;
		this.rounds = builder.rounds;
		this.commands = builder.commands;
		this.players = builder.players;
		this.playersIterator = builder.playersIterator;
		this.results = builder.results;
	}

	public static GameData valueOf(Builder dataBuilder) {
		return dataBuilder.build();
	}

	public	Boolean getFinished() {
		return finished;
	}
		
	public	List<Round> getRounds() {
		return rounds;
	}
		
	public	List<PlayerCommand> getCommands() {
		return commands;
	}

	public List<PlayerImpl> getPlayers() {
		return players;
	}

	public List<PlayerImpl> getResults() {
		return results;
	}
	
	public void setFinished(boolean finished) {
		setFinishedImpl(finished);
	}

	private void setFinishedImpl(boolean finished) {
		this.finished = finished;
	}

	public ListIterator<PlayerImpl> getPlayersIterator() {
		return playersIterator;
	}	
}