package com.personal.peterpoon;

/**
 * Message delivering object specific between View and
 * ExpectedSavingsQueryProcessor communications.
 * 
 * @author Peter C. Poon
 *
 */
public class ExpectedSavingsQuery extends Query {
	private final int REQUIRED_NUM_PARAMS = 3;
	private String filePath;
	private String buildingId;

	private String errorMessage;
	private Pair<String, String> responseExpectedSavings;

	/**
	 * 
	 * @param args
	 *            case and order sensitive array of Strings, 0: &lt;file
	 *            path&gt;, 1: expected_savings, 2: &lt;building_id&gt;
	 * @throws IllegalArgumentException
	 */
	public ExpectedSavingsQuery(String[] args) throws IllegalArgumentException {
		super(args);
		processArguments(getQueryArguments());
	}

	/**
	 * Extracts and caches required parameters for expected savings query
	 * 
	 * @param args
	 *            case and order sensitive array of Strings, 0: &lt;file
	 *            path&gt;, 1: [not used], 2: &lt;building_id&gt;
	 * @throws IllegalArgumentException
	 */
	private void processArguments(String[] args) throws IllegalArgumentException {
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
	 * Sets query response data key/value pair of expected savings
	 * 
	 * @param responseName
	 *            response key
	 * @param responseValue
	 *            response value
	 */
	public void setResponseExpectedSavings(EnergyUsageFileField responseName, String responseValue) {
		this.responseExpectedSavings = new Pair<>(responseName.getFieldString(), responseValue);
	}

	/**
	 * Gets query response key/value pair of expected savings
	 * 
	 * @return Pair&lt;String, String&gt; expected savings response data
	 *         key/value pair
	 */
	public Pair<String, String> getResponseExpectedSavings() {
		return this.responseExpectedSavings;
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
