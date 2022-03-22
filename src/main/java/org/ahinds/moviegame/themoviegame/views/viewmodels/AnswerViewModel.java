package org.ahinds.moviegame.themoviegame.views.viewmodels;

import java.util.Objects;

import org.ahinds.moviegame.themoviegame.model.Answer;
import org.ahinds.moviegame.themoviegame.model.movieentity.EntityType;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

/* AnswerViewModel.java
 * 
 * ViewModel for Answer object. 
 * 
 *  FUTURE WORK:
 *  	- Implement entire view model contract
 * 
 */
public class AnswerViewModel extends AbstractViewModel<Answer> {
	
	private IntegerProperty id = new SimpleIntegerProperty();
	private ObjectProperty<EntityType> entityType = new SimpleObjectProperty<>();
	private StringProperty playerInput = new SimpleStringProperty();
	private StringProperty nameFromLookup = new SimpleStringProperty();
	private ObjectProperty<Image> answerImage = new SimpleObjectProperty<>();
	private StringProperty player = new SimpleStringProperty();
	
	public static AnswerViewModel from(Answer currentAnswer) {
		if (Objects.nonNull(currentAnswer)) {
			return new AnswerViewModel(currentAnswer);			
		}
		return new AnswerViewModel();
	}
	
	private AnswerViewModel(Answer model) {
		init(model);
	}
	
	private AnswerViewModel() {
		
	}
	
	// VIEW MODEL CONTRACT
	@Override
	public void init(Answer model) {
		update(model);
	}

	@Override
	public void update(Answer model) {
		setModel(model);
		reloadFromModel();
	}
	
	@Override
	public void updateModelRefs(Answer model) {
		return;
	}

	@Override
	public void setModel(Answer model) {
		this.model = model;
	}

	@Override
	public void reloadFromModel() {
		id.set(model.getDbId());
		entityType.set(model.getEntityType());
		nameFromLookup.set(model.getNameFromTmdb());
		answerImage.set(model.getAnswerImage());
		player.set(model.getPlayer());
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
	public IntegerProperty idProperty() {
		return id;
	}
	
	public int getId( ) {
		return id.getValue();
	}
	
	public StringProperty playerInputProperty() {
		return playerInput;
	}
	
	public String getPlayerInput() {
		return playerInput.getValue();
	}
	
	public StringProperty nameFromLookupProperty() {
		return nameFromLookup;
	}
	
	public String getNameFromLookup() {
		return nameFromLookup.getValue();
	}
	
	public ObjectProperty<Image> imageProperty() {
		return answerImage;
	}
	
	public Image getImage() {
		return answerImage.getValue();
	}

	public ObjectProperty<EntityType> nameTypeProperty() {
		return entityType;
	}
	
	public EntityType getNameType() {
		return entityType.getValue();
	}

	public StringProperty playerProperty() {
		return player;
	}
	
	public String getPlayer() {
		return player.getValue();
	}
}