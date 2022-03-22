package org.ahinds.moviegame.themoviegame.views.viewmodels;

import java.util.Objects;

import org.ahinds.moviegame.themoviegame.model.player.PlayerImpl;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/* PlayerViewModel.java
 * 
 * ViewModel for PlayerImpl entity.
 * 
 * FUTURE WORK
 * 	- Implement all view model methods
 * 	- remove and after PlayerImpl is converted to an abstract class.
 * 
 */
public class PlayerViewModel extends AbstractViewModel<PlayerImpl>{
	private StringProperty name = new SimpleStringProperty();	

	private PlayerViewModel(PlayerImpl model) {
		init(Objects.requireNonNull(model));
	}

	// VIEW MODEL CONTRACT
	@Override
	public void update(PlayerImpl model) {
		setModel(model);
		reloadFromModel();
	}
	
	@Override
	public void updateModelRefs(PlayerImpl model) {
		return;
	}
	
	@Override
	public void init(PlayerImpl model) {
		update(model);
	}
	
	@Override
	public void setModel(PlayerImpl model) {
		this.model = model;
	}
	
	@Override
	public void reloadFromModel() {
		name.set(model.getName());
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
	public StringProperty nameProperty() {
		return name;
	}
	
	public String getName() {
		return name.getValue();
	}
	
	public static PlayerViewModel from(PlayerImpl player) {
		return new PlayerViewModel(player);		
	}
}