package com.personal.peterpoon;

/**
 * Query processor for Query request and response processing
 * 
 * @author Peter C. Poon
 *
 */
public interface QueryProcessor {

	/**
	 * Sets required response data of a Query
	 * 
	 * @throws Exception
	 */
	public void prepareQueryResponse() throws Exception;
}
