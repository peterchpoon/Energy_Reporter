package com.personal.peterpoon;

/**
 * Provides supported query function names
 * 
 * @author Peter C. Poon
 *
 */
public enum EnergyReporterQueryFunction {
	PEAK_USAGE("peak_usage"), EXPECTED_SAVINGS("expected_savings"), HELP("help");

	private final String value;

	private EnergyReporterQueryFunction(String value) {
		this.value = value;
	}

	public String getFunctionString() {
		return value;
	}
}
