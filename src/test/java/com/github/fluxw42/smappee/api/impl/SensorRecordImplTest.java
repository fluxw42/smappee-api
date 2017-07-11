package com.github.fluxw42.smappee.api.impl;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

/**
 * Date: 7/11/17 - 9:19 PM
 *
 * @author Jeroen Meulemeester
 */
public class SensorRecordImplTest {

	@Test
	public void equalsContract() {
		EqualsVerifier.forClass(SensorRecordImpl.class).verify();
	}

}