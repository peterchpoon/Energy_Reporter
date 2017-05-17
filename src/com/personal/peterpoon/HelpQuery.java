package com.personal.peterpoon;

import java.util.ArrayList;

/**
 * Message delivering object specific between View and HelpQueryProcessor
 * communications.
 * 
 * @author Peter C. Poon
 *
 */
public class HelpQuery extends Query {
	private String errorMessage;
	private Pair<String, String> responseHelp;

	public HelpQuery(String[] args) throws IllegalArgumentException {
		super(args);
	}

	/**
	 * Gets query response data key/value pair of help
	 * 
	 * @return Pair<String, String> peak hour response key/value pair
	 */
	public Pair<String, String> getResponseHelp() {
		return this.responseHelp;
	}

	/**
	 * Sets query response data key/value pair of help
	 * 
	 * @param responseName
	 *            response key
	 * @param responseValue
	 *            response value
	 */
	public void setResponseHelp(String responseName, ArrayList<String> responseValue) {
		StringBuilder helpString = new StringBuilder("");
		if (responseValue != null) {
			for (int i = 0; i < responseValue.size(); i++) {
				helpString.append(responseValue.get(i) + "\n");
			}
		}
		this.responseHelp = new Pair<>(responseName, helpString.toString());
	}

	@Override
	public String getErrorMessage() {
		return this.errorMessage;
	}

	@Override
	protected void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
