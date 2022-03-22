package org.ahinds.moviegame.themoviegame.views.viewmodels;

import org.ahinds.moviegame.themoviegame.model.player.CompetitivePlayer;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/* CompPlayerViewModel.java
 * 
 * ViewModel for CompetitivePlayer entity.
 * 
 */
public class CompPlayerViewModel extends AbstractViewModel<CompetitivePlayer> {

	private StringProperty name = new SimpleStringProperty();
	private IntegerProperty baseLives = new SimpleIntegerProperty();
	private IntegerProperty extraLives = new SimpleIntegerProperty();
	private IntegerProperty totalLives = new SimpleIntegerProperty();
	private IntegerProperty livesRemaining = new SimpleIntegerProperty();
	
	public CompPlayerViewModel(CompetitivePlayer player) {
		init(player);
	}
	
	// VIEW MODEL CONTRACT
	@Override
	public void init(CompetitivePlayer model) {
		update(model);
	}
	
	@Override
	public void update(CompetitivePlayer model) {
		setModel(model);
		reloadFromModel();
	}
	
	@Override
	public void updateModelRefs(CompetitivePlayer model) {
		return;
	}
	
	@Override
	public void setModel(CompetitivePlayer model) {
		this.model = model;
	}
	
	@Override 
	public void reloadFromModel() {
		name.set(model.getName());
		baseLives.set(model.getBaseLives());
		extraLives.set(model.getExtraLives());
		totalLives.set(model.getTotalLives());
	}
	
	@Override
	public void rollback() {
		return;
	}
	
	@Override
	public void save() {
		return;
	}
	
	// PROPERTIES AND WRAPPED VALUES
	public IntegerProperty baseLivesProperty() {
		return baseLives;
	}
	
	public IntegerProperty extraLivesProperty() {
		return extraLives;
	}

	public IntegerProperty totalLivesProperty() {
		return totalLives;
	}
	
	public int getExtraLives() {
		return extraLives.get();
	}
	
	public int getBaseLives() {
		return baseLives.get();
	}
	
	public int getTotalLives() {
		return totalLives.get();
	}
	
	public int getLivesRemaining() {
		return livesRemaining.get();
	}
}