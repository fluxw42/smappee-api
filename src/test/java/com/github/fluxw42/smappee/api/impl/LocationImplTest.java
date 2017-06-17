package com.github.fluxw42.smappee.api.impl;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Date: 6/17/17 - 7:45 AM
 *
 * @author Jeroen Meulemeester
 */
public class LocationImplTest {

	@Test
	public void fromValues() throws Exception {
		final double latitude = 50.8503;
		final double longitude = 4.3517;

		final LocationImpl location = new LocationImpl(latitude, longitude);
		assertEquals(latitude, location.getLatitude(), 0.0);
		assertEquals(longitude, location.getLongitude(), 0.0);
	}

}