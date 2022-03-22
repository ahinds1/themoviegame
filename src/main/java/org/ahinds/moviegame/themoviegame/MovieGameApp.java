package org.ahinds.moviegame.themoviegame;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.ArrayList;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.ahinds.moviegame.themoviegame.util.ScreenController;
import org.ahinds.moviegame.themoviegame.views.controllers.GameViewController;
import org.ahinds.moviegame.themoviegame.views.controllers.MainMenuViewController;
import org.ahinds.moviegame.themoviegame.views.controllers.NewGameViewController;

/* MovieGameApp.java
 * 
 * Main/Driver/Launch point of JavaFx App.
 * 
 * All view paths and controllers are stored here;
 * 
 * FUTURE WORK: 
 *  			- Map views to corresponding controllers;
 *  		  - Map Media and MediaPlayer Objects
 *  			- Construct FXML view for intro video
 *  			- Load majority of media/resources/fonts (including those 
 *  			  in Controllers) in App.init() and pass into constructors;
 *  			- create controllers when needed (not right away) and pass dependencies (data)
 *  
 */
public final class MovieGameApp extends Application {
	private Stage primaryStage;
	private ArrayList<Object> controllers;
	private ArrayList<URL> layoutFxmlPaths;
	private MediaPlayer mainMenuPlayer;
	private ScreenController screenController;
	private MediaPlayer videoPlayer;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override 
	public void init() {
		initializeFxmlViewPaths();
		initializeFxmlControllers();
		initializeMusic();
		initializeIntro();
	}
	
	private void initializeFxmlViewPaths() {
		layoutFxmlPaths = new ArrayList<>();
		layoutFxmlPaths.add(getClass().getResource("/views/mainMenuView.fxml"));
		layoutFxmlPaths.add(getClass().getResource("/views/newGameView.fxml"));
		layoutFxmlPaths.add(getClass().getResource("/views/gameView.fxml"));
	}
	
	private void initializeFxmlControllers() {

		controllers = new ArrayList<>();
		controllers.add(new MainMenuViewController(this));
		controllers.add(new NewGameViewController(this));
		controllers.add(new GameViewController(this));
	}

	private void initializeMusic() {
		final String mainMenuMusic = getClass().getResource("/assets/audio/The_Dark_Knight_Music_Ambience_Peaceful_Rain_on_Gotham_Rooftops.mp3").toExternalForm();
		final Media mainMenuMedia = new Media(mainMenuMusic);
		
		mainMenuPlayer = new MediaPlayer(mainMenuMedia);
		mainMenuPlayer.pause();
		mainMenuPlayer.setStartTime(Duration.seconds(108));
		mainMenuPlayer.setVolume(0.2);
	}
	
	private void initializeIntro() {
		final String introUrl = getClass().getResource("/assets/video/2008_MGM_logo_with_1995_lion_roar_1080p.mp4").toExternalForm();
		final Media mgmIntro = new Media(introUrl);
		
		videoPlayer = new MediaPlayer(mgmIntro);
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		initializeScreenController(); // cannot be called from App.init - creates scene;
		setUpIntroVideo();
		configurePrimaryStage();
		this.primaryStage.show();
		
		videoPlayer.setOnEndOfMedia(() -> {
			goTo("mainMenu");
			fadeIntoMainMenu(createFadeTransition(screenController.getMain().getRoot()));
		});
		
		videoPlayer.play();
	}

	private void initializeScreenController() {
		try {
			// practice pushing exceptions between layers of abstraction
			Scene scene = tryCreateSceneWithFxmlLoader(layoutFxmlPaths.get(0)); // main menu
			screenController = new ScreenController(scene);
		} catch (UncheckedIOException e){
			e.printStackTrace();
		}
	}

	private Scene tryCreateSceneWithFxmlLoader(URL fxmlPath) {
		final FXMLLoader fxmlSceneLoader = new FXMLLoader(fxmlPath);
		fxmlSceneLoader.setController(controllers.get(0));

		try {
			return new Scene(fxmlSceneLoader.load(), 1920, 1080, Color.BLACK);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		} 
	}
	
	private void setUpIntroVideo() {
		final MediaView videoPlayerView = new MediaView(videoPlayer);
		final AnchorPane videoRoot = new AnchorPane(videoPlayerView);
		
		videoRoot.setStyle("-fx-background-color: #000000;");
		videoRoot.setPrefSize(1920, 1080);
		videoPlayerView.setFitWidth(1920);
		videoPlayerView.setFitHeight(1080);
		screenController.addScreen("intro", videoRoot);
		goTo("intro");
	}
	
	private void configurePrimaryStage() {
		primaryStage.setScene(screenController.getMain());
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setTitle("The Movie Game");
		primaryStage.centerOnScreen();
		primaryStage.setFullScreenExitHint("");
		primaryStage.setFullScreen(true);
	}

	private FadeTransition createFadeTransition(Parent sceneRoot) {
		FadeTransition ft = new FadeTransition(Duration.seconds(9), sceneRoot);
		ft.setFromValue(0);
		ft.setToValue(0.80);
	
		return ft;
	}

	private void fadeIntoMainMenu(FadeTransition ft) {
		ft.play();
		playMenuMusic();
	}
	
	// PUBLIC CONTRACTS + IMPL 
	public boolean mainMenuPlayerIsPlaying() {
		return mainMenuPlayer.getStatus() == Status.PLAYING;
	}
	
	public void pauseMainMenuPlayer() {
		mainMenuPlayer.pause();
	}
	
	public void playMenuMusic() {
		playMenuMusicImpl();
	}

	private void playMenuMusicImpl() {
		mainMenuPlayer.setVolume(0.2);
		mainMenuPlayer.play();
	}
	
	public void goTo(String gameScreen) {
		goToImpl(gameScreen);
	}

	private void goToImpl(String gameScreen) {
		screenController.activate(gameScreen);
	}
	
	public void addScreen(String name, Parent gameView) {
		screenController.addScreen(name, gameView);
	}
	
	// GETTERS
	public ArrayList<Object> getControllers() {
		return controllers;
	}

	public ArrayList<URL> getLayoutFxmlPaths() {
		return layoutFxmlPaths;
	}

	public MediaPlayer getMainMenuPlayer() {
		return mainMenuPlayer;
	}
}