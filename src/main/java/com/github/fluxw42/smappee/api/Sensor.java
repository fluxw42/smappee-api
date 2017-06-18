package com.github.fluxw42.smappee.api;

import java.util.List;

/**
 * Date: 6/18/17 - 11:57 AM
 *
 * @author Jeroen Meulemeester
 */
public interface Sensor {

	/**
	 * Get the unique sensor identifier
	 *
	 * @return The sensor id
	 */
	long getId();

	/**
	 * Get the name of the sensor (eg. 30030000078, ...)
	 *
	 * @return The name of the sensor
	 */
	String getName();

	/**
	 * Get all channels provided by this sensor
	 *
	 * @return An unmodifiable list of channels
	 */
	List<Channel> getChannels();

}
