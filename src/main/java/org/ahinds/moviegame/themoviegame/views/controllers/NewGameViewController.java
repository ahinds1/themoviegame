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

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.ahinds.moviegame.themoviegame.MovieGameApp;
import org.ahinds.moviegame.themoviegame.model.Game;
import org.ahinds.moviegame.themoviegame.model.GameMode;
import org.ahinds.moviegame.themoviegame.model.GameConfiguration;
import org.ahinds.moviegame.themoviegame.model.GameConfiguration.Builder;
import org.ahinds.moviegame.themoviegame.model.player.PlayerImpl;
import org.ahinds.moviegame.themoviegame.util.ImageGenerator;
import org.ahinds.moviegame.themoviegame.views.viewmodels.GameOptionsBuilderViewModel;
import org.ahinds.moviegame.themoviegame.views.viewmodels.PlayerViewModel;

/* NewGameViewController.java
 * 
 * Controller for views generated from NewGameView.fxml.
 * 
 * FUTURE WORK: 
 * 				- Refactor to load assets on App.init() and store menu music media player here;
 * 				- Map associated Media and MediaPlayer objects;
 * 				- name validation when adding to table 
 * 					(no duplicates, 3-20 alphanumeric characters)
 * 				- add arrow key navigation
 * 				
 */
public class NewGameViewController {
	private Media backSound;
	private MediaPlayer menuTraversalSoundsPlayer;
	private MovieGameApp main;
	private ObservableList<PlayerViewModel> players;
	private GameOptionsBuilderViewModel gameOptionsBuilderViewModel;
	private Map<String, Image> backgroundImages;
	
	@FXML
	private Pane rootPane;

	@FXML
	private ImageView backgroundImage;

	@FXML
	private Text newGameText;

	@FXML
	private Button backButton;

	@FXML
	private Button nextButton;
	
	@FXML
	private Button startButton;

	@FXML
	private Group gameOptionsGroup;

	@FXML
	private Text gameModeLabelText;

	@FXML
	private ChoiceBox<GameMode> gameModeChoiceBox;

	@FXML
	private Text playersGroupLabel;
	
	@FXML
	private Text optionsGroupLabel;

	@FXML
	private Text numberOfLivesLabelText;

	@FXML
	private Text timeLimitLabelText;

	@FXML
	private Text numberOfLivesValueText;

	@FXML
	private Text timeLimitValueText;

	@FXML
	private Text saveFileLabelText;

	@FXML
	private Slider livesSlider;

	@FXML
	private Slider timeLimitSlider;

	@FXML
	private TextField saveFileTextBox;
	
	@FXML
	private Text answerCheckLabelText;

	@FXML
	private HBox toggleGroupHBox;

	@FXML
	private ToggleButton autoCheckToggleButton;

	@FXML
	private ToggleGroup answerMode;

	@FXML
	private ToggleButton bsModeToggleButton;

	@FXML
	private Group playerOptionsGroup;

	@FXML
	private HBox addPlayersHBox;

	@FXML
	private Label addPlayersLabel;

	@FXML
	private TextField addPlayersTextField;

	@FXML
	private Button addPlayerButton;

	@FXML
	private Button deletePlayerButton;

	@FXML
	private TableView<PlayerViewModel> playersTableView;

	@FXML
	private TableColumn<PlayerViewModel, String> playerTableColumn;

	@FXML
	private TableColumn<PlayerViewModel, String> extraLivesTableColumn;

	public NewGameViewController(MovieGameApp main) {
		this.main = main;
		backgroundImages = new HashMap<>();
		loadImages();
		loadBackSound();
	}
	
	private void loadImages() {
		final String casualPath = getClass().getResource(GameMode.CASUAL.getBackgroundPath()).toExternalForm();
		final String compPath = getClass().getResource(GameMode.COMPETITIVE.getBackgroundPath()).toExternalForm();
		final String compCatWoman = getClass().getResource("/assets/images/catwoman-5k-2019-uo-1920x1080.jpg").toExternalForm();
		
		backgroundImages.put("casual", ImageGenerator.generate(casualPath));
		backgroundImages.put("comp_batman", ImageGenerator.generate(compPath));
		backgroundImages.put("comp_catwoman", ImageGenerator.generate(compCatWoman));
	}
	
