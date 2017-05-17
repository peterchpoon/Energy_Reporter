package com.personal.peterpoon;

/**
 * A coordinator for expected_savings Query and QueryProcessor
 * 
 * @author Peter C. Poon
 *
 */
public class ExpectedSavingsResponseBroker extends ResponseBroker {

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
		QueryProcessor queryProcessor = new ExpectedSavingsQueryProcessor((ExpectedSavingsQuery) query);
		queryProcessor.prepareQueryResponse();
	}

}
