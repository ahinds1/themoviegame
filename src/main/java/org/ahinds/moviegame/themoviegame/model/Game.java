package org.ahinds.moviegame.themoviegame.model;

import java.util.ArrayList;

import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

import org.javatuples.Pair;

import javafx.event.EventHandler;
import javafx.scene.input.InputEvent;

import org.ahinds.moviegame.themoviegame.model.Answer.AnswerBuilder;
import org.ahinds.moviegame.themoviegame.model.GameObjects.Builder;
import org.ahinds.moviegame.themoviegame.model.movieentity.EntityType;
import org.ahinds.moviegame.themoviegame.model.player.PlayerImpl;
import org.ahinds.moviegame.themoviegame.model.player.commands.ActionType;
import org.ahinds.moviegame.themoviegame.model.player.commands.PlayerChallengeCommand;
import org.ahinds.moviegame.themoviegame.model.player.commands.PlayerCommand;
import org.ahinds.moviegame.themoviegame.model.player.commands.PlayerCommandBase;
import org.ahinds.moviegame.themoviegame.model.util.AnswerEvaluator;
import org.ahinds.moviegame.themoviegame.model.util.Evaluator;
import org.ahinds.moviegame.themoviegame.model.util.InputHandler;
import org.ahinds.moviegame.themoviegame.model.util.MessageGenerator;
import org.ahinds.moviegame.themoviegame.model.util.NameValidator;
import org.ahinds.moviegame.themoviegame.model.util.ValidationResult;
import org.ahinds.moviegame.themoviegame.model.util.Validator;
import org.ahinds.moviegame.themoviegame.util.ImageGenerator;
import org.ahinds.moviegame.themoviegame.views.controllers.GameViewController;
import org.ahinds.moviegame.themoviegame.views.viewmodels.PlayerViewModel;

/* Game.java
 * 
 * The game model (logic). Only Casual is playable. 
 * COMPETITIVE has not been implemented and will not play as intended.
 * 
 * FUTURE WORK:
 * 		- Refactor into abstract class and implement with Casual Game and Competitive Game  
 * 		- Refactor to implement using players/iterator stored in GameData
 * 		- Consider Java Builder pattern for construction. As it expands might become more useful.
 * 		- Refactor some data structures (see GameData.java)
 * 		- Refactor to include messages (calls to MessageGenerator) as part of game state, 
 * 			set text with the passed message via binding String property in GameViewModel to text property.
 * 		- Refactor to use Null/Empty Command class
 * 
 */
public class Game implements Validator, Evaluator, EventHandler<InputEvent> {
	private final GameViewController gameViewController;
	private final GameConfiguration config;
	
	// general data store
	private GameData data;
	
	/*
	 * relative game state objects to execute game, not all used in GameObjects.java
	 */
	private EntityType currentEntityType;
	private PlayerImpl currentPlayer;
	private PlayerImpl prevPlayer;
	private Round currentRound;
	private Answer currentAnswer;
	private Answer prevAnswer;
	private Answer prev2Answer;
	private List<PlayerImpl> players;
	private ListIterator<PlayerImpl> playerIterator;
	private PlayerCommandBase prevPlayerCommand;
	private PlayerCommandBase currentPlayerCommand;
	
	// results from after  player submits name (as String)
	private Name currentName; 
	private ValidationResult validationResult;
	private boolean answerEvaluation;
	
	public static Game from(GameViewController gc, GameConfiguration config, List<PlayerViewModel> playerList) {
		return new Game(gc, config, playerList);
	}
	
	private Game(GameViewController gameViewController, GameConfiguration config, List<PlayerViewModel> players) {
		this.gameViewController = Objects.requireNonNull(gameViewController);
		this.config = Objects.requireNonNull(config);
		initializeGamePlayers(Objects.requireNonNull(players));
		this.playerIterator = this.players.listIterator();
		
		initializeGameData();
	}
	
	private void initializeGamePlayers(List<PlayerViewModel> players) {
		final List<PlayerImpl> gamePlayers = new ArrayList<>();
		
		for (PlayerViewModel player : Objects.requireNonNull(players)) {
			gamePlayers.add(new PlayerImpl(player.getName(), this));
		}
		
		this.players = gamePlayers;
	}

	private void initializeGameData() {
		final GameData.Builder dataBuilder = 
				new GameData.Builder()
					.finished(false)
					.rounds(new ArrayList<>())
					.commands(new ArrayList<>())
					.players(this.players)
					.playerIterator(this.players.listIterator())
					.results(new ArrayList<>());
		
		data = GameData.valueOf(dataBuilder);
	}
		
