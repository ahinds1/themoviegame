//----------------------------------------------------------
//  File Name
//  	desc
//  
// Author: Alex Hinds
// Date: 
// Issues: 
//  
// Description: 
//
// Assumptions:
//  
//  
//
//-----------------------------------------------------------  
package org.ahinds.moviegame.themoviegame.views.controllers;

import java.util.List;
import java.util.Objects;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.ahinds.moviegame.themoviegame.MovieGameApp;
import org.ahinds.moviegame.themoviegame.model.Game;
import org.ahinds.moviegame.themoviegame.model.GameState;
import org.ahinds.moviegame.themoviegame.model.movieentity.EntityType;
import org.ahinds.moviegame.themoviegame.model.player.commands.ActionType;
import org.ahinds.moviegame.themoviegame.views.viewmodels.AnswerViewModel;
import org.ahinds.moviegame.themoviegame.views.viewmodels.GameViewModel;
import org.ahinds.moviegame.themoviegame.views.viewmodels.PlayerViewModel;
import org.ahinds.moviegame.themoviegame.views.viewmodels.RoundViewModel;

/* GameViewController.java
 * 
 * Controller for views loaded from gameView.fxml. Passes JavaFx InputEvents to Game.java. 
 * Intermediary between the game model and the view
 * 
 */
public class GameViewController {
	private MovieGameApp main;
	private Game gameInstance;
	private GameViewModel viewModel;	
	private List<PlayerViewModel> players;

	@FXML
	private BorderPane rootPane;

	@FXML
	private Pane paneMiddle;

	@FXML
	private ImageView backgroundImage;

	@FXML
	private Label roundLabel;
  
	@FXML
	private ListView<String> gamePlayersListView;
	
	@FXML
	private Label gameModeLabel;

	@FXML
	private Group turnAreaGroup;

	@FXML
	private Pane gameOverPane;

	@FXML
	private Button returnButton;

	@FXML
	private Label GameOverLabel;

	@FXML
	private VBox prevAnswersVbox;

	@FXML
	private Group turnGroup;

	@FXML
	private ImageView prevAnswer2ImageView;

	@FXML
	private Label prevAnswer2NameLabel;

	@FXML
	private ImageView prevAnswerImageView;

	@FXML
	private Label prevAnswerNameLabel;

	@FXML
	private VBox currentPlayerVbox;

	@FXML
	private TextField nameTextField;
  
	@FXML
	private Button rejectButton;
  
	@FXML
	private Button challengeButton;

	@FXML
	private Label playerTurnLabel;

	@FXML
	private Label playerTurnTimerLabel;

	@FXML
	private Label turnErrorTextLabel;
	
	@FXML
	private Labeled currentEntityTypeLabel;
	
	public GameViewController(MovieGameApp main) {
		this.main = main;
	}
	
	@FXML 
	private void initialize() {
		initializeViewModel();
		gameInstance.startGame();
		initializePlayersListView();
		initializeGameModeUi();
		addPlayerActionButtonDisableBindings();
	}

	private void initializeViewModel() {
		viewModel = new GameViewModel();
		addViewModelChangeListeners();
	}
	
	private void addViewModelChangeListeners() {
		viewModel.currentEntityTypeProperty().addListener(currentEntityTypeChangeListener);
		viewModel.currentRoundProperty().addListener(currentRoundChangeListener);
		viewModel.currentPlayerProperty().addListener(currentPlayerChangeListener);
		viewModel.prevAnswerProperty().addListener(prevAnswerChangeListener);
		viewModel.prev2AnswerProperty().addListener(prevAnswer2ChangeListener);
		viewModel.finishedProperty().addListener(isFinishedChangeListener);
	}
	
	private void initializePlayersListView() {
		final ObservableList<String> gamePlayers = FXCollections.observableArrayList();
		

		for (PlayerViewModel player : viewModel.getPlayersList()) {
			
			gamePlayers.add(player.getName());
		}
		
		gamePlayersListView.setItems(gamePlayers);
		gamePlayersListView.setMouseTransparent(true); // prevent changing selected table row
	}
	
