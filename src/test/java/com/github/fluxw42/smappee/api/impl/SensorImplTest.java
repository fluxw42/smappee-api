package com.github.fluxw42.smappee.api.impl;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Date: 6/18/17 - 12:56 PM
 *
 * @author Jeroen Meulemeester
 */
public class SensorImplTest {

	@Test
	public void equalsContract() {
		EqualsVerifier.forClass(SensorImpl.class).verify();
	}

}