	// PUBLIC CONTRACTS + IMPL
	
	@Override
	public void handle(InputEvent event) {
		handleInput(event);
	}

	private void handleInput(InputEvent event) {
		currentPlayerCommand = InputHandler.handle(event);
		
		if (Objects.nonNull(currentPlayerCommand)) {
			takeTurn(currentPlayerCommand);
		}
	}	
	
	public void takeTurn(PlayerCommand command) {
		takeTurnImpl(command);
	}

	private void takeTurnImpl(PlayerCommand command) {
		addCommand();
		command.execute(currentPlayer);
		updateViewModel();
	}
	
	private void addCommand() {
		data.getCommands().add(currentPlayerCommand);
	}
	
	private void updateViewModel() {
		gameViewController.updateViewModel(GameState.newState(buildGameObjects(), data));
	}
	
	private GameObjects buildGameObjects() {
		final GameObjects.Builder objects = new Builder()
			.players(players)
			.prevPlayer(prevPlayer)
			.currentPlayer(currentPlayer)
			.currentEntityType(currentEntityType)
			.currentRound(currentRound)
			.prevAnswer(prevAnswer)
			.prev2Answer(prev2Answer)
			.prevActionType(prevPlayerCommand == null ? ActionType.NONE : prevPlayerCommand.getType());

		return objects.build();
	}
	
	public void reject() {
		rejectImpl();
	}
	
	private void rejectImpl() {
		undo();
	}
	
	public void undo() {
		undoImpl();
	}

	private void undoImpl() {
		prevPlayerCommand.undo();
	}

	public void rollback() {
	 	rollbackImpl();
	}
	 
	private void rollbackImpl() {
		rollbackUndoPreviousAnswerImpl();
	}

	private void rollbackUndoPreviousAnswerImpl() {
		undoPreviousAnswer();
		goToPreviousPlayer();
		prevAnswer = null;
		toggleCurrentEntityType();
		
		gameViewController.rollbackViewModel();
	}
	
	public Answer undoPreviousAnswer() {
		return undoPreviousAnswerImpl();
	}
	 
	private Answer undoPreviousAnswerImpl() {
		return currentRound.removePreviousAnswer();
	}

	public void goToPreviousPlayer() {
		goToPreviousPlayerImpl();
	}

	private void goToPreviousPlayerImpl() {
		goToPreviousPlayerIteratorImpl();
	}

	private void goToPreviousPlayerIteratorImpl() {
		final int prevIndex = playerIterator.previousIndex();
		prevPlayer = currentPlayer;
		
		if (prevIndex == -1) { // beginning of player iterator 
			while (playerIterator.hasNext()) {
				currentPlayer = playerIterator.next();				
			}
		} else if (prevIndex > 0) {
			playerIterator.previous();
			currentPlayer = playerIterator.previous();
		}
	}
	
	public void challenge() {
		challengeImpl();
	}

	private void challengeImpl() {
		goToPreviousPlayer();
		updatePlayerCommandRefs();
	}
	
	private void updatePlayerCommandRefs() {
		prevPlayerCommand = currentPlayerCommand;
		currentPlayerCommand = null;
	}

	public void submit(String playerInput) {
		submitImpl(playerInput);
	}
	
	private void submitImpl(String playerInput) {
		nameFromPlayerInput(playerInput);
		validate();
		
		if (nameIsValid()) {
		 	buildAnswer();
			addAnswerToRound();
			evaluate();
			updateStatePostEval();
		
		} else if (nameNotFound()) {
			displayNameNotFound();
				
		} else if (nameAssociatedWithPrevious()) {
  		displayNameAlreadyAssociated();
		}
	}
	
	public void nameFromPlayerInput(String playerInput) {
		nameFromPlayerInputImpl(playerInput);
	}
	
	private void nameFromPlayerInputImpl(String playerInput) {
		currentName = Name.from(playerInput, currentEntityType);
	}
	
	@Override
	public void validate() {
		// valid if found a search result in MovieDB with submission
		// and name from result is not already associated with previous name
		// in current round.
		validationResult = NameValidator.validate(currentName, currentRound);
	}

	public boolean nameIsValid() {
		return nameIsValidImpl();
	}
	
	private boolean nameIsValidImpl() {		
		return validationResult.isValid();
	}
	
