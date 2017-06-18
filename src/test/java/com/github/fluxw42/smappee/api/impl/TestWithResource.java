package com.github.fluxw42.smappee.api.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Date: 6/18/17 - 8:32 PM
 *
 * @author Jeroen Meulemeester
 */
public class TestWithResource {

	protected byte[] getResource(final String name) throws IOException {
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		final byte[] buffer = new byte[1024];
		int bytesRead;
		try (final InputStream in = getClass().getResourceAsStream(name)) {
			while ((bytesRead = in.read(buffer)) >= 0) {
				if (bytesRead > 0) {
					out.write(buffer, 0, bytesRead);
				}
			}
		}
		return out.toByteArray();
	}


}
