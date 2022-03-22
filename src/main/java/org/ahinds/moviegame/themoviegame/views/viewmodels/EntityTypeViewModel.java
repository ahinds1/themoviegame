package org.ahinds.moviegame.themoviegame.views.viewmodels;

import java.util.Objects;

import org.ahinds.moviegame.themoviegame.model.movieentity.EntityType;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/* EntityTypeViewModel.java
 * 
 * ViewModel for EntityType enum
 * 
 * FUTURE WORK: 
 * 		- potentially remove and simply store it as an ObjectProperty<EntityType>
 * 		- OR implement all view model contracts
 */
public class EntityTypeViewModel extends AbstractViewModel<EntityType>{
	StringProperty entityType = new SimpleStringProperty();
	
	public EntityTypeViewModel(EntityType entityType) {
		this.entityType.set(Objects.requireNonNull(entityType).name());
	}
	
	public static EntityTypeViewModel from(EntityType entityType) {
		return new EntityTypeViewModel(entityType);
	}
	
	// VIEW MODEL CONTRACT
	@Override
	public void init(EntityType model) {
		update(model);	
	}
	
	@Override
	public void update(EntityType model) {
		setModel(model);
		reloadFromModel();
	}
	
	@Override
	public void updateModelRefs(EntityType model) {
		return;
	}

	@Override
	public void rollback() {
		return;
	}

	@Override
	public void setModel(EntityType model) {
		this.model = model;
	}

	@Override
	public void reloadFromModel() {
		entityType.set(model.toString());
	}

	@Override
	public void save() {
		return;
	}	
	
	// PROPERTIES AND WRAPPED VALUES
	StringProperty nameTypeProperty() {
		return entityType;
	}
	
	String getNameType() {
		return entityType.get();
	}
}