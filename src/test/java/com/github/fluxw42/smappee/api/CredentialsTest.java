package com.github.fluxw42.smappee.api;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Date: 6/16/17 - 11:28 PM
 *
 * @author Jeroen Meulemeester
 */
public class CredentialsTest {

	@Test
	public void fromValues() throws Exception {
		final String userName = "user";
		final String password = "pass";

		assertNull(Credentials.fromValues(null, password).getUserName());
		assertEquals(userName, Credentials.fromValues(userName, password).getUserName());

		assertNull(Credentials.fromValues(userName, null).getPassword());
		assertEquals(password, Credentials.fromValues(userName, password).getPassword());
	}

}