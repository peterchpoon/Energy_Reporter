package com.personal.peterpoon;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Query processor specifically for help query
 * 
 * @author Peter C. Poon
 *
 */
public class HelpQueryProcessor implements QueryProcessor {
	private HelpQuery query;
	private final String FILE_PATH = "../ReadMe/readme.txt";

	public HelpQueryProcessor(HelpQuery query) throws IllegalArgumentException {
		setQuery(query);
	}

	private void setQuery(HelpQuery query) throws IllegalArgumentException {
		if (query == null) {
			throw new IllegalArgumentException("Query provided to " + getClass().getSimpleName() + " cannot be null");
		} else {
			this.query = query;
		}
	}

	@Override
	public void prepareQueryResponse() throws Exception {
		String row = null;
		ArrayList<String> helpMessages = new ArrayList<>();

		BufferedReader bufferedReader = null;

		try {
			bufferedReader = new BufferedReader(new FileReader(this.FILE_PATH));

			while ((row = bufferedReader.readLine()) != null) {
				if (row.length() > 0) {
					helpMessages.add(row);
				}
			}

			if (!helpMessages.isEmpty()) {
				query.setResponseHelp("Help:", helpMessages);
			} else {
				query.setErrorMessage("Help messages file is empty.");
			}

		} catch (FileNotFoundException e) {
			throw new Exception(e);
		} catch (IOException e) {
			throw new Exception(e);
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					throw new Exception(e);
				}
			}
		}
	}
}
