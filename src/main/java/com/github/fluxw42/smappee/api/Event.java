package com.github.fluxw42.smappee.api;

import java.util.Date;

/**
 * Date: 6/29/17 - 9:56 PM
 *
 * @author Jeroen Meulemeester
 */
public interface Event {

	/**
	 * Get the identifier of the appliance this event if related to
	 *
	 * @return The appliance identifier
	 */
	long getApplianceId();

	/**
	 * Get the timestamp of the event
	 *
	 * @return The timestamp of the event
	 */
	Date getTimeStamp();

	/**
	 * Get the active power value of the event
	 *
	 * @return The active power
	 */
	long getActivePower();

}
