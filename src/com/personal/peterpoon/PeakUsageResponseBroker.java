package com.personal.peterpoon;

/**
 * A coordinator for peak_usage Query and QueryProcessor
 * 
 * @author Peter C. Poon
 *
 */
public class PeakUsageResponseBroker extends ResponseBroker {

	@Override
	public void update() {
		throw new UnsupportedOperationException("The 'update()' method isn't supported by " + getClass().getSimpleName()
				+ ". Did you mean to use 'update(Query)' method?");

	}

	@Override
	public void update(Query query) {
		setQuery(query);

		// setup separate thread to process/prepare query and response to avoid
		// blocking main thread
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	protected void prepareResponse(Query query) throws Exception {
		QueryProcessor queryProcessor = new PeakUsageQueryProcessor((PeakUsageQuery) query);
		queryProcessor.prepareQueryResponse();
	}

}
