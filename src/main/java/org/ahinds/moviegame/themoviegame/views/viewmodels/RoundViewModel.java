package org.ahinds.moviegame.themoviegame.views.viewmodels;


import java.util.List;
import java.util.Objects;

import org.ahinds.moviegame.themoviegame.model.Answer;
import org.ahinds.moviegame.themoviegame.model.Round;
import org.javatuples.Pair;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/* RoundViewModel.java
 * 
 * ViewModel for Round entity;
 * 
 * FUTURE WORK:
 * 	- Implement all view model methods
 */
 public class RoundViewModel extends AbstractViewModel<Round>{
	
	private IntegerProperty seqId = new SimpleIntegerProperty(); // for natural ordering
	private ListProperty<AnswerViewModel> answers = new SimpleListProperty<>(FXCollections.observableArrayList());
	private IntegerProperty answerCount = new SimpleIntegerProperty();
	private ListProperty<Pair<String, String>> answerAssociations = new SimpleListProperty<>(FXCollections.observableArrayList());
	
	public RoundViewModel() {
	
	}
	
	public RoundViewModel(Round model) {
		init(Objects.requireNonNull(model));
	}
	
	public static RoundViewModel from(Round currentRound) {
		return new RoundViewModel(currentRound);
	}
	
	// VIEW MODEL CONTRACT
	@Override
	public void init(Round model) {
		update(model);
	}
	
	@Override
	public void update(Round model) {
		setModel(model);
		reloadFromModel();
	}
	
	@Override
	public void updateModelRefs(Round model) {
		return;
	}
	
	@Override
	public void setModel(Round model) {
		this.model = model;
	}
	
	@Override
	public void reloadFromModel() {
		
		final ObservableList<AnswerViewModel> viewModels = FXCollections.observableArrayList();
		
		this.seqId.set(model.getRoundId());
		this.answerCount.set(model.answerCountAsInt());
		
		if (model.answerCountAsInt() > 0) {
			for (Answer answer : model.getAnswers()) {
				viewModels.add(AnswerViewModel.from(answer));
			}
			this.answers.set(viewModels);
		}
		
		if (model.answerCountAsInt() > 1) {
			this.answerAssociations.set(FXCollections.observableArrayList(model.getAnswerAssociations()));
		}
	}
	
	@Override
	public void rollback() {
		return;
	}
	
	@Override
	public void save() {
		return;
	}
	
	@Override
	public String toString() {
		return "Round " + seqId.get();
	}
	
	// PROPERTIES AND WRAPPED VALUES
	public IntegerProperty seqIdProperty() {
		return seqId;
	}
	
	public ListProperty<Pair<String, String>> associationsProperty() {
		return answerAssociations;
	}
	
	public int getSeqId() {
		return this.seqId.get();
	}
	
	public List<AnswerViewModel> getAnswers() {
		return answers.get();
	}
	
	public IntegerProperty answerCountProperty() {
		return answerCount;
	}
	
	public int getAnswerCount() {
		return answerCount.get();
	}
	
	public List<Pair<String, String>> getAssociations() {
		return answerAssociations.get();
	}
}