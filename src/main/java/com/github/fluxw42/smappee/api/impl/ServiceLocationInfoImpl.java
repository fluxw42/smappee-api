package com.github.fluxw42.smappee.api.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.fluxw42.smappee.api.Actuator;
import com.github.fluxw42.smappee.api.Appliance;
import com.github.fluxw42.smappee.api.Location;
import com.github.fluxw42.smappee.api.ServiceLocationInfo;

import java.util.Collections;
import java.util.Currency;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

/**
 * Date: 6/17/17 - 7:56 AM
 *
 * @author Jeroen Meulemeester
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServiceLocationInfoImpl implements ServiceLocationInfo {

	private final long id;
	private final String name;
	private final TimeZone timeZone;
	private final Location location;
	private final double electricityCost;
	private final Currency electricityCurrency;
	private final List<Appliance> appliances;
	private final List<Actuator> actuators;

	/**
	 * Create a new info instance using the given values
	 *
	 * @param id                  The unique service location identifier
	 * @param name                The service location name
	 * @param timeZone            The time zone the service location is in
	 * @param lat                 The latitude of the service location
	 * @param lon                 The longitude of the service location
	 * @param electricityCost     The electricity price
	 * @param electricityCurrency The unit of the electricity price
	 * @param appliances          The list of appliances available in this service location
	 * @param actuators           The list of actuators available in this service location
	 */
	@JsonCreator
	public ServiceLocationInfoImpl(
			@JsonProperty("serviceLocationId") final long id,
			@JsonProperty("name") final String name,
			@JsonProperty("timezone") final TimeZone timeZone,
			@JsonProperty("lat") final double lat,
			@JsonProperty("lon") final double lon,
			@JsonProperty("electricityCost") final double electricityCost,
			@JsonProperty("electricityCurrency") final Currency electricityCurrency,
			@JsonProperty("appliances") final List<ApplianceImpl> appliances,
			@JsonProperty("actuators") final List<ActuatorImpl> actuators) {

		this.id = id;
		this.name = name;
		this.timeZone = timeZone;

		this.location = new LocationImpl(lat, lon);
		this.electricityCost = electricityCost;
		this.electricityCurrency = electricityCurrency;
		this.appliances = Collections.unmodifiableList(Optional.ofNullable(appliances).orElse(Collections.emptyList()));
		this.actuators = Collections.unmodifiableList(Optional.ofNullable(actuators).orElse(Collections.emptyList()));
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
	public final String getName() {
		return this.name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final TimeZone getTimeZone() {
		return this.timeZone;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final Location getLocation() {
		return this.location;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final double getElectricityCost() {
		return this.electricityCost;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final Currency getElectricityCurrency() {
		return this.electricityCurrency;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final List<Appliance> getAppliances() {
		return this.appliances;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Actuator> getActuators() {
		return this.actuators;
	}

	@Override
	public final String toString() {
		final StringBuilder sb = new StringBuilder("ServiceLocationInfoImpl{");
		sb.append("id=").append(id);
		sb.append(", name='").append(name).append('\'');
		sb.append(", timeZone=").append(timeZone.getDisplayName());
		sb.append(", location=").append(location);
		sb.append(", electricityCost=").append(electricityCost);
		sb.append(", electricityCurrency=").append(electricityCurrency.getCurrencyCode());
		sb.append(", appliances=").append(appliances);
		sb.append(", actuators=").append(actuators);
		sb.append('}');
		return sb.toString();
	}

}
