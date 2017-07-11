package com.github.fluxw42.smappee.api.impl;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

/**
 * Date: 7/11/17 - 9:20 PM
 *
 * @author Jeroen Meulemeester
 */
public class ConsumptionImplTest {

	@Test
	public void equalsContract() {
		EqualsVerifier.forClass(ConsumptionImpl.class).verify();
	}

}