package org.ahinds.moviegame.themoviegame.views.viewmodels;

import org.ahinds.moviegame.themoviegame.model.GameMode;
import org.ahinds.moviegame.themoviegame.model.GameConfiguration.Builder;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/* GameOptionsBuilderViewModel.java
 * 
 * ViewModel for GameOptions.Builder. Used in New Game screen / associated controller.
 * 
 * FUTURE WORK: 
 * 		- shorter name?
 */
public class GameOptionsBuilderViewModel extends AbstractViewModel<Builder>{
	
	private  ObjectProperty<GameMode> gameMode = new SimpleObjectProperty<>();
	private  IntegerProperty numberOfRounds = new SimpleIntegerProperty();
	private  IntegerProperty baseLives = new SimpleIntegerProperty();
	private  StringProperty  saveFileName = new SimpleStringProperty();
	private  BooleanProperty answerLookupAuto = new SimpleBooleanProperty();
	private  BooleanProperty turnTimeLimit = new SimpleBooleanProperty();
	private  IntegerProperty turnTimeDuration = new SimpleIntegerProperty();
	
	// VIEW MODEL CONTRACT
	@Override
	public void init(Builder builderModel) {
		update(builderModel);
	}
	
	@Override
	public void update(Builder model) {
		setModel(model);
		reloadFromModel();
	}

	@Override
	public void updateModelRefs(Builder model) {
		return;
	}
	
	@Override
	public void rollback() {
		return;	
	}

	@Override
	public void setModel(Builder model) {
		this.model = model;	
	}

	@Override
	public void reloadFromModel() {
		gameMode.set(model.getGameMode());
		numberOfRounds.set(model.getNumberOfRounds());
		baseLives.set(model.getBaseLives());
		saveFileName.set(model.getSaveFileName());
		answerLookupAuto.set(model.getAnswerLookUpAuto());
		turnTimeLimit.set(model.getTurnTimeLimit());
		turnTimeDuration.set((int) model.getTurnTimeDuration().getSeconds());
	}
	
	@Override
	public void save() {
		model.gameMode(gameMode.getValue());
		model.numberOfRounds(numberOfRounds.getValue());
		model.baseLives(baseLives.getValue());
		model.saveFileName(saveFileName.getValue());
		model.answerLookupAuto(answerLookupAuto.getValue());
		model.turnTimeLimit(turnTimeLimit.getValue());
		model.turnTimeDuration(turnTimeDuration.getValue());
	}
	
	// PROPERTIES AND WRAPPED VALUES
	public ObjectProperty<GameMode> gameModeProperty() {
		return gameMode;
	}
	
	public IntegerProperty numberOfRoundsProperty() {
		return numberOfRounds;
	}
	
	public IntegerProperty baseLivesProperty() {
		return baseLives;
	}
	
	public StringProperty saveFileNameProperty() {
		return saveFileName;
	}
	
	public BooleanProperty answerLookupProperty() {
		return answerLookupAuto;
	}
	
	public BooleanProperty timeLimitProperty() {
		return turnTimeLimit;
	}
	
	public IntegerProperty timeLimitDurationProperty() {
		return turnTimeDuration;
	}
	
	public GameMode getGameMode() {
		return gameMode.getValue();
	}
	
	public int getNumberOfRounds() {
		return numberOfRounds.getValue();
	}
	
	public int getBaseLives() {
		return baseLives.getValue();
	}
	
	public String getSaveFileName() {
		return saveFileName.getValue();
	}
	
	public boolean getAnswerLookup() {
		return answerLookupAuto.getValue();
	}
	
	public boolean getTimeLimit() {
		return turnTimeLimit.getValue();
	}
	
	public int getTimeDuration() {
		return turnTimeDuration.getValue();
	}
	
	public Builder getModel() {
		return model;
	}
}