	public void buildAnswer() {
		buildAnswerImpl();	
	}
	
	private void buildAnswerImpl() {
		buildAnswerFromValidationResult();
	}

	public void addAnswerToRound() {
		addAnswerToRoundImpl();
	}
	
	private void addAnswerToRoundImpl() {
		currentRound.addAnswer(currentAnswer);
		
		if (currentAnswerStartsRound() == false) {
			associateAnswerWithPrevious();
		}
	}
	
	public void associateAnswerWithPrevious() {
		associateAnswerWithPreviousImpl();
	}
	
	private void associateAnswerWithPreviousImpl() {		
		addAssociation(new Pair<String, String>(
			prevAnswer.getNameFromTmdb(), 
			currentAnswer.getNameFromTmdb())
		);
	}

	public void addAssociation(Pair<String, String> lastTwoAnswers) {
		addAssociationImpl(lastTwoAnswers);
	}

	private void addAssociationImpl(Pair<String, String> lastTwoAnswers) {
		currentRound.addAssociation(lastTwoAnswers);
	}
	
	private void buildAnswerFromValidationResult() {
		Answer.AnswerBuilder answerBuilder = new AnswerBuilder()
		  .player(currentPlayer.getName())
		  .entityType(validationResult.getName().getType())
		  .answerFromTmdb(validationResult.getNameFromTmdb())
		  .dbId(validationResult.getDbId())
		  .answerImage(ImageGenerator.generate(validationResult.getNameImageUrl()));
		
		currentAnswer = Answer.from(answerBuilder);
	}
	
	@Override
	public void evaluate() {	
		answerEvaluation = evaluateAnswerImpl(currentAnswer, prevAnswer);
	}
	private boolean evaluateAnswerImpl(Answer currentAnswer, Answer prevAnswer){
		return currentAnswerStartsRound() ? true : AnswerEvaluator.evaluate(currentAnswer, prevAnswer);
	}
	
	public boolean currentAnswerStartsRound() {
		return currentAnswerStartsRoundImpl();
	}
	
	private boolean currentAnswerStartsRoundImpl() {
		return currentRound.answerCountAsInt() == 1;
	}
	
	/*
	 * update internal state to reflect changes and push to view model
	 */
	private void updateStatePostEval() {
		if (answerIsCorrect() && !prevActionIsChallenge()) {
			endTurn();
			
		} else if (!answerIsCorrect() || prevActionIsChallenge()) {
			
			if (!prevActionIsChallenge()) {
				displayNameWasIncorrect();
				
			} else if (prevActionIsChallenge() && answerIsCorrect()) {
				displayChallengeUnsuccessful();
				nextPlayer(); // player who challenged starts next round;
				
			} else if (prevActionIsChallenge() && !answerIsCorrect()) {
				displayChallengeSuccessful();
			}
			
			endRound();
			
			if (reachedRoundLimit()) { 
				endGame();
			} else {			
				startNewRound();
			} 
		}
	}
	
	public boolean answerIsCorrect() {
		return answerEvaluation;
	}
	
	public boolean prevActionIsChallenge() {
		return prevActionIsChallengeImpl();
	}

	private boolean prevActionIsChallengeImpl() {
		if (Objects.isNull(prevPlayerCommand)) {
			return false;
		}
		
		return (prevPlayerCommand instanceof PlayerChallengeCommand);
	}

	public void endTurn() {
		endTurnImpl();
	}
	
	private void endTurnImpl() {
		currentName = null;
		toggleCurrentEntityType();
		updateAnswerRefs();
		
		updatePlayerRefs();
		updatePlayerCommandRefs();	
	}
	
	private void toggleCurrentEntityType() {
		currentEntityType = (currentEntityType == EntityType.ACTOR) ? EntityType.MOVIE : EntityType.ACTOR;
	}

	public void updateAnswerRefs() {
		updateAnswerRefsImpl();
	}
	
	private void updateAnswerRefsImpl() {
		prev2Answer = prevAnswer;
		prevAnswer = currentAnswer;
		currentAnswer = null;
	}
	
	private void updatePlayerRefs() {
		prevPlayer = currentPlayer;
		nextPlayer();
	}
	
	public void nextPlayer() {
		nextPlayerImpl();
	}
	
