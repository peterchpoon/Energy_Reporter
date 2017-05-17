package com.personal.peterpoon;

/**
 * Message delivering object specific between View and QueryProcessor
 * communications.
 * 
 * @author Peter C. Poon
 *
 */
public abstract class Query {
	private String[] queryArguments;

	public Query(String[] args) throws IllegalArgumentException {
		if (args == null) {
			throw new IllegalArgumentException("Query argument/s cannot be null");
		}

		this.queryArguments = args;
	}

	public String[] getQueryArguments() {
		return queryArguments;
	}

	/**
	 * Gets error message when error is found during response construction
	 * 
	 * @return null when no errors; a message when an error has occurred.
	 */
	public abstract String getErrorMessage();

	/**
	 * Sets error message if an error has occurred during response construction
	 * 
	 * @param errorMessage
	 *            null when no errors; a message when an error has occurred.
	 */
	protected abstract void setErrorMessage(String errorMessage);
}
