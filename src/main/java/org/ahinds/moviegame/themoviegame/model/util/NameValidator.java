package org.ahinds.moviegame.themoviegame.model.util;

import java.util.ArrayList;
import java.util.List;
import org.javatuples.Pair;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.people.Person;

import org.ahinds.moviegame.themoviegame.model.Name;
import org.ahinds.moviegame.themoviegame.model.Round;
import org.ahinds.moviegame.themoviegame.model.movieentity.EntityType;
import org.ahinds.moviegame.themoviegame.model.util.ValidationResult.Builder;
import org.ahinds.moviegame.themoviegame.util.TmdbController;

/* NameValidator.java
 * 
 * Utility class. Responsible for validating name submitted by player. 
 * 
 * Validation involves attempting to retrieve a name from searching via a Movie DB API
 * 
 * FUTURE WORK:
 * 		- Decouple name validation and movie entity data lookup (might expand in future)
 * 				- refactor validation to simply return the returned name and dbId
 * 					and build url in separate call to TMDBController from a different class.
 * 
 *  	- 
 */
public class NameValidator {
	
 	public static ValidationResult validate(Name submission, Round currentRound) {
		 return validateImpl(submission, currentRound);
	}

	public static ValidationResult validateImpl(Name submission, Round currentRound) {
		final Builder validationResult = new Builder(submission);
		checkForName(validationResult);
		
		if (validationResult.nameFound()) {
			checkRoundForAssociation(validationResult, currentRound);
		}
		
		validationResult.valid(validationResult.getFoundInDb() && validationResult.getAssociatedWithPreviousAnswer() == false);
		
		return validationResult.build();
	}

	public static Builder checkForName(Builder validationBuilder) {
		return (validationBuilder.getName().getType() == EntityType.ACTOR) 
				 ? checkPeople(validationBuilder.getName().getName(), validationBuilder) 
				 : checkMovies(validationBuilder.getName().getName(), validationBuilder);
	}
	 
	 /*
	 *  searches TMDB for people with name player submitted
	 * 
	 *  returns: default validation builder (if not found) or validation builder with info from db
	 */
	public static Builder checkPeople(String input, Builder validationBuilder){
		final List<Person> actorSearch = new ArrayList<>(TmdbController.search.searchPerson(input, false, 0 ).getResults());
		
		if (actorSearch.size() > 0) {
			final Person firstActor = actorSearch.get(0); // arbitrarily grab first actor
			
			validationBuilder
				.nameReturned(firstActor.getName())
				.foundInDb(true)
				.dbId(firstActor.getId())
				.nameImageUrl(TmdbController.buildActorImageUrl(firstActor.getId()));
		}
		
		return validationBuilder;
	}
	
	/*
	 *  searches TMDB for movie with name player submitted
	 * return true if a movie with name (could be partial) was found, false otherwise;
	 */
	public static Builder checkMovies(String input, Builder validationBuilder) {
		final List<MovieDb> movieSearch = TmdbController.search.searchMovie(input,  0, "", true, 0).getResults();
		
		if (movieSearch.size() > 0) {
			final MovieDb firstMovie = movieSearch.get(0);
			
			validationBuilder
				.nameReturned(firstMovie.getTitle())
				.dbId(firstMovie.getId())
				.foundInDb(true)
				.nameImageUrl(TmdbController.buildMovieImageUrl(firstMovie.getId()));
		}
	
		return validationBuilder;
	}


	public static Builder checkRoundForAssociation(Builder validationResult, Round currentRound) {
		if (currentRound.answerCountAsInt() == 0) { // first answer of round, nothing to associate with
			validationResult.associatedWithPrevAnswer(false);
			return validationResult;
		}

		final String previousAnswer = currentRound.previousAnswer().getNameFromTmdb();
		
		for (Pair<String, String> actorMovieAssociation: currentRound.getAnswerAssociations()) {
			if (actorMovieAssociation.contains(validationResult.getNameReturned()) && actorMovieAssociation.contains(previousAnswer)) {
				validationResult.associatedWithPrevAnswer(true); // found association
				return validationResult;
			}
		}
		
		return validationResult.associatedWithPrevAnswer(false);
	}
}