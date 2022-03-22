package org.ahinds.moviegame.themoviegame.model;

import java.util.List;

import org.ahinds.moviegame.themoviegame.model.movieentity.EntityType;
import org.ahinds.moviegame.themoviegame.model.player.PlayerImpl;
import org.ahinds.moviegame.themoviegame.model.player.commands.ActionType;

/* GameObjects.java
 * 
 * Collection POJO, part of GameState.java (underlying model for GameViewModel). 
 * 
 * Stores relative/current game objects of importance to execute game.  Uses Java Builder pattern. 
 * 
 */
public class GameObjects {
	private Answer prevAnswer;
	private Answer prev2Answer;
	private EntityType currentEntityType;
	private PlayerImpl currentPlayer;
	private PlayerImpl prevPlayer;
	private Round currentRound;
	private ActionType prevActionType;
	private List<PlayerImpl> players;
	
	public static class Builder {
		private Answer prevAnswer;
		private Answer prev2Answer;
		private EntityType currentEntityType;
		private PlayerImpl currentPlayer;
		private PlayerImpl prevPlayer;
		private Round currentRound;
		private ActionType prevActionType;
		private List<PlayerImpl> players;
	
		public Builder prevAnswer(Answer prevAnswer) {
			this.prevAnswer = prevAnswer;
			return this;
		}
		
		public Builder prev2Answer(Answer prev2Answer) {
			this.prev2Answer = prev2Answer;
			return this;
		}
		
		public Builder currentEntityType(EntityType entityType) {
			this.currentEntityType = entityType;
			return this;
		}
		
		public Builder currentPlayer(PlayerImpl currentPlayer) {
			this.currentPlayer = currentPlayer;
			return this;
		}
		
		public Builder prevPlayer(PlayerImpl prevPlayer) {
			this.prevPlayer = prevPlayer;
			return this;
		}
		
		public Builder currentRound(Round currentRound) {
			this.currentRound = currentRound;
			return this;
		}
		
		public Builder prevActionType(ActionType actionType)  {
			this.prevActionType = actionType;
			return this;
		}
		
		public Builder players(List<PlayerImpl> players) {
			this.players = players;
			return this;
		}
	
		public GameObjects build() {
			return new GameObjects(this);
		}
	}
	
	public GameObjects(Builder builder) {
		this.prevAnswer = builder.prevAnswer;
		this.prev2Answer = builder.prev2Answer;
		this.currentEntityType = builder.currentEntityType;
		this.currentRound = builder.currentRound;
		this.currentPlayer = builder.currentPlayer;
		this.prevPlayer = builder.prevPlayer;
		this.prevActionType = builder.prevActionType;
		this.players = builder.players;
	}

	public Answer getPrevAnswer() {
		return prevAnswer;
	}
	
	public Answer getPrev2Answer() {
		return prev2Answer;
	}
	
	public EntityType getCurrentNameType() {
		return currentEntityType;
	}
	
	public PlayerImpl getCurrentPlayer() {
		return currentPlayer;
	}
	
	public Round getCurrentRound() {
		return currentRound;
	}	
	
	public PlayerImpl getPrevPlayer() {
		return prevPlayer;
	}

	public ActionType getPrevActionType() {
		return prevActionType;
	}

	public List<PlayerImpl> getPlayers() {
		return players;
	}
}