package org.ahinds.moviegame.themoviegame.views.viewmodels;

import java.util.Objects;

import org.ahinds.moviegame.themoviegame.model.Turn;
import org.ahinds.moviegame.themoviegame.model.player.commands.PlayerCommandBase;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

/* TurnViewModel.java
 * 
 * (NOT USED IN CURRENT IMPLEMENTATION)
 * 
 * View Model for Turn object.
 * 
 */
 class TurnViewModel {
	private IntegerProperty id = new SimpleIntegerProperty();
	private ObjectProperty<PlayerCommandBase> playerCommand = new SimpleObjectProperty<>();
	private ObjectProperty<GameViewModel> state = new SimpleObjectProperty<>();
	
	public void update(Turn turnModel) {
		final Turn turn = Objects.requireNonNull(turnModel);
		this.id.set(turn.getId());
		this.playerCommand.set(turn.getPlayerCommand());
		this.state.set(GameViewModel.from(turn.getState()));
	}
	
	public ObjectProperty<PlayerCommandBase> playerCommandProperty() {
		return playerCommand;
	}
}