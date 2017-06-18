package com.github.fluxw42.smappee.api;

import java.util.Currency;
import java.util.List;
import java.util.TimeZone;

/**
 * Date: 6/17/17 - 7:34 AM
 *
 * @author Jeroen Meulemeester
 */
public interface ServiceLocationInfo {

	/**
	 * Get the unique service location identifier
	 *
	 * @return The service location id
	 */
	long getId();

	/**
	 * Get the name of the service location (eg. Home, My place, ...)
	 *
	 * @return A human readable name of the service location
	 */
	String getName();

	/**
	 * Get the time zone of the service location
	 *
	 * @return The service location time zone
	 */
	TimeZone getTimeZone();

	/**
	 * Get the exact coordinates of the service location
	 *
	 * @return The location
	 */
	Location getLocation();

	/**
	 * The current electricity price
	 *
	 * @return The electricity price
	 */
	double getElectricityCost();

	/**
	 * Get the currency of the electricity cost
	 *
	 * @return The currency of the electricity cost
	 */
	Currency getElectricityCurrency();

	/**
	 * Get the list of appliances in this service location
	 *
	 * @return The list of appliances
	 */
	List<Appliance> getAppliances();

	/**
	 * Get the list of actuators in this service location
	 *
	 * @return The list of actuators
	 */
	List<Actuator> getActuators();

	/**
	 * Get the list of sensors in this service location
	 *
	 * @return The list of sensors
	 */
	List<Sensor> getSensors();

}
