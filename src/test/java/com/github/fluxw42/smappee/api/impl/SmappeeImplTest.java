package com.github.fluxw42.smappee.api.impl;

import com.github.fluxw42.smappee.api.AggregationPeriod;
import com.github.fluxw42.smappee.api.AuthorizationCallback;
import com.github.fluxw42.smappee.api.Consumption;
import com.github.fluxw42.smappee.api.OAuthStorage;
import com.github.fluxw42.smappee.api.ServiceLocation;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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

	@Test
	public void testServiceLocations() throws Exception {
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
			final List<ServiceLocation> serviceLocations = api.getServiceLocations();
			assertNotNull(serviceLocations);
			assertEquals(2, serviceLocations.size());
			assertEquals(new ServiceLocationImpl("Home", 1), serviceLocations.get(0));
			assertEquals(new ServiceLocationImpl("Beach resort", 2), serviceLocations.get(1));

			verifyZeroInteractions(authorizationCallback);
			verify(oAuthStorage, atLeastOnce()).getAccessToken();
			verifyNoMoreInteractions(oAuthStorage);
		}
	}

	@Test
	public void testConsumption() throws Exception {
		stubFor(get(urlEqualTo("/servicelocation/123/consumption?aggregation=3&from=1388534400000&to=1391212800000"))
				.withHeader("Authorization", equalTo("Bearer access-token-value"))
				.willReturn(aResponse()
						.withStatus(200)
						.withHeader("Content-Type", "application/json")
						.withBody(getResource("GetConsumption.json"))));

		final AuthorizationCallback authorizationCallback = mock(AuthorizationCallback.class);
		final OAuthStorage oAuthStorage = mock(OAuthStorage.class);
		when(oAuthStorage.getAccessToken()).thenReturn("access-token-value");

		final String baseUrl = "http://localhost:" + this.wireMockRule.port();

		try (final SmappeeImpl api = new SmappeeImpl(authorizationCallback, oAuthStorage, baseUrl)) {
			final ServiceLocation serviceLocation = mock(ServiceLocation.class);
			when(serviceLocation.getId()).thenReturn(123L);
			
			final List<Consumption> consumptions = api.getConsumption(
					serviceLocation,
					new Date(1388534400000L),
					new Date(1391212800000L),
					AggregationPeriod.DAILY
			);
			
			assertNotNull(consumptions);
			assertEquals(3, consumptions.size());
			assertEquals(new ConsumptionImpl(new Date(1372672500000L), 23.0, 56.0, 12.0), consumptions.get(0));
			assertEquals(new ConsumptionImpl(new Date(1372672800000L), 67.0, 57.0, 12.0), consumptions.get(1));
			assertEquals(new ConsumptionImpl(new Date(1372673100000L), 88.0, 58.0, 12.0), consumptions.get(2));
			
			verifyZeroInteractions(authorizationCallback);
			verify(oAuthStorage, atLeastOnce()).getAccessToken();
			verifyNoMoreInteractions(oAuthStorage);
		}
	}

}