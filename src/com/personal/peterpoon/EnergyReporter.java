package com.personal.peterpoon;

public class EnergyReporter {

	public static void main(String[] args) {
		try {
			new EnergyReporterController(args);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}