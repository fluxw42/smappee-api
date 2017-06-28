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
	 * @param serviceLocation   The an accessible service location
	 * @param from              The UTC stamp in indicating the beginning of the queried period
	 * @param to                The UTC stamp indicating the end of the queried period
	 * @param aggregationPeriod The aggregation period (5 min values are only available for the last 14 days)
	 * @return The consumption intervals
	 */
	List<Consumption> getConsumption(final ServiceLocation serviceLocation, final Date from, final Date to, final AggregationPeriod aggregationPeriod);

}
