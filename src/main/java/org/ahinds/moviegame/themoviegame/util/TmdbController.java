package org.ahinds.moviegame.themoviegame.util;

import java.util.ArrayList;
import java.util.List;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.TmdbPeople;
import info.movito.themoviedbapi.TmdbSearch;
import info.movito.themoviedbapi.TmdbMovies.MovieMethod;
import info.movito.themoviedbapi.model.config.TmdbConfiguration;

/* TmdbController.java
 * 
 * TMDB (The Movie Database) Wrapper API controller; handles all TMDB API requests and results
 *
 * Not in game.model.util because images may be used in main menu background 
 * in future versions.
 * 
 * FUTURE WORK:
 * 		- Refactor to non static db controller, construct in App and pass api key as param; 
 * 		- allow multiple DB's (TMDB/IMDB/others if they exist)
 * 		- Create adapters for each Movie DB used, pass API keys via command-line args
 * 		- Fetch collections of entity images to enable a variety of images 
 * 					(currently grab first image, or provided poster path)
 *
 */

 public final class TmdbController {
	private static final TmdbApi moviesApi = new TmdbApi(System.getenv("TMDB_API_KEY")); 
	public static final TmdbConfiguration config = setUpApiConfig();
	public static final TmdbSearch search = moviesApi.getSearch(); 
	public static final TmdbMovies movies = moviesApi.getMovies(); 
	public static final TmdbPeople people = moviesApi.getPeople();
	public static final int MAX_PAGE_COUNT = 1;

	public static String buildActorImageUrl(int dbId) {
		return new StringBuilder()
			.append(config.getSecureBaseUrl())
			.append(config.getProfileSizes().get(2))
			.append(people.getPersonImages(dbId).get(0).getFilePath())
			.toString();
	}
	
	public static String buildMovieImageUrl(int dbId) {
		return new StringBuilder()
			.append(config.getSecureBaseUrl())
			.append(config.getPosterSizes().get(6))
			.append(movies.getMovie(dbId, "en-US", MovieMethod.images).getPosterPath())
			.toString();
	}
	
	/*
	 * configuration to create image urls from the TMDB API;
	 * 
	 * profile sizes are for actors
	 * posterSizes are for movies
	 */
	private static TmdbConfiguration setUpApiConfig() {
		final TmdbConfiguration apiConfig = new TmdbConfiguration();
		List<String> profileSizes = new ArrayList<>();
		List<String> posterSizes = new ArrayList<>();
		profileSizes.add("w45");
		profileSizes.add("w185");
		profileSizes.add("w300");
		profileSizes.add("h632");
		profileSizes.add("original");
		posterSizes.add("w92");
		posterSizes.add("w154");
		posterSizes.add("w185");
		posterSizes.add("w342");
		posterSizes.add("w500");
		posterSizes.add("w780");
		posterSizes.add("original");
		// logo -> profile -> poster -> backdrop
		apiConfig.setSecureBaseUrl("https://image.tmdb.org/t/p/");
		apiConfig.setProfileSizes(profileSizes);
		//		config.setLogoSizes(List<String> logoSizes);
		//		config.setBackdropSizes(List<String> backdropSizes);
		apiConfig.setPosterSizes(posterSizes);
		
		return apiConfig;
	}
}