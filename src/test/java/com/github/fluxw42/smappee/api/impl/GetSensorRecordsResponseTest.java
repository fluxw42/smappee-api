package com.github.fluxw42.smappee.api.impl;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

/**
 * Date: 7/11/17 - 9:11 PM
 *
 * @author Jeroen Meulemeester
 */
public class GetSensorRecordsResponseTest {

	@Test
	public void equalsContract() {
		EqualsVerifier.forClass(GetSensorRecordsResponse.class)
				.withNonnullFields("sensorRecords")
				.verify();
	}

}