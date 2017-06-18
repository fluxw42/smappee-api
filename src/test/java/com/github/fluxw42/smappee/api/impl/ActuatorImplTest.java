package com.github.fluxw42.smappee.api.impl;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

/**
 * Date: 6/18/17 - 11:39 AM
 *
 * @author Jeroen Meulemeester
 */
public class ActuatorImplTest {

	@Test
	public void equalsContract() {
		EqualsVerifier.forClass(ActuatorImpl.class).verify();
	}

}