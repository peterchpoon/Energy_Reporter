package com.personal.peterpoon;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

/**
 * This View corresponds to the CLI UI
 * 
 * @author Peter C. Poon
 *
 */
public class ViewCLI implements View {
	// not a perfect hash function, but suffice for this purpose
	private final int hashCode = (int) (Math.random() * (499 + 1));

	private EnergyReporterQueryFunction queryFunction;
	private Query query;
	private String[] queryParams;
	private Hashtable<Integer, Vector<Subscribable>> subscribersHT = new Hashtable<>();

	public ViewCLI(String[] queryParams) throws IllegalArgumentException {
		if (queryParams == null) {
			throw new IllegalArgumentException(
					"Insufficient argument provided for " + getClass().getSimpleName() + ".");
		}

		this.queryParams = queryParams;
		setQueryFunction(queryParams);
		setQuery();
	}

	@Override
	public int hashCode() {
		return hashCode;
	}

	@Override
	public void update() {
		switch (this.queryFunction) {
		case PEAK_USAGE:
			PeakUsageQuery peakUsageQuery = (PeakUsageQuery) query;
			if (query.getErrorMessage() == null) {
				String hour = String.format("%02d:%02d", Integer.parseInt(peakUsageQuery.getResponseHr().getValue()),
						0);
				System.out.println(peakUsageQuery.getResponsePeakUsage().getValue() + " "
						+ peakUsageQuery.getResponsePeakUsage().getKey() + " at " + hour);
			} else {
				System.out.println(query.getErrorMessage());
			}
			break;
		case EXPECTED_SAVINGS:
			ExpectedSavingsQuery expectedSavingsQuery = (ExpectedSavingsQuery) query;
			if (query.getErrorMessage() == null) {
				System.out.println(expectedSavingsQuery.getResponseExpectedSavings().getValue() + " "
						+ expectedSavingsQuery.getResponseExpectedSavings().getKey());
			} else {
				System.out.println(query.getErrorMessage());
			}
			break;

		case HELP:
			HelpQuery helpQuery = (HelpQuery) query;
			if (query.getErrorMessage() == null) {
				System.out
						.println(helpQuery.getResponseHelp().getKey() + "\n" + helpQuery.getResponseHelp().getValue());
			} else {
				System.out.println(query.getErrorMessage());
			}
			break;
		}
	}

	@Override
	public void update(Query query) {
	}

	/**
	 * Setup appropriate query type based on user request
	 */
	private void setQuery() {
		switch (this.queryFunction) {
		case PEAK_USAGE:
			this.query = new PeakUsageQuery(this.queryParams);
			break;

		case EXPECTED_SAVINGS:
			this.query = new ExpectedSavingsQuery(this.queryParams);
			break;

		case HELP:
			this.query = new HelpQuery(this.queryParams);
			break;
		}
	}

	/**
	 * Gets which query function is being requested by user
	 * 
	 * @return enum query function value corresponds to user requested query
	 *         function
	 */
	public EnergyReporterQueryFunction getQueryFunction() {
		return this.queryFunction;
	}

	/**
	 * Sets which query function is being requested by user
	 * 
	 * @param args
	 *            case and order sensitive array of Strings
	 */
	private void setQueryFunction(String[] queryParams) throws IllegalArgumentException {
		String errorMessage = null;
		String function = null;

		if (queryParams.length == 3) {
			function = queryParams[1];

			if (function.equals(EnergyReporterQueryFunction.PEAK_USAGE.getFunctionString())) {
				queryFunction = EnergyReporterQueryFunction.PEAK_USAGE;

			} else if (function.equals(EnergyReporterQueryFunction.EXPECTED_SAVINGS.getFunctionString())) {
				queryFunction = EnergyReporterQueryFunction.EXPECTED_SAVINGS;

			} else {
				errorMessage = "'" + queryParams[1] + "' is not a valid query function for CLI.";
			}
		} else if (queryParams.length == 1) {
			function = queryParams[0];

			if (function.equals(EnergyReporterQueryFunction.HELP.getFunctionString())) {
				queryFunction = EnergyReporterQueryFunction.HELP;

			} else {
				errorMessage = "'" + queryParams[0] + "' is not a valid query function for CLI.";
			}
		} else {
			errorMessage = "Invalid entry for CLI.";
		}

		if (errorMessage != null) {
			throw new IllegalArgumentException(errorMessage);
		}
	}

	@Override
	public void notifySubscribers() {
		Set<Integer> keys = subscribersHT.keySet();
		Iterator<Integer> iteratorHT = keys.iterator();

		while (iteratorHT.hasNext()) {
			Vector<Subscribable> vector = subscribersHT.get(iteratorHT.next());
			Iterator<Subscribable> iteratorV = vector.iterator();
			while (iteratorV.hasNext()) {
				iteratorV.next().update(query);
			}
		}
	}

	@Override
	public boolean addSubscriber(Subscribable subscriber) {
		boolean retVal = false;
		if (subscriber != null) {
			Integer hashCode = subscriber.hashCode();

			if (!subscribersHT.containsKey(hashCode)) {
				Vector<Subscribable> vector = new Vector<>();

				vector.add(subscriber);
				subscribersHT.put(hashCode, vector);

				retVal = true;

			} else {
				Vector<Subscribable> subscribers = subscribersHT.get(hashCode);

				if (!subscribers.contains(subscriber)) {
					subscribers.add(subscriber);

					retVal = true;

				} else {
					boolean isFound = false;
					Iterator<Subscribable> iterator = subscribers.iterator();

					while (iterator.hasNext()) {
						Subscribable s = iterator.next();
						if (s == subscriber) {
							isFound = true;
							break;
						}
					}

					if (!isFound) {
						subscribers.add(subscriber);
						retVal = true;
					}
				}
			}
		}
		return retVal;
	}

	@Override
	public boolean removeSubscriber(Subscribable subscriber) {
		boolean retVal = false;

		if (subscriber != null) {
			Integer hashCode = subscriber.hashCode();

			if (subscribersHT.containsKey(hashCode)) {
				Vector<Subscribable> subscribers = subscribersHT.get(hashCode);

				if (subscribers.contains(subscriber)) {
					if (subscribers.size() == 1) {
						subscribersHT.remove(hashCode);
					} else {
						Iterator<Subscribable> iterator = subscribers.iterator();
						while (iterator.hasNext()) {
							Subscribable s = iterator.next();
							if (s == subscriber) {
								iterator.remove();
							}
						}
					}
					retVal = true;
				}
			}
		}

		return retVal;
	}

	@Override
	public String[] getQueryParameters() {
		return this.queryParams;
	}
}
