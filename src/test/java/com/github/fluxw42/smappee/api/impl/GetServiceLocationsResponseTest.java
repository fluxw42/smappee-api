package com.github.fluxw42.smappee.api.impl;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Date: 6/16/17 - 11:46 PM
 *
 * @author Jeroen Meulemeester
 */
public class GetServiceLocationsResponseTest {

	@Test
	public void testNullName() throws Exception {
		assertEquals("", new GetServiceLocationsResponse(null, new ArrayList<>()).getAppName());
	}

	@Test
	public void testNullLocations() throws Exception {
		final GetServiceLocationsResponse response = new GetServiceLocationsResponse("appName", null);
		assertNotNull(response.getServiceLocations());
		assertTrue(response.getServiceLocations().isEmpty());
	}

}