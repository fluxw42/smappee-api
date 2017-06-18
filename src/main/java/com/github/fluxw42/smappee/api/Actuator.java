package com.github.fluxw42.smappee.api;

/**
 * Date: 6/18/17 - 11:24 PM
 *
 * @author Jeroen Meulemeester
 */
public interface Actuator {

	/**
	 * Get the unique id of this actuator
	 *
	 * @return The unique actuator identifier
	 */
	long getId();

	/**
	 * Get the name of the actuator (eg. TV, Lamp, ...)
	 *
	 * @return A human readable name for the actuator
	 */
	String getName();

}
