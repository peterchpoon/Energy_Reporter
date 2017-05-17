package com.personal.peterpoon;

/**
 * Abstraction layer between the UI and the models
 * 
 * @author Peter C. Poon
 *
 */
public class EnergyReporterController {
	private View view;

	/**
	 * Constructor specifically for CLI
	 * 
	 * @param args
	 *            CLI arguments
	 * @throws Exception
	 */
	public EnergyReporterController(String[] args) throws Exception {
		view = new ViewCLI(args);
		runCLI();
	}

	/**
	 * Coordinates the CLI view with models
	 * 
	 * @throws Exception
	 */
	private void runCLI() throws Exception {
		ResponseBroker rb = null;
		EnergyReporterQueryFunction queryFunction = ((ViewCLI) view).getQueryFunction();

		switch (queryFunction) {
		case PEAK_USAGE:
			rb = new PeakUsageResponseBroker();
			break;

		case EXPECTED_SAVINGS:
			rb = new ExpectedSavingsResponseBroker();
			break;

		case HELP:
			rb = new HelpResponseBroker();
			break;

		default:
			throw new IllegalArgumentException(queryFunction.getFunctionString() + " isn't a validate query function.");
		}

		view.addSubscriber(rb);
		rb.addSubscriber(view);

		view.notifySubscribers();
	}
}
