package com.personal.peterpoon;

public interface Subscribable {
	public void update();

	public void update(Query query);

	public int hashCode();
}
