package org.ahinds.moviegame.themoviegame.views.controllers;

import java.io.IOException;
import java.util.Objects;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.ahinds.moviegame.themoviegame.MovieGameApp;
import org.ahinds.moviegame.themoviegame.PlayerProfile;

/* MainMenuViewController.java
 * 
 * Controller for MainMenuView.fxml. 
 * 
 * User Profiles creation / loading, as well as related UI items are not functional; only 
 * included to show general thought behind how it might look.
 * 
 * Only "NEW GAME" menu item is functional / has a corresponding view;
 * 
 * FUTURE WORK: 
 * 		- Map associated Media and MediaPlayer objects
 * 		- User Profile integration (including text file / embedded DB support)
 * 		- Create Views and Controllers for other Menu Items, 
 * 			add them to screen controller
 * 		- add arrow key navigation
 *
 */

public class MainMenuViewController {
	private MovieGameApp main;
	private Media hoverSound;
	private MediaPlayer mainMenuSfxPlayer;

	public MainMenuViewController(MovieGameApp main) {
		this.main = Objects.requireNonNull(main);
	}
	
  @FXML
  private ImageView backgroundImage;
  
  @FXML
  private FlowPane UserProfileFlowPane;
  
  @FXML
  private Pane profilePaneOverlay;

  @FXML
  private ListView<PlayerProfile> mainMenuUserProfilesListView;

  @FXML
  private VBox mainMenuUserProfileVBox;

  @FXML
  private Pane userProfilePane;

  @FXML
  private Label userProfileActionIcon;

  @FXML
  private Label mainMenuUserProfileNameLabel;

  @FXML
  private Text mainMenuTitle;

  @FXML
  private GridPane menuGridPane;

  @FXML
  private Label continueLabel;

  @FXML
  private Label loadLabel;

  @FXML
  private Label newGameLabel;

  @FXML
  private Label tutorialLabel;

  @FXML
  private Label socialLabel;

  @FXML
  private Label optionsLabel;

  @FXML
  private ImageView batarangFocusIcon;

	@FXML
	private void initialize() {
		setTitleFont();
		initializeSfx();
	}

	private void setTitleFont() {
		mainMenuTitle.fontProperty().set(
			Font.loadFont(
					getClass().getResource("/styles/fonts/batmfa__.ttf").toExternalForm(),
					120
			)
		);
	}

	private void initializeSfx() {
		final String hoverSoundUrl = getClass().getResource(
				"/assets/audio/Abletunes_TSD_Closed_Hi_Hat_04_copy.wav"
		).toExternalForm();

		hoverSound = new Media(hoverSoundUrl);
		mainMenuSfxPlayer = new MediaPlayer(hoverSound);
		mainMenuSfxPlayer.setVolume(0.05);
	}
	
	// UI EVENT HANDLERS
	
 /* 
	 * (NOT CURRENTLY IMPLEMENTED) shows flowpane with player profile listview; 
	 * 
	 *  handles click event triggering user action dialogue
	 */
 @FXML
 void userActionDialogue(MouseEvent event) {
 	// flowpane holds player profile ui with listview;
 	final boolean profilePaneVisibility = UserProfileFlowPane.isVisible();
 	UserProfileFlowPane.setVisible(!profilePaneVisibility);
 	profilePaneOverlay.setVisible(!profilePaneVisibility);
 		
 }
   
	/*
	 * 	remove text style of menu item that was hovered over
	 */
	@FXML
	void addHightlightAndFocus(MouseEvent event) {
		if (Objects.requireNonNull(event).getTarget() instanceof Label) {
			final Label menuItem = (Label) event.getTarget();
			menuItem.setStyle("-fx-background-color: #000000");
			menuItem.requestFocus();
			final Integer menuItemRowIndex = GridPane.getRowIndex((Node) event.getTarget());

			moveBatarangIcon(menuItemRowIndex);
			if (mainMenuSfxPlayer.getStatus() != Status.PLAYING) {
				mainMenuSfxPlayer.play();
			}

			mainMenuSfxPlayer.seek(Duration.ZERO);
		}
	}
	
	/*
	 *  moves batarang icon to follow cursor; uses row index from target node. 
	 */
	private void moveBatarangIcon(Integer rowIndex) {
		menuGridPane.getChildren().remove(batarangFocusIcon);
		GridPane.setRowIndex(batarangFocusIcon, rowIndex);
		menuGridPane.getChildren().add(batarangFocusIcon);
	}
	
	/*
	 * remove text style of menu item that was hovered over 
	 */
	@FXML
	private void removeHightlightAndFocus(MouseEvent event) {
		if (event.getTarget() != null && event.getTarget() instanceof Label) {
			Label menuItem = (Label) event.getTarget();
			menuItem.setStyle("");
		}
	}

	@FXML
	private void goToNewGame(MouseEvent event) {
		FXMLLoader newGameLoader = new FXMLLoader(main.getLayoutFxmlPaths().get(1));
		newGameLoader.setController(main.getControllers().get(1));
		
		try {
			Parent newGame = newGameLoader.load();
			main.addScreen("newGame", newGame);
			main.goTo("newGame");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}