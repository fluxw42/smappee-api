package com.github.fluxw42.smappee.api.impl;


import com.github.fluxw42.smappee.api.AuthorizationCallback;
import com.github.fluxw42.smappee.api.OAuthStorage;
import com.github.fluxw42.smappee.api.ServiceLocation;
import com.github.fluxw42.smappee.api.ServiceLocationInfo;
import com.github.fluxw42.smappee.api.Smappee;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.List;
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

}
