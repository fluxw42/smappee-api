package com.github.fluxw42.smappee.api.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.github.fluxw42.smappee.api.ServiceLocation;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Date: 6/16/17 - 11:46 PM
 *
 * @author Jeroen Meulemeester
 */
public class GetServiceLocationsResponseTest {

	@Test
	public void testFromJsonResponse() throws Exception {
		final ObjectMapper objectMapper = new ObjectMapper();
		final ObjectReader objectReader = objectMapper.readerFor(GetServiceLocationsResponse.class);
		final GetServiceLocationsResponse response = objectReader.readValue(getClass().getResourceAsStream("ServiceLocations.json"));

		assertNotNull(response);
		assertEquals("MyFirstApp", response.getAppName());

		final List<ServiceLocation> serviceLocations = response.getServiceLocations();
		assertNotNull(serviceLocations);
		assertEquals(2, serviceLocations.size());
		assertEquals(new ServiceLocationImpl("Home", 1), serviceLocations.get(0));
		assertEquals(new ServiceLocationImpl("Beach resort", 2), serviceLocations.get(1));
	}

}