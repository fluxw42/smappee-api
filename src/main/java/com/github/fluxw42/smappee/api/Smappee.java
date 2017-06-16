package com.github.fluxw42.smappee.api;

import java.io.Closeable;
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
	
}
