package org.ahinds.moviegame.themoviegame.views.viewmodels;

import org.ahinds.moviegame.themoviegame.model.GameState;
import org.ahinds.moviegame.themoviegame.model.movieentity.EntityType;
import org.ahinds.moviegame.themoviegame.model.player.PlayerImpl;
import org.ahinds.moviegame.themoviegame.model.player.commands.ActionType;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/* GameViewModel.java
 * 
 * Main ViewModel used to display changes in GameState. Used in GameViewController.
 * 
 * FUTURE WORK
 * 		- Implement all view model model contracts
 */
public class GameViewModel extends AbstractViewModel<GameState> {
		
	private BooleanProperty isFinished;
	private ObjectProperty<RoundViewModel> currentRound;
	private IntegerProperty answerCount;
	private ObjectProperty<AnswerViewModel> prevAnswer; // 1 turn from current answer
	private ObjectProperty<AnswerViewModel> prev2Answer;  // 2 turns from current answer
	private ObjectProperty<PlayerViewModel> currentPlayer; 
	private ObjectProperty<PlayerViewModel> prevPlayer; 
	private ObjectProperty<EntityType> currentEntityType;
	private ListProperty<PlayerViewModel> playersList;
	private ObjectProperty<ActionType> prevActionType;
	
	public GameViewModel() {
		prevModel = null;
		isFinished = new SimpleBooleanProperty();
		currentRound = new SimpleObjectProperty<>();
		answerCount = new SimpleIntegerProperty();
		prevAnswer = new SimpleObjectProperty<>();
		prev2Answer = new SimpleObjectProperty<>();
		currentPlayer = new SimpleObjectProperty<>();
		prevPlayer = new SimpleObjectProperty<>();
		currentEntityType = new SimpleObjectProperty<>(EntityType.NONE);
		playersList = new SimpleListProperty<>();
		prevActionType = new SimpleObjectProperty<>();
	}
	
	private GameViewModel(GameState model) {
		init(model);
	}
	
	public static GameViewModel from(GameState gameState) {
		return new GameViewModel(gameState);
	}
	
	// VIEW MODEL CONTRACT
	@Override
	public void update(GameState model) {
		updateModelRefs(model);
		reloadFromModel();
	}

	public void updateModelRefs(GameState gameState) {
		prevModel = model;
		model = gameState;
	}
	
	@Override
	public void rollback() {
		update(prevModel);
		model = prevModel;
		prevModel = null;
	}
	
	@Override
	public void init(GameState model) {
		update(model);	
	}

	@Override
	public void setModel(GameState model) {
		this.model = model;
	}

	@Override
	public void reloadFromModel() {
		isFinished.set(model.getGameData().getFinished());
		currentRound.set(RoundViewModel.from(model.getGameObjects().getCurrentRound()));
		answerCount.set(model.getGameObjects().getCurrentRound().answerCountAsInt());
		prevAnswer.set(AnswerViewModel.from(model.getGameObjects().getPrevAnswer()));
		prev2Answer.set(AnswerViewModel.from(model.getGameObjects().getPrev2Answer()));
		currentPlayer.set(PlayerViewModel.from(model.getGameObjects().getCurrentPlayer()));
		prevPlayer.set(PlayerViewModel.from(model.getGameObjects().getCurrentPlayer()));
		currentEntityType.set(model.getGameObjects().getCurrentNameType());
		prevActionType.set(model.getGameObjects().getPrevActionType());
		
		final ObservableList<PlayerViewModel> playerViewModels = FXCollections.observableArrayList();
		for (PlayerImpl player : model.getGameObjects().getPlayers()) {
				playerViewModels.add(PlayerViewModel.from(player));
		}
		
		playersList.set(playerViewModels);
	}

	@Override
	public void save() {
		return;
	}
	
	// PROPERTIES AND WRAPPED VALUES
	public BooleanProperty finishedProperty() {
		return isFinished;
	}

	public ObjectProperty<RoundViewModel> currentRoundProperty() {
		return currentRound;
	}
	public IntegerProperty answerCountProperty() {
		return answerCount;
	}
	
	public ObjectProperty<PlayerViewModel> currentPlayerProperty() {
		return currentPlayer;
	}
	
	public ObjectProperty<AnswerViewModel> prevAnswerProperty() {
		return prevAnswer;
	}
	
	public ObjectProperty<AnswerViewModel> prev2AnswerProperty() {
		return prev2Answer;
	}
	
	public ObjectProperty<EntityType> currentEntityTypeProperty() {
		return currentEntityType;
	}
	
	public ListProperty<PlayerViewModel> playersListProperty() {
		return playersList;
	}
	
	public ObjectProperty<PlayerViewModel> prevPlayerProperty() {
		return prevPlayer;
	}
	
	public boolean getFinished() {
		return isFinished.getValue();
	}
	
	public RoundViewModel getCurrentRound() {
		return currentRound.getValue();
	}

	public int getAnswerCount() {
		return answerCount.getValue();
	}
	public PlayerViewModel getCurrentPlayer() {
		return currentPlayer.getValue();
	}
	
	public PlayerViewModel getPreviousPlayer() {
		return prevPlayer.getValue();
	}
	
	public AnswerViewModel getPreviousAnswer() {
		return prevAnswer.getValue();
	}
	
	public AnswerViewModel getPrevious2Answer() {
		return prev2Answer.getValue();
	}
	
	public EntityType getCurrentEntityType() {
		return currentEntityType.getValue();
	}
	
	public ObservableList<PlayerViewModel> getPlayersList() {
		return playersList.getValue();
	}

	public ObjectProperty<ActionType> prevActionTypeProperty() {
		return prevActionType;
	}
}