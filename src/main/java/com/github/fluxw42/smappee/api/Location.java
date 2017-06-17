package com.github.fluxw42.smappee.api;

import com.github.fluxw42.smappee.api.impl.LocationImpl;

/**
 * Date: 6/17/17 - 7:39 AM
 *
 * @author Jeroen Meulemeester
 */
public interface Location {

	/**
	 * Get the latitude of this location. The latitude is the geographic coordinate that specifies the
	 * north–south position of a point on the Earth's surface.
	 *
	 * @return The latitude
	 */
	double getLatitude();

	/**
	 * Get the longitude of this location. The longitude is the geographic coordinate that specifies the
	 * east-west position of a point on the Earth's surface.
	 *
	 * @return The longitude
	 */
	double getLongitude();

}
