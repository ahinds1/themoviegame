package org.ahinds.moviegame.themoviegame.model.util;

import java.util.List;

import info.movito.themoviedbapi.TmdbMovies.MovieMethod;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.people.PersonCast;

import org.ahinds.moviegame.themoviegame.model.Answer;
import org.ahinds.moviegame.themoviegame.util.TmdbController;

/* AnswerEvaluator.java
 * 
 * Utility class. evaluates the current answer using TmdbController to check its association with the previous answer.
 * 
 * 
 * FUTURE WORK
 * 		- refactor to pass a Pair of Answers as single parameter
 * 		- refactor actorInMovie to use cast list of actor. Currently both methods use same logic.
 * 		- refactor to store past evaluations
 */
public class AnswerEvaluator {

	public static boolean evaluate(Answer currentAnswer, Answer prevAnswer) {
		return evaluateImpl(currentAnswer, prevAnswer);
	}

	private static boolean evaluateImpl(Answer currentAnswer, Answer prevAnswer) {
		if (Answer.isActor(currentAnswer)) { 
			return actorInMovie(currentAnswer, prevAnswer);
		} else if (Answer.isMovie(currentAnswer)) {
			return movieHasActor(currentAnswer, prevAnswer);
		}
		
		throw new IllegalArgumentException("Illegal NameType: " + currentAnswer.getEntityType());
	}
	
	private static boolean actorInMovie(Answer currentAnswer, Answer previousAnswer) {
		final String currAnswer = currentAnswer.getNameFromTmdb();
		final MovieDb previousMovie = TmdbController.movies.getMovie(previousAnswer.getDbId(), "en-US", MovieMethod.credits);
		final List<PersonCast> movieCast = previousMovie.getCast();
		
		for (PersonCast person : movieCast) {
			if (person.getName().equals(currAnswer)) {
				return true; 
			}
		}
		return false;
	}
	
	private static boolean movieHasActor(Answer currentAnswer, Answer previousAnswer) {
		final String prevAnswer = previousAnswer.getNameFromTmdb();
		final MovieDb movie = TmdbController.movies.getMovie(currentAnswer.getDbId(), "en-US", MovieMethod.images, MovieMethod.credits);
		final List<PersonCast> movieCast = movie.getCast();
		
		for (PersonCast person : movieCast) {
			if (person.getName().equals(prevAnswer)) {
				return true; 
			}
		}
		return false;
	}
}