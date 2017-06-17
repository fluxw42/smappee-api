package com.github.fluxw42.smappee.api.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.fluxw42.smappee.api.ServiceLocation;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Date: 6/14/17 - 12:21 AM
 *
 * @author Jeroen Meulemeester
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetServiceLocationsResponse {

	/**
	 * Get the name of the application
	 */
	private final String appName;

	/**
	 * The list of service locations
	 */
	private final List<ServiceLocation> serviceLocations;

	/**
	 * Create a new instance with the given values
	 *
	 * @param appName          The application name
	 * @param serviceLocations The list of service locations
	 */
	@JsonCreator
	public GetServiceLocationsResponse(@JsonProperty("appName") final String appName, @JsonProperty("serviceLocations") final List<ServiceLocationImpl> serviceLocations) {
		this.appName = Optional.ofNullable(appName).orElse("");
		this.serviceLocations = Collections.unmodifiableList(Optional.ofNullable(serviceLocations).orElse(Collections.emptyList()));
	}

	/**
	 * Get the application name
	 *
	 * @return The name of the application
	 */
	public final String getAppName() {
		return this.appName;
	}

	/**
	 * Get the list of known service locations
	 *
	 * @return The service locations
	 */
	public final List<ServiceLocation> getServiceLocations() {
		return this.serviceLocations;
	}

}
