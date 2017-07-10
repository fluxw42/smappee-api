package com.github.fluxw42.smappee.api.impl;

import com.github.fluxw42.smappee.api.Actuator;
import com.github.fluxw42.smappee.api.AggregationPeriod;
import com.github.fluxw42.smappee.api.Appliance;
import com.github.fluxw42.smappee.api.AuthorizationCallback;
import com.github.fluxw42.smappee.api.Consumption;
import com.github.fluxw42.smappee.api.Event;
import com.github.fluxw42.smappee.api.OAuthStorage;
import com.github.fluxw42.smappee.api.ServiceLocation;
import com.github.fluxw42.smappee.api.StateDuration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.matching;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
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

	@Test
	public void testEvents() throws Exception {
		stubFor(get(urlEqualTo("/servicelocation/123/events?applianceId=432&applianceId=472&maxNumber=10&from=1388534400000&to=1391212800000"))
				.withHeader("Authorization", equalTo("Bearer access-token-value"))
				.willReturn(aResponse()
						.withStatus(200)
						.withHeader("Content-Type", "application/json")
						.withBody(getResource("GetEvents.json"))));

		final AuthorizationCallback authorizationCallback = mock(AuthorizationCallback.class);
		final OAuthStorage oAuthStorage = mock(OAuthStorage.class);
		when(oAuthStorage.getAccessToken()).thenReturn("access-token-value");

		final String baseUrl = "http://localhost:" + this.wireMockRule.port();

		try (final SmappeeImpl api = new SmappeeImpl(authorizationCallback, oAuthStorage, baseUrl)) {
			final ServiceLocation serviceLocation = mock(ServiceLocation.class);
			when(serviceLocation.getId()).thenReturn(123L);

			final Appliance appliance1 = mock(Appliance.class);
			when(appliance1.getId()).thenReturn(432L);

			final Appliance appliance2 = mock(Appliance.class);
			when(appliance2.getId()).thenReturn(472L);

			final List<Event> events = api.getEvents(
					serviceLocation,
					new Date(1388534400000L),
					new Date(1391212800000L),
					10,
					appliance1,
					appliance2
			);

			assertNotNull(events);
			assertEquals(2, events.size());
			assertEquals(new EventImpl(432L, new Date(1391212532100L), 340L), events.get(0));
			assertEquals(new EventImpl(472L, new Date(1389215612400L), -120L), events.get(1));

			verifyZeroInteractions(authorizationCallback);
			verify(oAuthStorage, atLeastOnce()).getAccessToken();
			verifyNoMoreInteractions(oAuthStorage);
		}

	}

	@Test
	public void testSetActuatorState() throws Exception {
		stubFor(post(urlMatching("/servicelocation/123/actuator/472/(on|off)"))
				.withHeader("Authorization", equalTo("Bearer access-token-value"))
				.withRequestBody(matching("\\{\"duration\":-?\\d+\\}"))
				.willReturn(aResponse()
						.withStatus(200)
						.withHeader("Content-Type", "application/json")
						.withBody("")));

		stubFor(post(urlMatching("/servicelocation/123/actuator/999/(on|off)"))
				.withHeader("Authorization", equalTo("Bearer access-token-value"))
				.withRequestBody(matching("\\{\"duration\":-?\\d+\\}"))
				.willReturn(aResponse()
						.withStatus(504)
						.withHeader("Content-Type", "application/json")
						.withBody("")));

		final AuthorizationCallback authorizationCallback = mock(AuthorizationCallback.class);
		final OAuthStorage oAuthStorage = mock(OAuthStorage.class);
		when(oAuthStorage.getAccessToken()).thenReturn("access-token-value");

		final String baseUrl = "http://localhost:" + this.wireMockRule.port();

		try (final SmappeeImpl api = new SmappeeImpl(authorizationCallback, oAuthStorage, baseUrl)) {
			final ServiceLocation serviceLocation = mock(ServiceLocation.class);
			when(serviceLocation.getId()).thenReturn(123L);

			final Actuator actuator1 = mock(Actuator.class);
			when(actuator1.getId()).thenReturn(472L);

			final Actuator actuator2 = mock(Actuator.class);
			when(actuator2.getId()).thenReturn(999L);

			assertTrue(api.setActuatorState(serviceLocation, actuator1, true, StateDuration.HALF_HOUR));
			assertTrue(api.setActuatorState(serviceLocation, actuator1, false, StateDuration.HALF_HOUR));
			assertTrue(api.setActuatorState(serviceLocation, actuator1, true, StateDuration.INFINITE));
			assertFalse(api.setActuatorState(serviceLocation, actuator2, true, StateDuration.HALF_HOUR));

			verifyZeroInteractions(authorizationCallback);
			verify(oAuthStorage, atLeastOnce()).getAccessToken();
			verifyNoMoreInteractions(oAuthStorage);
		}
	}
}