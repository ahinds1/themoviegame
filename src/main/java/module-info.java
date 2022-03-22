module org.ahinds.moviegame.themoviegame {
	requires javafx.controls;
	requires javafx.base;
	requires javafx.fxml;
	requires transitive javafx.graphics;
	requires transitive javafx.media;
	requires transitive themoviedbapi;
	requires java.xml.bind;
	requires javatuples;
	requires dotenv.java;

  opens org.ahinds.moviegame.themoviegame to javafx.fxml;
  opens org.ahinds.moviegame.themoviegame.util to java.fxml;
	opens org.ahinds.moviegame.themoviegame.model.util to javafx.base, javafx.fxml;
	opens org.ahinds.moviegame.themoviegame.views.controllers to javafx.fxml;
	opens org.ahinds.moviegame.themoviegame.views.viewmodels to javafx.base;
  
	exports org.ahinds.moviegame.themoviegame;
}