	private void loadBackSound() {
		final String backSoundFile = "/assets/audio/Abletunes TSD Closed Hi Hat 27 copy.wav";
	
		backSound = new Media(getClass().getResource(backSoundFile).toExternalForm());
		menuTraversalSoundsPlayer = new MediaPlayer(backSound);
		menuTraversalSoundsPlayer.setVolume(0.1);
	}

	
	/**
	 * set up sounds, control elements in newGame.fxml
	 */
	@FXML
	private void initialize() {
		final String kaneBold = getClass().getResource("/styles/fonts/kane-bold.ttf").toExternalForm();
		addValueListenersToOptions(); // enables background image swap - don't move after initializeGameModeChoiceBox 
		initializeGameModeChoiceBox();
		initializePlayerTableView(kaneBold);
		initializeGameOptionsViewModel();
		addStartButtonDisableBinding();
		addPlayersLabel.fontProperty().setValue(Font.loadFont(kaneBold, 84));
		main.playMenuMusic();
		
	}

	private void initializeGameOptionsViewModel() {
		gameOptionsBuilderViewModel = new GameOptionsBuilderViewModel();
		gameOptionsBuilderViewModel.init(new Builder());
		addGameOptionsBindings();
	}
	
	private void addValueListenersToOptions() {
		addValueListenerToGameMode();
		addValueListenerToLivesSlider();
		addValueListenerToTimeLimitSlider();
	}

	private void addValueListenerToGameMode() {
		gameModeChoiceBox.valueProperty().addListener((ob, oldv, newV) -> {
			if (newV == GameMode.COMPETITIVE) {
				changeBackgroundImage(backgroundImages.get("comp_batman"));
				showCompetitiveOptions();
			} else if (newV == GameMode.CASUAL) {
				changeBackgroundImage(backgroundImages.get("casual"));
				showCasualOptions();
			}
			livesSlider.setValue(3); // arbitrary value that we use to play
		});
	}
	
	private void changeBackgroundImage(Image image) {
		backgroundImage.setImage(image);	
	}

	private void addValueListenerToLivesSlider() {
		livesSlider.valueProperty().addListener((ob, old, newV) -> {

			numberOfLivesValueText.setText(newV.intValue() + "");
			
			// catwoman background image easter egg in competitive mode;
			if (gameModeChoiceBox.getValue() == GameMode.COMPETITIVE) {
				if (newV.intValue() == 9) {
					backgroundImage.setImage(backgroundImages.get("comp_catwoman"));
				} else if (newV.intValue() < 9) {
					backgroundImage.setImage(backgroundImages.get("comp_batman"));
				}
			}
			
			gameOptionsBuilderViewModel.baseLivesProperty().setValue(newV.intValue());
		});		
	}

	private void addValueListenerToTimeLimitSlider() {
		timeLimitSlider.valueProperty().addListener((ob, old, newV) -> {
			timeLimitValueText.setText(String.valueOf(newV.intValue()));
		});
	}

	private void initializeGameModeChoiceBox() {
		gameModeChoiceBox.setItems(FXCollections.observableList(Arrays.asList(GameMode.values())));
	}

	private void addGameOptionsBindings() {
		gameModeChoiceBox.valueProperty().bindBidirectional(gameOptionsBuilderViewModel.gameModeProperty());
		livesSlider.valueProperty().bindBidirectional(gameOptionsBuilderViewModel.numberOfRoundsProperty());
		timeLimitSlider.valueProperty().bindBidirectional(gameOptionsBuilderViewModel.timeLimitDurationProperty());
		gameOptionsBuilderViewModel.timeLimitProperty().bindBidirectional(timeLimitSlider.disableProperty());
		saveFileTextBox.textProperty().bindBidirectional(gameOptionsBuilderViewModel.saveFileNameProperty());
		gameOptionsBuilderViewModel.answerLookupProperty().bindBidirectional(autoCheckToggleButton.selectedProperty());
		
	}

