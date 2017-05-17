package com.personal.peterpoon;

public interface Observable {

	/**
	 * Notifies all subscribers for appropriate task/s
	 */
	public void notifySubscribers();

	/**
	 * Add a subscriber for notification
	 * 
	 * @param subscriber
	 *            This is an object that wants to be notified.
	 * @return Returns true is successfully subscribed, false otherwise.
	 */
	public boolean addSubscriber(Subscribable subscriber);

	/**
	 * Remove a subscriber for notification
	 * 
	 * @param subscriber
	 *            This is an object that wants to be removed from subscription.
	 * @return Returns true is successfully removed from subscription, false
	 *         otherwise.
	 */
	public boolean removeSubscriber(Subscribable subscriber);
}
