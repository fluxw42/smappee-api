package com.github.fluxw42.smappee.api.impl;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Date: 6/18/17 - 11:43 AM
 *
 * @author Jeroen Meulemeester
 */
public class ApplianceImplTest {

	@Test
	public void equalsContract() {
		EqualsVerifier.forClass(ApplianceImpl.class).verify();
	}

}