	private void addStartButtonDisableBinding() {
		// if <2 players added or no file name entered, start button is disabled;
		final BooleanBinding gameParametersInvalid = new BooleanBinding() {
			{
				super.bind(players, saveFileTextBox.textProperty());
			}
			@Override
			protected boolean computeValue() {
				return players.size() < 2 || saveFileTextBox.textProperty().getValueSafe().isEmpty();
			}
		};
		
		startButton.disableProperty().bind(gameParametersInvalid);
	}
	
	private void initializePlayerTableView(String font) {
		final Font buttonFont = Font.loadFont(font, 30);
		setButtonFonts(buttonFont);
		
		players = FXCollections.observableArrayList();
		final Label tablePlaceHolder = new Label("ADD 2+ PLAYERS!");
		tablePlaceHolder.fontProperty().setValue(Font.loadFont(font, 42)); 
	tablePlaceHolder.setTextFill(Color.rgb(102, 78, 58, 1));
	//	tablePlaceHolder.setTextFill(Color.rgb(95,108,191, 1));
		playersTableView.setPlaceholder(tablePlaceHolder);
		playersTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); // to delete multiple players if desired
		playersTableView.setItems(players);
		playersTableView.setEditable(false);
		playerTableColumn.setCellValueFactory(new PropertyValueFactory<PlayerViewModel, String>("name"));		
		playerTableColumn.setStyle("-fx-font: 40px 'Barlow Medium';");
		playerTableColumn.setSortable(false);
		
	}

	private void setButtonFonts(final Font buttonFont) {
		addPlayerButton.fontProperty().setValue(buttonFont);
		deletePlayerButton.fontProperty().setValue(buttonFont);
	}

	// default casual settings
	private void showCasualOptions() {
		timeLimitSlider.setValue(0);
		timeLimitSlider.setDisable(true);
		numberOfLivesLabelText.setText("Rounds"); // temporary placeholder for swapping out options groups
		livesSlider.setMax(12);
	}
  // default competitive settings
	private void showCompetitiveOptions() {
		timeLimitSlider.setDisable(false);
		timeLimitSlider.setValue(60);
		numberOfLivesLabelText.setText("Lives"); // use lives label for game
		livesSlider.setMax(9); // arbitrary value to show UI changes (and catwoman easter egg)
	}
 
	// UI EVENT HANDLERS
	
	// Button hover event handler
	@FXML
	private void addHighlightAndFocus(MouseEvent event) {
		if (event.getTarget() != null && event.getTarget() instanceof Button) {
			final Button menuItem = (Button) event.getTarget();
			if (menuItem.getText().equals("Back")) {
				menuItem.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, Color.BLACK, 24.74, 0.64, 0.0, 0.0));
				menuItem.setStyle("-fx-background-color: none; -fx-text-fill: #f3b983");
			} else {
				menuItem.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, Color.BLACK, 24.74, 0.64, 0.0, 0.0));
				menuItem.setStyle("-fx-background-color: none;");
			}
			menuItem.requestFocus();
		}
	}
	
	// exit hover event for buttons, button fades into background;
	@FXML
	private void removeHighlightAndFocus(MouseEvent event) {
		if (event.getTarget() instanceof Button) {
			final Button menuItem = (Button) event.getTarget();
			menuItem.setStyle("-fx-background-color: none");
			menuItem.setEffect(null);
		}
	}
		
	/*
	 * Add button event handler. Creates a player object from the 
	 * name provided and adds to table.
	 */
	@FXML
	private void addPlayerToTable(ActionEvent event) {
		playButtonSound();
		
		final String playerName = addPlayersTextField.getText();

		if (!playerName.isEmpty()) {
			players.add(PlayerViewModel.from(new PlayerImpl(playerName, null)));
			addPlayersTextField.setText("");
		}
	}

	// Remove button event handler
	@FXML
	private void removePlayerFromTable(ActionEvent event) {
		playButtonSound();
		
		final ObservableList<PlayerViewModel> selectedPlayers = playersTableView.getSelectionModel().getSelectedItems();
		playersTableView.getItems().removeAll(selectedPlayers);
	}

	/*
	 * event for AnswerCheck Toggle Group. Toggles selection based on 
	 * the button pressed, the selection's style will change to provide 
	 * UI feedback.
	 */
	@FXML
	private void selectToggleButton(ActionEvent event) {
		playButtonSound(); 
		
		final ToggleButton selected = (ToggleButton) event.getSource();
		selected.setSelected(true);
		gameOptionsBuilderViewModel.save();
	}

	/* 
	 * 'Next' label click handler. Toggles visibility of Game Options and 
	 * Add players UI groups. 
	 */
	@FXML
	private void goToNextOptionsGroup(MouseEvent event) {
		playButtonSound();
		
		if (gameOptionsGroup.visibleProperty().get() == true) {
			gameOptionsGroup.setVisible(false);
			optionsGroupLabel.setVisible(false);
			playerOptionsGroup.setVisible(true);
			playersGroupLabel.setVisible(true);
			nextButton.setText("OPTIONS");
		} else {
			gameOptionsGroup.setVisible(true);
			optionsGroupLabel.setVisible(true);
			playerOptionsGroup.setVisible(false);
			playersGroupLabel.setVisible(false);
			nextButton.setText("PLAYERS");
		}
	}

	/*
	 * back button click handler
	 */
	@FXML
	private void goToMainMenu(MouseEvent event) {
		playButtonSound();
		
		main.goTo("mainMenu");
	}
	
	private void playButtonSound() {
		if (menuTraversalSoundsPlayer.getStatus() == Status.PLAYING) {
			resetPlaybackPosition();
		}
		menuTraversalSoundsPlayer.play();
	}

	private void resetPlaybackPosition() {
		menuTraversalSoundsPlayer.pause();
		menuTraversalSoundsPlayer.seek(Duration.ZERO);
	}

	/*
	 * set up game model / game controller / game view. 
	 *
	 */
	@FXML
	private void startGame(MouseEvent event) {
		gameOptionsBuilderViewModel.save();

		final GameConfiguration gameOptions = GameConfiguration.valueOf(gameOptionsBuilderViewModel.getModel());
		final List<PlayerViewModel> playerList = new ArrayList<>(players);
		final FXMLLoader gameViewLoader = new FXMLLoader(main.getLayoutFxmlPaths().get(2));
		final GameViewController gc = (GameViewController) main.getControllers().get(2);
		
		gc.setPlayers(playerList);
		gc.setGameModel(Game.from(gc, gameOptions, playerList));
		gameViewLoader.setController(gc);

		try {
			main.addScreen("inGame", gameViewLoader.load());
			main.goTo("inGame");
			if (mainMenuMusicIsPlaying()) {
				fadeOutMenuMusic();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean mainMenuMusicIsPlaying() {
		return main.mainMenuPlayerIsPlaying();
	}

	private void fadeOutMenuMusic() {
		Timeline timeline = new Timeline(
				new KeyFrame(Duration.seconds(8),
						new KeyValue(main.getMainMenuPlayer().volumeProperty(), 0.2),
						new KeyValue(main.getMainMenuPlayer().volumeProperty(), 0.125),
						new KeyValue(main.getMainMenuPlayer().volumeProperty(), 0.0062),
						new KeyValue(main.getMainMenuPlayer().volumeProperty(), 0.0)
				)
		);
		
		timeline.setOnFinished((event) -> {
			pauseMusic();
		});
		
		timeline.play();
	}

	private void pauseMusic() {
		main.pauseMainMenuPlayer();
	}
}