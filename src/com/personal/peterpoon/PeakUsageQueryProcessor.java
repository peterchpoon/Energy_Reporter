package com.personal.peterpoon;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Query processor specifically for peak_usage query
 * 
 * @author Peter C. Poon
 *
 */
public class PeakUsageQueryProcessor implements QueryProcessor {
	private PeakUsageQuery query;
	private String filePath;
	private String buildingId;
	private String[] headersFromFile;

	private int buildingIdColIndex = -1;
	private int hourColIndex = -1;
	private int usageColIndex = -1;

	public PeakUsageQueryProcessor(PeakUsageQuery query) throws IllegalArgumentException {
		setQuery(query);
		setFilePath(query);
		setBuildingId(query);
	}

	private void setQuery(PeakUsageQuery query) throws IllegalArgumentException {
		if (query == null) {
			throw new IllegalArgumentException("Query provided to " + getClass().getSimpleName() + " cannot be null");
		} else {
			this.query = query;
		}
	}

	private void setFilePath(PeakUsageQuery query) throws IllegalArgumentException {
		if (query == null || query.getFilePath() == null) {
			throw new IllegalArgumentException(
					"File path provided to " + getClass().getSimpleName() + " cannot be null");
		} else {
			this.filePath = query.getFilePath();
		}
	}

	private void setBuildingId(PeakUsageQuery query) throws IllegalArgumentException {
		if (query == null || query.getBuildingId() == null) {
			throw new IllegalArgumentException(EnergyUsageFileField.BUILDING_ID.getFieldString() + " provided to "
					+ getClass().getSimpleName() + " cannot be null");
		} else {
			this.buildingId = query.getBuildingId();
		}
	}

	@Override
	public void prepareQueryResponse() throws Exception {
		String row = null;
		String[] fields = null;
		String usageHr = null;
		double usageMax = Double.MIN_VALUE;
		boolean isFound = false;

		BufferedReader bufferedReader = null;

		try {
			bufferedReader = new BufferedReader(new FileReader(this.filePath));

			if (headersFromFile == null || headersFromFile.length == 0) {
				getHeadersFromFile(bufferedReader);
			}

			setColumnsIndex();

			while ((row = bufferedReader.readLine()) != null) {
				row = row.trim();
				fields = row.split(",");

				if (fields[buildingIdColIndex].equals(this.buildingId)) {
					double usage = Double.parseDouble(fields[usageColIndex]);
					if (usage > usageMax) {
						usageMax = usage;
						usageHr = fields[hourColIndex];
						isFound = true;
					}
				}
			}

			if (isFound) {
				query.setResponseHr(EnergyUsageFileField.HOUR, usageHr);
				query.setResponsePeakUsage(EnergyUsageFileField.KWH_USAGE, String.valueOf(usageMax));
			} else {
				query.setErrorMessage("\"" + this.buildingId + "\" not in data set!");
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

	/**
	 * Caches column headers from CSV file
	 * 
	 * @param bufferedReader
	 *            buffer of CSV file being read
	 * @throws Exception
	 */
	private void getHeadersFromFile(BufferedReader bufferedReader) throws Exception {
		String row = null;
		row = bufferedReader.readLine();
		if (row != null) {
			row = row.trim();

			if (row.length() > 0) {
				headersFromFile = row.split(",");
			}
		}
	}

	/**
	 * Sets the column field index correspond to array index position
	 * 
	 * @throws Exception
	 */
	private void setColumnsIndex() throws Exception {
		buildingIdColIndex = getColumnIndex(EnergyUsageFileField.BUILDING_ID.getFieldString());
		hourColIndex = getColumnIndex(EnergyUsageFileField.HOUR.getFieldString());
		usageColIndex = getColumnIndex(EnergyUsageFileField.KWH_USAGE.getFieldString());

		if (buildingIdColIndex < 0 || hourColIndex < 0 || usageColIndex < 0) {
			throw new Exception("Column not found in file: '" + this.filePath + "'.");
		}
	}

	private int getColumnIndex(String header) {
		int retVal = -1;
		if (headersFromFile != null) {
			for (int i = 0; i < headersFromFile.length; i++) {
				if (headersFromFile[i].equals(header)) {
					retVal = i;
					break;
				}
			}
		}
		return retVal;
	}
}
