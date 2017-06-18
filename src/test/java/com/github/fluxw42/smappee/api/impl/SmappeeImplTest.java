package com.github.fluxw42.smappee.api.impl;

import com.github.fluxw42.smappee.api.AuthorizationCallback;
import com.github.fluxw42.smappee.api.OAuthStorage;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * Date: 6/18/17 - 7:45 PM
 *
 * @author Jeroen Meulemeester
 */
public class SmappeeImplTest extends TestWithResource {

	@Rule
	public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().dynamicPort().dynamicHttpsPort());

	@Test
	public void testGetApplicationName() throws Exception {
		stubFor(get(urlEqualTo("/servicelocation"))
				.withHeader("Authorization", equalTo("Bearer access-token-value"))
				.willReturn(aResponse()
						.withStatus(200)
						.withHeader("Content-Type", "application/json")
						.withBody(getResource("ServiceLocations.json"))));

		final AuthorizationCallback authorizationCallback = mock(AuthorizationCallback.class);
		final OAuthStorage oAuthStorage = mock(OAuthStorage.class);
		when(oAuthStorage.getAccessToken()).thenReturn("access-token-value");

		final String baseUrl = "http://localhost:" + this.wireMockRule.port();

		try (final SmappeeImpl api = new SmappeeImpl(authorizationCallback, oAuthStorage, baseUrl)) {
			final String applicationName = api.getApplicationName();
			assertEquals("MyFirstApp", applicationName);
			verifyZeroInteractions(authorizationCallback);
			verify(oAuthStorage, atLeastOnce()).getAccessToken();
			verifyNoMoreInteractions(oAuthStorage);
		}
	}

}