	private void nextPlayerImpl() {
		if (playerIterator.hasNext() == false) { // last player of list, go back to first player
			while (playerIterator.hasPrevious()) { 
				currentPlayer = playerIterator.previous();	
			}
		} else {
			if (currentPlayer == null) { // beginning of game
				currentPlayer = playerIterator.next();
				return;
			}
			
			final PlayerImpl tempPlayer = playerIterator.next();
	
			// ensure next player not the same as current. see listiterator
			if (currentPlayer.equals(tempPlayer)) { 
				currentPlayer = playerIterator.next();
			} else {
				currentPlayer = tempPlayer;
			}
		}
	}
	
	private void displayNameWasIncorrect() {
		gameViewController.displayMessage(
			MessageGenerator.incorrectAnswerMessage(
				getLastAssociation(),
				currentPlayer,
				reachedRoundLimit()
			)
		);
	}
	
	private void displayChallengeUnsuccessful() {
		gameViewController.displayMessage(
			MessageGenerator.challengeUnsuccessfulMessage(
				prevPlayer, 
				currentPlayer, 
				getLastAssociation(),
				reachedRoundLimit()
			)
		);
	}

	private void displayChallengeSuccessful() {
		gameViewController.displayMessage(
			MessageGenerator.challengeSuccessfulMessage(
				prevPlayer, 
				currentPlayer, 
				getLastAssociation(),
				reachedRoundLimit()
			)
		);	
	}
	
	private Pair<String, String> getLastAssociation() {
		return currentRound.getAnswerAssociations().get(
				currentRound.getAnswerAssociations().size() - 1
		);
	}
	
	public boolean reachedRoundLimit() {
		return reachedRoundLimitImpl();
	}
	
	private boolean reachedRoundLimitImpl() {
		return currentRound.getRoundId() == config.getNumberOfRounds();
	}
		
	
	public void endRound() {
		endRoundImpl();
	}
	
	private void endRoundImpl() {
		currentRound.setFinished(true);
	}
	
	public boolean currentRoundFinished() {
		return currentRoundFinishedImpl();
	}
	 
	private boolean currentRoundFinishedImpl() {
		return currentRound.isFinished();
	}
	
	public void displayNameAlreadyAssociated() {
		gameViewController.displayMessage(
			MessageGenerator.nameAlreadyAssociatedMessage(
				validationResult.getNameFromTmdb(), 
				prevAnswer.getNameFromTmdb()
			)
		);
	}
	
	public void displayNameNotFound() {
		gameViewController.displayMessage(
			MessageGenerator.nameNotFoundMessage(
				validationResult.getName()
			)
		);
	}

	public boolean nameAssociatedWithPrevious() {
		return nameAssociatedWithPreviousImpl();
	}

	private boolean nameAssociatedWithPreviousImpl() {
		return validationResult.getAssociatedWithPrevAnswer() == true;
	}

	public boolean nameNotFound() {
		return nameNotFoundImpl();
	}

 	private boolean nameNotFoundImpl() {
		return validationResult.getFoundInDb() == false;
	}

	public boolean isFinished() {
		return isFinishedImpl();
	}
	
	private boolean isFinishedImpl() {
		return reachedRoundLimitImpl();
	}
	
	public void endGame() {
		endGameImpl();
	}
	
	private void endGameImpl() {
		data.setFinished(true);
	}
	
	public void startNewRound() {
		startNewRoundImpl();
	}	
	
	private void startNewRoundImpl() {
		resetAnswerRefs();
		resetPlayerCommandRefs();
		prevPlayer = null;
		currentEntityType = EntityType.ACTOR;
		currentRound = new Round();
		addCurrentRound();
		
	}
	
	public void resetAnswerRefs() {
		resetAnswerRefsImpl();
	}

	private void resetAnswerRefsImpl() {
		currentAnswer = null;
		prevAnswer = null;
		prev2Answer = null;
	}

	private void resetPlayerCommandRefs() {
		prevPlayerCommand = null;
		currentPlayerCommand = null;	
	}
	
	private void addCurrentRound() {
		data.getRounds().add(currentRound);
	}
	
	public void startGame() {
		startGameImpl();	
	}
	
	private void startGameImpl() {
		Round.resetCount();
		startNewRound();
		nextPlayer();
		updateViewModel();
	}
	
	// GETTERS
	
	public int currentRoundAsInt() {
		return currentRound.getRoundId();
	}
	
	public GameViewController getGc() {
		return gameViewController;
	}
	
	public GameConfiguration getGameConfiguration() {
		return config;
	}

	public EntityType getCurrentEntityType() {
		return currentEntityType;
	}
}