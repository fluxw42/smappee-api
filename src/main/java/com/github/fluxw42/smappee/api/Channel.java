package com.github.fluxw42.smappee.api;

/**
 * Date: 6/18/17 - 11:58 AM
 *
 * @author Jeroen Meulemeester
 */
public interface Channel {

	/**
	 * Get the channel number
	 *
	 * @return The channel number
	 */
	long getChannelNr();

	/**
	 * Get the name of the channel
	 *
	 * @return The channel name
	 */
	String getName();

	/**
	 * Get the number of pulses per measured unit
	 *
	 * @return The number of pulses per unit
	 */
	double getPulsesPerUnit();

	/**
	 * The measurement unit (eg. m3, kW, l, ...)
	 *
	 * @return The measurement unit
	 */
	String getUnitOfMeasurement();

	/**
	 * Indicates if this channel is enabled or not
	 *
	 * @return <tt>true</tt> when enabled, <tt>false</tt> if not
	 */
	boolean isEnabled();

	/**
	 * Get the channel type (eg. 'gas', 'water', ...)
	 *
	 * @return The channel type
	 */
	String getType();

}
