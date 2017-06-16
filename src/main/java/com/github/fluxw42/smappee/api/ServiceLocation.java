package com.github.fluxw42.smappee.api;

/**
 * Date: 6/14/17 - 10:56 PM
 *
 * @author Jeroen Meulemeester
 */
public interface ServiceLocation {

	/**
	 * Get the name of the service location (eg. Home, My house, ...)
	 *
	 * @return A human readable name for the service location
	 */
	String getName();

	/**
	 * Get the unique id of this service location
	 *
	 * @return The unique service location identifier
	 */
	long getId();

}
