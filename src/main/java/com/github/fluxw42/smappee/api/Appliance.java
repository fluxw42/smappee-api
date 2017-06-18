package com.github.fluxw42.smappee.api;

/**
 * Date: 6/17/17 - 7:42 PM
 *
 * @author Jeroen Meulemeester
 */
public interface Appliance {

	/**
	 * Get the unique id of this appliance
	 *
	 * @return The unique appliance identifier
	 */
	long getId();

	/**
	 * Get the name of the appliance (eg. Coffeemaker, Refrigerator, ...)
	 *
	 * @return A human readable name for the appliance
	 */
	String getName();

	/**
	 * The the type of the appliance (eg. Cooking, refrigeration, ...)
	 *
	 * @return The appliance type
	 */
	String getType();

}
