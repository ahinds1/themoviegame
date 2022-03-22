package org.ahinds.moviegame.themoviegame.util;

import java.util.Objects;
import javafx.scene.image.Image;

/* ImageGenerator.java
 * 
 * Utility class, single responsibility of creating JavaFx Images;
 * 
 */
public final class ImageGenerator {
	public static Image generate(String url) {
		return new Image(Objects.requireNonNull(url));
	}
}