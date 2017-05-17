package com.personal.peterpoon;

/**
 * Message delivering object specific between View and PeakUsageQueryProcessor
 * communications.
 * 
 * @author Peter C. Poon
 *
 */
public class PeakUsageQuery extends Query {
	private final int REQUIRED_NUM_PARAMS = 3;
	private String filePath;
	private String buildingId;

	private String errorMessage;
	private Pair<String, String> responseHr;
	private Pair<String, String> responsePeakUsage;

	/**
	 * 
	 * @param args
	 *            case and order sensitive array of Strings, 0: &lt;file
	 *            path&gt;, 1: peak_usage, 2: &lt;building_id&gt;
	 * @throws IllegalArgumentException
	 */
	public PeakUsageQuery(String[] args) throws IllegalArgumentException {
		super(args);
		processArguments(getQueryArguments());
	}

	/**
	 * Extracts and caches required parameters for peak usage query
	 * 
	 * @param args
	 *            case and order sensitive array of Strings, 0: &lt;file
	 *            path&gt;, 1: [not used], 2: &lt;building_id&gt;
	 */
	private void processArguments(String[] args) {
		if (args.length < REQUIRED_NUM_PARAMS) {
			throw new IllegalArgumentException(EnergyReporterQueryFunction.PEAK_USAGE.getFunctionString()
					+ " query requires file path, and a " + EnergyUsageFileField.BUILDING_ID.getFieldString() + ".");
		}

		filePath = args[0];
		buildingId = args[2];
	}

	public String getFilePath() {
		return this.filePath;
	}

	public String getBuildingId() {
		return this.buildingId;
	}

	/**
	 * Sets query response data key/value pair of peak hour
	 * 
	 * @param responseName
	 *            response key
	 * @param responseValue
	 *            response value
	 */
	public void setResponseHr(EnergyUsageFileField responseName, String responseValue) {
		this.responseHr = new Pair<>(responseName.getFieldString(), responseValue);
	}

	/**
	 * Gets query response data key/value pair of peak hour
	 * 
	 * @return Pair<String, String> peak hour response key/value pair
	 */
	public Pair<String, String> getResponseHr() {
		return this.responseHr;
	}

	/**
	 * Sets query response data key/value pair of peak usage
	 * 
	 * @param responseName
	 *            response key
	 * @param responseValue
	 *            response value
	 */
	public void setResponsePeakUsage(EnergyUsageFileField responseName, String responseValue) {
		this.responsePeakUsage = new Pair<>(responseName.getFieldString(), responseValue);
	}

	/**
	 * Gets query response key/value pair of peak usage
	 * 
	 * @return Pair&lt;String, String&gt; peak usage response data key/value
	 *         pair
	 */
	public Pair<String, String> getResponsePeakUsage() {
		return this.responsePeakUsage;
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
