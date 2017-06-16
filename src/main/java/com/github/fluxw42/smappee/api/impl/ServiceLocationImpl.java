package com.github.fluxw42.smappee.api.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.fluxw42.smappee.api.ServiceLocation;

/**
 * Date: 6/14/17 - 12:22 AM
 *
 * @author Jeroen Meulemeester
 */
public class ServiceLocationImpl implements ServiceLocation {

	/**
	 * The unique service location identifier
	 */
	private final long id;

	/**
	 * A human readable name for the service location
	 */
	private final String name;

	/**
	 * Create a new service location instance
	 *
	 * @param name The name of the service location
	 * @param id   The unique service location identifier
	 */
	@JsonCreator
	public ServiceLocationImpl(@JsonProperty("name") final String name, @JsonProperty("serviceLocationId") final long id) {
		this.name = name;
		this.id = id;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String getName() {
		return this.name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final long getId() {
		return this.id;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("ServiceLocation{");
		sb.append("name='").append(name).append('\'');
		sb.append(", id=").append(id);
		sb.append('}');
		return sb.toString();
	}
}
