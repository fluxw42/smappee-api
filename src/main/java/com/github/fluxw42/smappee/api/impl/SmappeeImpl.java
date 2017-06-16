package com.github.fluxw42.smappee.api.impl;


import com.github.fluxw42.smappee.api.AuthorizationCallback;
import com.github.fluxw42.smappee.api.OAuthStorage;
import com.github.fluxw42.smappee.api.ServiceLocation;
import com.github.fluxw42.smappee.api.Smappee;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.List;

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
	 * Create a new {@link Smappee} instance
	 *
	 * @param authCallback The authorization callback, used to request the credentials of the user when needed
	 * @param oAuthStorage The OAuth storage used to store the API tokens so we don't need to ask the user for authorization again
	 */
	public SmappeeImpl(final AuthorizationCallback authCallback, final OAuthStorage oAuthStorage) {
		final OAuth2Filter oAuth2Filter = new OAuth2Filter(BASE_URL, authCallback, oAuthStorage);

		final ClientConfig clientConfig = new ClientConfig();
		clientConfig.register(JacksonFeature.class);

		this.client = ClientBuilder.newClient(clientConfig);
		this.client.register(oAuth2Filter);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void close() {
		this.client.close();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getApplicationName() {
		final GetServiceLocationsResponse serviceLocationResponse = this.client.target(BASE_URL)
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
	public List<ServiceLocation> getServiceLocations() {
		final GetServiceLocationsResponse serviceLocationResponse = this.client.target(BASE_URL)
				.path("servicelocation")
				.request(MediaType.APPLICATION_JSON)
				.get()
				.readEntity(GetServiceLocationsResponse.class);

		return Collections.unmodifiableList(serviceLocationResponse.getServiceLocations());
	}

}
