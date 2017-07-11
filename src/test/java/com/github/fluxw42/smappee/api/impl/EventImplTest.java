package com.github.fluxw42.smappee.api.impl;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Date: 7/11/17 - 9:15 PM
 *
 * @author Jeroen Meulemeester
 */
public class EventImplTest {

	@Test
	public void equalsContract() {
		EqualsVerifier.forClass(EventImpl.class).verify();
	}


}