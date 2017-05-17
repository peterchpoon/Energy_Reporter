package com.personal.peterpoon;

/**
 * Provides Usage file specific column header names
 * 
 * @author Peter C. Poon
 *
 */
public enum EnergyUsageFileField {
	BUILDING_ID("building_id"), HOUR("hour"), KWH_USAGE("kwh_usage");

	private final String value;

	private EnergyUsageFileField(String value) {
		this.value = value;
	}

	public String getFieldString() {
		return value;
	}
}