	private void initializeGameModeUi() {
		final String gameMode = gameInstance.getGameConfiguration().getGameMode().toString();
		
		setGameModeLabel(gameMode);
		setTurnTimerVisibility(gameMode);
	
	}

	private void setTurnTimerVisibility(String gameMode) {
		playerTurnTimerLabel.setVisible(gameMode.equalsIgnoreCase("competitive"));
	}

	private void setGameModeLabel(String gameMode) {
		final String fontPath = getClass().getResource("/styles/fonts/batmfa__.ttf").toExternalForm();
		
		gameModeLabel.fontProperty().set(Font.loadFont(fontPath,36));
		gameModeLabel.setText(gameMode);
		
	}
	
	private void addPlayerActionButtonDisableBindings() {
		final BooleanBinding prevActionIsChallenge = viewModel.prevActionTypeProperty().isEqualTo(ActionType.CHALLENGE);
		final BooleanBinding answerCountIsNotOne = viewModel.answerCountProperty().isNotEqualTo(1);
		final BooleanBinding answerCountLessThanTwo = viewModel.answerCountProperty().lessThan(2);
		
		rejectButton.disableProperty().bind(answerCountIsNotOne);
		challengeButton.disableProperty().bind(answerCountLessThanTwo.or(prevActionIsChallenge));
	}
	
 	/*
   * returnButton click handler 
   * 
   */
  @FXML
  private void goToMainMenu(ActionEvent event) {
		removeViewModelChangeListeners();
		unbindPlayerActionButtons();
	
		main.playMenuMusic();
		main.goTo("mainMenu");
  }
  
  private void removeViewModelChangeListeners() {
  	viewModel.currentEntityTypeProperty().removeListener(currentEntityTypeChangeListener);
		viewModel.currentRoundProperty().removeListener(currentRoundChangeListener);
		viewModel.currentPlayerProperty().removeListener(currentPlayerChangeListener);
		viewModel.prevAnswerProperty().removeListener(prevAnswerChangeListener);
		viewModel.prev2AnswerProperty().removeListener(prevAnswer2ChangeListener);
		viewModel.finishedProperty().removeListener(isFinishedChangeListener);
  }

  private void unbindPlayerActionButtons() {
  	challengeButton.disableProperty().unbind();
  	rejectButton.disableProperty().unbind();
  }

