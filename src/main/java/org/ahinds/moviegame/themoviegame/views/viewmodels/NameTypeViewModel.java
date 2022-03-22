package org.ahinds.moviegame.themoviegame.views.viewmodels;

import java.util.Objects;

import org.ahinds.moviegame.themoviegame.model.movieentity.EntityType;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class NameTypeViewModel {
	StringProperty nameType = new SimpleStringProperty();
	
	public NameTypeViewModel(EntityType nameType) {
		this.nameType.set(Objects.requireNonNull(nameType).name());
	}

	public void update(StringProperty nameType) {
		this.nameType.set(nameType.getValue());
	}	
	
	StringProperty nameTypeProperty() {
		return nameType;
	}
	
	String getNameType() {
		return nameType.get();
	}

	public static NameTypeViewModel from(EntityType nameType) {
		return new NameTypeViewModel(nameType);
	}
}
