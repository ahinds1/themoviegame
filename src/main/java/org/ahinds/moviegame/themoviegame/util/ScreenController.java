package org.ahinds.moviegame.themoviegame.util;

import java.util.HashMap;

import javafx.scene.Parent;
import javafx.scene.Scene;


/* ScreenController.java
 * 
 * Utility class, responsible for storing and activating game screens. 
 * Screens are simply Parent nodes swapped out as the scene's root node. 
 * Current screens are loaded FXML files;
 * 
 * Screens stored include: Main Menu, New Game, In-Game, Intro
 */

public final class ScreenController {
  private HashMap<String, Parent> screenMap = new HashMap<>();
  private Scene main;

  public ScreenController(Scene main) {
      this.main = main;
      addScreen("mainMenu", main.getRoot());
  }
  
  public ScreenController() {
  	
  }

  public void addScreen(String name, Parent pane){
       screenMap.put(name, pane);
  }

  public void removeScreen(String name){
      screenMap.remove(name);
  }

  public void activate(String name){
      main.setRoot(screenMap.get(name));
  }

  public Scene getMain() {
		return main;
	}
}