package com.github.fluxw42.smappee.api.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.fluxw42.smappee.api.SensorRecord;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Date: 6/14/17 - 12:21 AM
 *
 * @author Jeroen Meulemeester
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetSensorRecordsResponse {

	/**
	 * The service location id
	 */
	private final long serviceLocationId;

	/**
	 * The sensor id
	 */
	private final long sensorId;

	/**
	 * The consumption data
	 */
	private final List<SensorRecord> sensorRecords;

	/**
	 * Create a new instance with the given values
	 *
	 * @param serviceLocationId The id of the service location
	 * @param sensorId          The id of the sensor
	 * @param records           The sensor record interval data
	 */
	@JsonCreator
	public GetSensorRecordsResponse(
			@JsonProperty("serviceLocationId") final long serviceLocationId,
			@JsonProperty("sensorId") final long sensorId,
			@JsonProperty("records") final List<SensorRecordImpl> records) {
		this.serviceLocationId = serviceLocationId;
		this.sensorId = sensorId;
		this.sensorRecords = Collections.unmodifiableList(Optional.ofNullable(records).orElse(Collections.emptyList()));
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
	 * Get the sensor identifier
	 *
	 * @return The sensor id
	 */
	public final long getSensorId() {
		return this.sensorId;
	}

	/**
	 * Get the list of sensor record intervals
	 *
	 * @return The sensor records
	 */
	public final List<SensorRecord> getSensorRecords() {
		return Collections.unmodifiableList(this.sensorRecords);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof GetSensorRecordsResponse)) {
			return false;
		}
		final GetSensorRecordsResponse that = (GetSensorRecordsResponse) o;
		return getServiceLocationId() == that.getServiceLocationId() &&
				getSensorId() == that.getSensorId() &&
				Objects.equals(getSensorRecords(), that.getSensorRecords());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int hashCode() {
		return Objects.hash(getServiceLocationId(), getSensorId(), getSensorRecords());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String toString() {
		final StringBuilder sb = new StringBuilder("GetSensorRecordsResponse{");
		sb.append("serviceLocationId=").append(serviceLocationId);
		sb.append(", sensorId=").append(sensorId);
		sb.append(", sensorRecords=").append(sensorRecords);
		sb.append('}');
		return sb.toString();
	}
}
