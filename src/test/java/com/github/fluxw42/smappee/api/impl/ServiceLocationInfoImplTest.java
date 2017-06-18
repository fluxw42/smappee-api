package com.github.fluxw42.smappee.api.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.github.fluxw42.smappee.api.Actuator;
import com.github.fluxw42.smappee.api.Appliance;
import com.github.fluxw42.smappee.api.Sensor;
import org.junit.Test;

import java.util.ArrayList;
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

		final List<Actuator> actuators = info.getActuators();
		assertNotNull(actuators);
		assertEquals(2, actuators.size());
		assertEquals(new ActuatorImpl(1, "TV plug"), actuators.get(0));
		assertEquals(new ActuatorImpl(2, "Office plug"), actuators.get(1));

		final List<Sensor> sensors = info.getSensors();
		assertNotNull(sensors);
		assertEquals(1, sensors.size());

		final List<ChannelImpl> channels = new ArrayList<>();
		channels.add(new ChannelImpl(1, "Garage", 100.0, "m3", false, "gas"));
		channels.add(new ChannelImpl(2, "Outdoor", 100.0, "m3", false, "water"));

		assertEquals(new SensorImpl(2, "3003000078", channels), sensors.get(0));

	}
}