package com.github.fluxw42.smappee.api;

import java.util.Date;

/**
 * Date: 7/11/17 - 7:39 PM
 *
 * @author Jeroen Meulemeester
 */
public interface SensorRecord {

	/**
	 * Get the record timestamp
	 *
	 * @return The timestamp
	 */
	Date getTimeStamp();

	/**
	 * Get the recorded value of the first channel
	 *
	 * @return The first value
	 */
	double getValue1();

	/**
	 * Get the recorded value of the second channel
	 *
	 * @return The second value
	 */
	double getValue2();

	/**
	 * Get the recorded temperature
	 *
	 * @return The temperature in 1/10th of a °C
	 */
	double getTemperature();

	/**
	 * Get the recorded humidity
	 *
	 * @return The humidity in % (0-100)
	 */
	double getHumidity();

	/**
	 * Get the recorded battery charge left
	 *
	 * @return The battery level in % (0-100)
	 */
	double getBatteryLevel();

}
