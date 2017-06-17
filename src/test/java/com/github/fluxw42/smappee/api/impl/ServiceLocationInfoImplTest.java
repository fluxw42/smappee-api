package com.github.fluxw42.smappee.api.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.github.fluxw42.smappee.api.Appliance;
import org.junit.Test;

import java.util.Currency;
import java.util.List;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Date: 6/17/17 - 8:10 PM
 *
 * @author Jeroen Meulemeester
 */
public class ServiceLocationInfoImplTest {

	@Test
	public void testFromJsonResponse() throws Exception {
		final ObjectMapper objectMapper = new ObjectMapper();
		final ObjectReader objectReader = objectMapper.readerFor(ServiceLocationInfoImpl.class);
		final ServiceLocationInfoImpl info = objectReader.readValue(getClass().getResourceAsStream("ServiceLocationInfo.json"));

		assertNotNull(info);
		assertNotNull(info.toString());
		assertEquals(1, info.getId());
		assertEquals("My Place", info.getName());

		assertEquals(new LocationImpl(0.0, 0.0), info.getLocation());
		assertEquals(TimeZone.getTimeZone("Europe/Brussels"), info.getTimeZone());
		assertEquals(0.0, info.getElectricityCost(), 0.0);
		assertEquals(Currency.getInstance("EUR"), info.getElectricityCurrency());

		final List<Appliance> appliances = info.getAppliances();
		assertNotNull(appliances);
		assertEquals(2, appliances.size());
		assertEquals(new ApplianceImpl(1, "Coffeemaker", "cooking"), appliances.get(0));
		assertEquals(new ApplianceImpl(2, "Refrigerator", "refrigeration"), appliances.get(1));

	}
}