package com.github.fluxw42.smappee.api.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.fluxw42.smappee.api.Consumption;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Date: 6/14/17 - 12:21 AM
 *
 * @author Jeroen Meulemeester
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetConsumptionsResponse {

	/**
	 * The service location id
	 */
	private final long serviceLocationId;

	/**
	 * The consumption data
	 */
	private final List<Consumption> consumptions;

	/**
	 * Create a new instance with the given values
	 *
	 * @param serviceLocationId The id of the service location
	 * @param consumptions      The consumption interval data
	 */
	@JsonCreator
	public GetConsumptionsResponse(@JsonProperty("serviceLocationId") final long serviceLocationId, @JsonProperty("consumptions") final List<ConsumptionImpl> consumptions) {
		this.serviceLocationId = serviceLocationId;
		this.consumptions = Collections.unmodifiableList(Optional.ofNullable(consumptions).orElse(Collections.emptyList()));
	}

	/**
	 * Get the service location identifier
	 *
	 * @return The service location id
	 */
	public final long getServiceLocationId() {
		return this.serviceLocationId;
	}

	/**
	 * Get the list of consumption intervals
	 *
	 * @return The consumption data
	 */
	public final List<Consumption> getConsumptions() {
		return this.consumptions;
	}

}
