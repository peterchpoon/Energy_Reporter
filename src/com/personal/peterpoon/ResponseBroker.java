package com.personal.peterpoon;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

/**
 * A coordinator between Query and QueryProcessor
 * 
 * @author Peter C. Poon
 *
 */
public abstract class ResponseBroker implements Subscribable, Observable, Runnable {
	// not a perfect hash function, but suffice for this purpose
	private final int hashCode = (int) (Math.random() * (499 + 1));

	private Query query;
	private Hashtable<Integer, Vector<Subscribable>> subscribersHT = new Hashtable<>();

	@Override
	public int hashCode() {
		return hashCode;
	}

	public void setQuery(Query query) {
		if (query == null) {
			throw new IllegalArgumentException("Query provided to " + getClass().getSimpleName() + " cannot be null");
		} else {
			this.query = query;
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
				iteratorV.next().update();
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
	public void run() {
		try {
			if (query != null) {
				prepareResponse(query);
				notifySubscribers();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Sets required response data of a Query
	 * 
	 * @throws Exception
	 */
	protected abstract void prepareResponse(Query query) throws Exception;

}
