package org.ahinds.moviegame.themoviegame.model;

import org.ahinds.moviegame.themoviegame.model.movieentity.EntityType;

import javafx.scene.image.Image;

/* Answer.java
 * 
 * An answer is a name that has been successfully validated + corresponding data. 
 * 
 * Type object created after Name Validation.
 * 
 * FUTURE WORK: 
 * 			- refactor to use MovieEntities (Actor/Movie) 
 */
 public final class Answer implements Evaluatable {
	private String player;
	private EntityType entityType;
	private String nameFromTmdb;
	private Image answerImage;
	private int dbId;
	
	public static boolean isActor(Answer answer) {
		return answer.getEntityType() == EntityType.ACTOR;
	}
	
	public static boolean isMovie(Answer answer) {
		return answer.getEntityType() == EntityType.MOVIE;
	}
	
	public static Answer from(AnswerBuilder builder) {
		return builder.build();
	}
	
	public static Answer defaultAnswer() {
		return Answer.from(new AnswerBuilder());
	}
	
	public static class AnswerBuilder {
		private String player = "";
		private EntityType entityType = EntityType.NONE;
		private String nameFromTmdb = "";
		private Image answerImage;
		private int dbId = -1;
		
		public AnswerBuilder() {
			
		}
		
		public AnswerBuilder player(String player) {
			this.player = player;
			return this;
		}
		
		public AnswerBuilder entityType(EntityType entityType) {
			this.entityType = entityType;
			return this;
		}
		
		public AnswerBuilder answerFromTmdb(String answerFromTmdb) {
			this.nameFromTmdb = answerFromTmdb;
			return this;
		}
		
		public AnswerBuilder answerImage(Image answerImage) {
			this.answerImage = answerImage;
			return this;
		}
		
		public AnswerBuilder dbId(int dbId) {
			this.dbId = dbId;
			return this;
		}
		
		private Answer build() {
			return new Answer(this);
		}
	}
	
	private Answer(AnswerBuilder builder) {
		this.player = builder.player;
		this.entityType = builder.entityType;
		this.nameFromTmdb = builder.nameFromTmdb;
		this.answerImage = builder.answerImage;
		this.dbId = builder.dbId;
	}
	
	public EntityType getEntityType() {
		return entityType;
	}
	public String getNameFromTmdb() {
		return nameFromTmdb;
	}
	
	public Image getAnswerImage() {
		return answerImage;
	}

	public int getDbId() {
		return dbId;
	}

	public String getPlayer() {
		return player;
	}
	
	public String playedBy() {
		return getPlayer();
	}
}