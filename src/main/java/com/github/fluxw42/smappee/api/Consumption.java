package com.github.fluxw42.smappee.api;

import java.util.Date;

/**
 * Date: 6/28/17 - 9:13 PM
 *
 * @author Jeroen Meulemeester
 */
public interface Consumption {

	/**
	 * Get the timestamp of the interval
	 *
	 * @return The timestamp of the interval, in UTC
	 */
	Date getTimeStamp();

	/**
	 * Get the consumption value
	 *
	 * @return The consumption
	 */
	double getConsumption();

	/**
	 * Get the solar value
	 *
	 * @return The solar
	 */
	double getSolar();

	/**
	 * Get the always-on value
	 *
	 * @return The always-on value
	 */
	double getAlwaysOn();

}
