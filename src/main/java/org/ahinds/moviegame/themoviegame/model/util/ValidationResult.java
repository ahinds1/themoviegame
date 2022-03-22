package org.ahinds.moviegame.themoviegame.model.util;

import org.ahinds.moviegame.themoviegame.model.Name;

/* ValidationResult.java
 * 
 * class to store general name info from name validation via API look up.
 *  Includes image path and lookup id for future reference
 * 
 * FUTURE WORK:
 * 		- Implement validity state/status enum
 * 		- Refactor to look up image url with the GameViewController
 * 
 */
public class ValidationResult {
	public static final String NOT_FOUND = "";
	public static final int NOT_FOUND_INT = -1;
	
	private final Name name;
	private boolean associatedWithPrevAnswer;
	private boolean foundInDb;
	private boolean valid;
	private String nameFromTmdb;
	private String nameImageUrl;
	private int dbId;
	
	public static class Builder {
		private final Name name;
		
		// invalid result values as default
		private boolean associatedWithPrevAnswer = true;
		private boolean foundInDb = false;
		private boolean valid = false;
		private String nameReturned = NOT_FOUND;
		private String nameImageUrl = NOT_FOUND;
		private int dbId = NOT_FOUND_INT;
	
		public Builder associatedWithPrevAnswer(boolean associated) {
			this.associatedWithPrevAnswer = associated;
			return this;
		}
		
		public Builder foundInDb(boolean foundInDb) {
			this.foundInDb = foundInDb;
			return this;
		}
		
		public Builder valid(boolean valid) {
			this.valid = valid;
			return this;
		}
		
		public Builder nameReturned(String answerFromDb) {
			this.nameReturned = answerFromDb;
			return this;
		}
		
		public Builder nameImageUrl(String imageUrl) {
			this.nameImageUrl = imageUrl;
			return this;
		}
		
		public Builder dbId(int dbId) {
			this.dbId = dbId;
			return this;
		}
		
		public ValidationResult build() {
			return new ValidationResult(this);
		}
		
		public Builder(Name submittedName) {
			this.name = submittedName;
		}
		
		// alias for getFoundInDb
		public boolean nameFound() {
			return foundInDb;
		}
		
		public boolean getFoundInDb() {
			return this.foundInDb;
		}
		
		public String getNameReturned() {
			return nameReturned;
		}
		
		public Name getName() {
			return name;
		}
		
		public boolean getAssociatedWithPreviousAnswer() {
			return associatedWithPrevAnswer;
		}
	}
	
	private ValidationResult(Builder builder) {
		this.name = builder.name;
		this.associatedWithPrevAnswer = builder.associatedWithPrevAnswer;
		this.foundInDb = builder.foundInDb;
		this.valid = builder.valid;
		this.nameFromTmdb = builder.nameReturned;
		this.nameImageUrl = builder.nameImageUrl;
		this.dbId = builder.dbId;
	}
	
	public static ValidationResult from(Builder builder) {
		return builder.build();
	}
	
	// GETTERS
	public Name getName() {
		return name;
	}

	public boolean getAssociatedWithPrevAnswer() {
		return associatedWithPrevAnswer;
	}
	
	public boolean getFoundInDb() {
		return foundInDb;
	}

	public String getNameFromTmdb() {
		return nameFromTmdb;
	}

	public String getNameImageUrl() {
		return nameImageUrl;
	}

	public int getDbId() {
		return dbId;
	}
	
	public boolean getValid() {
		return valid;
	}
	
	public boolean isValid() {
		return getValid();
	}
	 
	@Override 
	public String toString() {
		 StringBuilder res = new StringBuilder();
		 res.append(String.format("Player submitted %s (%s)\n", name.getName(), name.getType()));
		 res.append(String.format("TMDB result:\nFound: %b\n Name Returned: %s\n dbId: %d\n ImageUrl: %s",
				 getFoundInDb(), getNameFromTmdb(), getDbId(), getNameImageUrl())); 
		 return res.toString();
	}
}