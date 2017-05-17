package com.personal.peterpoon;

public interface View extends Subscribable, Observable {

	public String[] getQueryParameters();

}
