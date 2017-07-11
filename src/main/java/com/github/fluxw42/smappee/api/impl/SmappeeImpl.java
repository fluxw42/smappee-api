package com.github.fluxw42.smappee.api.impl;


import com.github.fluxw42.smappee.api.Actuator;
import com.github.fluxw42.smappee.api.AggregationPeriod;
import com.github.fluxw42.smappee.api.Appliance;
import com.github.fluxw42.smappee.api.AuthorizationCallback;
import com.github.fluxw42.smappee.api.Consumption;
import com.github.fluxw42.smappee.api.Event;
import com.github.fluxw42.smappee.api.OAuthStorage;
import com.github.fluxw42.smappee.api.Sensor;
import com.github.fluxw42.smappee.api.SensorRecord;
import com.github.fluxw42.smappee.api.ServiceLocation;
import com.github.fluxw42.smappee.api.ServiceLocationInfo;
import com.github.fluxw42.smappee.api.Smappee;
import com.github.fluxw42.smappee.api.StateDuration;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Date: 6/13/17 - 9:36 PM
 *
 * @author Jeroen Meulemeester
 */
public class SmappeeImpl implements Smappee {

	/**
	 * The base URL of the Smappee API ()
	 */
	private static final String BASE_URL = "https://app1pub.smappee.net/dev/v1";

	/**
	 * The REST client, used to request data from the Smappee API
	 */
	private final Client client;

	/**
	 * The smappee API base URL
	 */
	private final String baseUrl;

	/**
	 * Create a new {@link Smappee} instance, using the default base URL
	 *
	 * @param authCallback The authorization callback, used to request the credentials of the user when needed
	 * @param oAuthStorage The OAuth storage used to store the API tokens so we don't need to ask the user for authorization again
	 */
	public SmappeeImpl(final AuthorizationCallback authCallback, final OAuthStorage oAuthStorage) {
		this(authCallback, oAuthStorage, BASE_URL);
	}

	/**
	 * Create a new {@link Smappee} instance, using a specific base URL
	 *
	 * @param authCallback The authorization callback, used to request the credentials of the user when needed
	 * @param oAuthStorage The OAuth storage used to store the API tokens so we don't need to ask the user for authorization again
	 * @param baseUrl      The base url
	 */
	public SmappeeImpl(final AuthorizationCallback authCallback, final OAuthStorage oAuthStorage, final String baseUrl) {
		final OAuth2Filter oAuth2Filter = new OAuth2Filter(BASE_URL, authCallback, oAuthStorage);

		final ClientConfig clientConfig = new ClientConfig();
		clientConfig.register(JacksonFeature.class);

		this.client = ClientBuilder.newClient(clientConfig);
		this.client.register(oAuth2Filter);

		this.baseUrl = Objects.requireNonNull(baseUrl);
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void close() {
		this.client.close();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String getApplicationName() {
		final GetServiceLocationsResponse serviceLocationResponse = this.client.target(this.baseUrl)
				.path("servicelocation")
				.request(MediaType.APPLICATION_JSON)
				.get()
				.readEntity(GetServiceLocationsResponse.class);

		return serviceLocationResponse.getAppName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final List<ServiceLocation> getServiceLocations() {
		final GetServiceLocationsResponse serviceLocationResponse = this.client.target(this.baseUrl)
				.path("servicelocation")
				.request(MediaType.APPLICATION_JSON)
				.get()
				.readEntity(GetServiceLocationsResponse.class);

		return Collections.unmodifiableList(serviceLocationResponse.getServiceLocations());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final ServiceLocationInfo getServiceLocationInfo(final ServiceLocation serviceLocation) {
		return this.client.target(this.baseUrl)
				.path("servicelocation/{id}/info")
				.resolveTemplate("id", serviceLocation.getId())
				.request(MediaType.APPLICATION_JSON)
				.get()
				.readEntity(ServiceLocationInfoImpl.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final List<Consumption> getConsumption(final ServiceLocation serviceLocation, final Date from, final Date to, final AggregationPeriod aggregationPeriod) {
		return this.client.target(this.baseUrl)
				.path("servicelocation/{id}/consumption")
				.resolveTemplate("id", serviceLocation.getId())
				.queryParam("aggregation", aggregationPeriod.getId())
				.queryParam("from", from.getTime())
				.queryParam("to", to.getTime())
				.request(MediaType.APPLICATION_JSON)
				.get()
				.readEntity(GetConsumptionsResponse.class)
				.getConsumptions();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final List<Event> getEvents(final ServiceLocation serviceLocation, final Date from, final Date to, final int maxEntries, final Appliance... appliances) {

		if (appliances == null || appliances.length == 0) {
			return Collections.emptyList();
		}

		final Object[] applianceIds = Arrays.stream(appliances)
				.filter(Objects::nonNull)
				.map(Appliance::getId)
				.distinct()
				.toArray();

		final List<EventImpl> events = this.client.target(this.baseUrl)
				.path("servicelocation/{id}/events")
				.resolveTemplate("id", serviceLocation.getId())
				.queryParam("applianceId", applianceIds)
				.queryParam("maxNumber", maxEntries)
				.queryParam("from", Objects.requireNonNull(from).getTime())
				.queryParam("to", Objects.requireNonNull(to).getTime())
				.request(MediaType.APPLICATION_JSON)
				.get()
				.readEntity(new GenericType<List<EventImpl>>() {});

		return Collections.unmodifiableList(events);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean setActuatorState(final ServiceLocation serviceLocation, final Actuator actuator, final boolean state, final StateDuration duration) {
		final Map<Object, Object> data = new HashMap<>();
		data.put("duration", duration.getDuration().getSeconds());

		final Response.Status.Family family = this.client.target(this.baseUrl)
				.path("/servicelocation/{serviceLocationId}/actuator/{actuatorId}/{state}")
				.resolveTemplate("serviceLocationId", serviceLocation.getId())
				.resolveTemplate("actuatorId", actuator.getId())
				.resolveTemplate("state", state ? "on" : "off")
				.request(MediaType.APPLICATION_JSON)
				.post(Entity.json(data))
				.getStatusInfo()
				.getFamily();

		return family == Response.Status.Family.SUCCESSFUL;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final List<SensorRecord> getSensorConsumption(final ServiceLocation serviceLocation, final Sensor sensor, final Date from, final Date to, final AggregationPeriod aggregationPeriod) {
		return this.client.target(this.baseUrl)
				.path("servicelocation/{serviceLocationId}/sensor/{sensorId}/consumption")
				.resolveTemplate("serviceLocationId", serviceLocation.getId())
				.resolveTemplate("sensorId", sensor.getId())
				.queryParam("from", Objects.requireNonNull(from).getTime())
				.queryParam("to", Objects.requireNonNull(to).getTime())
				.queryParam("aggregation", aggregationPeriod.getId())
				.request(MediaType.APPLICATION_JSON)
				.get()
				.readEntity(GetSensorRecordsResponse.class)
				.getSensorRecords();
	}
}