  @FXML 
	private void submitName(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER && nameTextField.getText().isEmpty() == false) {
			clearErrorText();
			gameInstance.handle(event);
		}
	}
	
  @FXML 
 	private void challengePreviousPlayerHandler(MouseEvent event) {
  	gameInstance.handle(event);
  }
    
  @FXML 
 	private void rejectPreviousAnswerHandler(MouseEvent event)  {
  	gameInstance.handle(event);
  }

	private void clearErrorText() {
		turnErrorTextLabel.setText("");
		turnErrorTextLabel.setVisible(false);
	}
	
	private void setErrorText(String errorText) {
		turnErrorTextLabel.setText(errorText);
		turnErrorTextLabel.setVisible(true);
	}
	
	private void gameOverUi() {
		hideTurnUi();
		showGameOver();
	}
	
	private void newRoundUi(RoundViewModel newValue) {
		updateRoundLabel(newValue.getSeqId());
	}
	
	private void hideTurnUi() {
		playerTurnTimerLabel.setVisible(false);
		currentPlayerVbox.setVisible(false);
		prevAnswersVbox.setVisible(false);
		rejectButton.setVisible(false);
		challengeButton.setVisible(false);
		playerTurnLabel.setVisible(false);
		currentEntityTypeLabel.setVisible(false);
	}

	private void showGameOver() {	
		gameOverPane.setVisible(true);
	}

	private void selectPlayerFromListView(PlayerViewModel player) {
		gamePlayersListView.getSelectionModel().select(player.getName());
	}
	
	public void displayMessage(String message) {
		setErrorText(message);
	}

	public void updateRoundLabel(Integer roundNumber) {
		updateRoundLabelImpl(roundNumber);
	}
	
	private void updateRoundLabelImpl(Integer roundNumber) {
		roundLabel.setText( "Round " + roundNumber);
	}
	 
	public void updateViewModel(GameState state) {
		updateViewModelImpl(state);
	}

	private void updateViewModelImpl(GameState state) {
		viewModel.update(state);
	}

	public void rollbackViewModel() {
		viewModel.rollback();
	}
	
	
	ChangeListener<EntityType> currentEntityTypeChangeListener = new ChangeListener<EntityType>() {
 		@Override
 		public void changed(ObservableValue<? extends EntityType> observable, EntityType oldValue, EntityType newValue) {
 			currentEntityTypeLabel.setText(newValue.toString());
 		}
	};

	// VIEW MODEL CHANGE LISTENERS - anonymous because can't remove/reference lambdas
 	ChangeListener<AnswerViewModel> prevAnswerChangeListener = new ChangeListener<AnswerViewModel>() {
 		@Override
 		public void changed(ObservableValue<? extends AnswerViewModel> observable, AnswerViewModel oldValue, AnswerViewModel newValue) {
 			if (newValue == null) {
 				prevAnswerImageView.setImage(null);
 				prevAnswerNameLabel.setText(null);
 				return;
 			}
 			
 			prevAnswerImageView.setImage(newValue.getImage());
 			prevAnswerNameLabel.setText(newValue.getNameFromLookup());
 		}
 	};
 	
 	ChangeListener<AnswerViewModel> prevAnswer2ChangeListener = new ChangeListener<AnswerViewModel>() {
 		@Override
 		public void changed(ObservableValue<? extends AnswerViewModel> observable, AnswerViewModel oldValue, AnswerViewModel newValue) {
 			if (newValue == null) {
 				prevAnswer2ImageView.setImage(null);
 				prevAnswer2NameLabel.setText(null);
 				return;
 			}
 			
 			prevAnswer2ImageView.setImage(newValue.getImage());
 			prevAnswer2NameLabel.setText(newValue.getNameFromLookup());
 		}
 	};
 	
 	ChangeListener<Boolean> isFinishedChangeListener = new ChangeListener<Boolean>() {
 		@Override
 		public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
 			if (newValue == true) {
 				gameOverUi();
 			}
 		}
 	};
 	
 	ChangeListener<RoundViewModel> currentRoundChangeListener = new ChangeListener<RoundViewModel>() {
 		@Override
 		public void changed(ObservableValue<? extends RoundViewModel> observable, RoundViewModel oldValue, RoundViewModel newValue) {
 			newRoundUi(newValue);
 		}	
 	};
 	
 	ChangeListener<PlayerViewModel> currentPlayerChangeListener = new ChangeListener<PlayerViewModel>() {
 		@Override
 		public void changed(ObservableValue<? extends PlayerViewModel> observable, PlayerViewModel oldValue, PlayerViewModel newValue) {
 			if (Objects.nonNull(oldValue) && oldValue.getName().equals(newValue.getName())) {
 				playerTurnLabel.setText(String.format("Still %s's turn", newValue.getName()));
 			} else {
 				playerTurnLabel.setText(String.format("%s's turn", newValue.getName()));
 	 			selectPlayerFromListView(newValue);
 			}
 			nameTextField.setText("");
 		}
 	};
 	
	// GETTERS AND SETTERS
	public Label getCompetitiveGameTimer() {
		return playerTurnTimerLabel;
	}

	public List<PlayerViewModel> getPlayers() {
		return players;
	}

	void setGameModel(Game model) {
		this.gameInstance = model;
	}

	void setPlayers(List<PlayerViewModel> playerList) {
		this.players = playerList;
	}
}