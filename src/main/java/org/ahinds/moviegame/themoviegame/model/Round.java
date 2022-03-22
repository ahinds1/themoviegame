package org.ahinds.moviegame.themoviegame.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.javatuples.Pair;

/* Round.java
 * 
 * 
 * Stores data related to the specific game round, answers played, and their associations
 * 
 * FUTURE WORK:
 * 		- optimize data structures
 * 			- set or map for associations
 * 			- map answer to movie entity, entity to its id
 */
public final class Round {
	// shared variable to number each round, stored in its corresponding seqId
	private final static AtomicInteger roundCount = new AtomicInteger(0);
	private final AtomicInteger answerCount = new AtomicInteger(0); 
	private int seqId; // for natural ordering
	private boolean finished;
	private List<Answer> answers;
	private List<Pair<String, String>> answerAssociations;

	public Round() {
		seqId = roundCount.incrementAndGet();
		finished = false;
		answers = new ArrayList<>();
		answerAssociations = new ArrayList<>();	
	}
	
	public static void resetCount() {
		Round.roundCount.set(0);
	}
	
	public void addAnswer(Answer answerToAdd) {
		addAnswerImpl(answerToAdd);
	}
	
	private void addAnswerImpl(Answer answerToAdd) {
		answers.add(answerToAdd);
		answerCount.set(answers.size());
	}
	
	public AtomicInteger getAnswerCount() {
		return answerCount;
	}

	public int answerCountAsInt() {
		return answerCount.get();
	}

	public Answer removePreviousAnswer() {
		return removePreviousAnswerImpl();
	}

	private Answer removePreviousAnswerImpl() {
		if (answerCount.get() == 0) {
			throw new IllegalStateException("Round has no answers");
		}
		
		return answers.remove(answerCount.decrementAndGet());
	}
	
	public String nameFromPreviousAnswer() {
		 return nameFromPreviousAnswerImpl();
	}

	private String nameFromPreviousAnswerImpl() {
		return previousAnswer().getPlayer();
	}

	public Answer previousAnswer() {
		return previousAnswerImpl();
	}

	private Answer previousAnswerImpl() {
		return answers.get(answers.size() - 1);
	}

	public void addAssociation(Pair<String, String> association) {
		addAssociationImpl(association);
	}

	private void addAssociationImpl(Pair<String, String> association) {
		answerAssociations.add(association);
	}
	
	public int getRoundId() {
		return seqId;
	}
	
	public Boolean isFinished() {
		return finished;
	}

	public void setFinished(Boolean finished) {
		this.finished = finished;
	}
	
	public List<Answer> getAnswers() {
		return answers;
	}

	public List<Pair<String, String>> getAnswerAssociations() {
		return answerAssociations;
	}

	public void setAnswerAssociations(ArrayList<Pair<String, String>> associations) {
		this.answerAssociations = associations;
	}
	
	public AtomicInteger getRoundCount() {
		return roundCount;
	}

	public void removeLastAssociation() {
		answerAssociations.remove(answerAssociations.size() - 1);		
	}
}