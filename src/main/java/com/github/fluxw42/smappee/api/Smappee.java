package com.github.fluxw42.smappee.api;

import java.io.Closeable;
import java.util.Date;
import java.util.List;

/**
 * Date: 6/14/17 - 10:54 PM
 *
 * @author Jeroen Meulemeester
 */
public interface Smappee extends Closeable {

	/**
	 * Get the name of the connected application
	 *
	 * @return The application name
	 */
	String getApplicationName();

	/**
	 * Get all available service locations
	 *
	 * @return An unmodifiable list of all available service locations
	 */
	List<ServiceLocation> getServiceLocations();

	/**
	 * Get all available information about one particular service location
	 *
	 * @return The service location info
	 */
	ServiceLocationInfo getServiceLocationInfo(final ServiceLocation serviceLocation);

	/**
	 * Get the consumption of the given service location
	 *
	 * @param serviceLocation   The service location
	 * @param from              The UTC stamp in indicating the beginning of the queried period
	 * @param to                The UTC stamp indicating the end of the queried period
	 * @param aggregationPeriod The aggregation period (5 min values are only available for the last 14 days)
	 * @return The consumption intervals
	 */
	List<Consumption> getConsumption(final ServiceLocation serviceLocation, final Date from, final Date to, final AggregationPeriod aggregationPeriod);

	/**
	 * Get the events of one or more appliances
	 *
	 * @param serviceLocation the service location the appliances belong to
	 * @param from            The UTC stamp in indicating the beginning of the queried period
	 * @param to              The UTC stamp indicating the end of the queried period
	 * @param maxEntries      The maximum number of events that should be returned by this query
	 * @param appliances      The set of appliances to get events for
	 * @return The list of appliance events
	 */
	List<Event> getEvents(final ServiceLocation serviceLocation, final Date from, final Date to, final int maxEntries, final Appliance... appliances);

	/**
	 * Set the state of the actuator to the given value
	 *
	 * @param serviceLocation The service location the actuator belongs to
	 * @param actuator        The actual actuator that should get the new state
	 * @param state           The new state where <tt>true</tt> is enabled, <tt>false</tt> disabled
	 * @param duration        The duration of the new state
	 * @return <tt>true</tt> when successful, <tt>false</tt> if not
	 */
	boolean setActuatorState(final ServiceLocation serviceLocation, final Actuator actuator, final boolean state, final StateDuration duration);

	/**
	 * Get the consumption of the given sensor
	 *
	 * @param serviceLocation   The service location which the sensor belongs to
	 * @param sensor            The sensor
	 * @param from              The UTC stamp in indicating the beginning of the queried period
	 * @param to                The UTC stamp indicating the end of the queried period
	 * @param aggregationPeriod The aggregation period (5 min values are only available for the last 14 days)
	 * @return The sensor record intervals
	 */
	List<SensorRecord> getSensorConsumption(final ServiceLocation serviceLocation, final Sensor sensor, final Date from, final Date to, final AggregationPeriod aggregationPeriod);

}
