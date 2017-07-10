package com.github.fluxw42.smappee.api;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Date: 7/10/17 - 9:56 PM
 *
 * @author Jeroen Meulemeester
 */
public class StateDurationTest {

	@Test
	public void getDuration() throws Exception {
		assertTrue(StateDuration.INFINITE.getDuration().isNegative());
		assertEquals(5L, StateDuration.FIVE_MINUTES.getDuration().toMinutes());
		assertEquals(15L, StateDuration.FIFTEEN_MINUTES.getDuration().toMinutes());
		assertEquals(30L, StateDuration.HALF_HOUR.getDuration().toMinutes());
		assertEquals(60L, StateDuration.HOUR.getDuration().toMinutes());
	}

}