package com.github.fluxw42.smappee.api.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.fluxw42.smappee.api.Channel;
import com.github.fluxw42.smappee.api.Sensor;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Date: 6/18/17 - 12:05 PM
 *
 * @author Jeroen Meulemeester
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public final class SensorImpl implements Sensor {

	/**
	 * The unique sensor id
	 */
	private final long id;

	/**
	 * The sensor name
	 */
	private final String name;

	/**
	 * The unmodifiable list of sensor channels
	 */
	private final List<Channel> channels;

	/**
	 * Create a new sensor instance using the given values
	 *
	 * @param id       The sensor identifier
	 * @param name     The sensor name
	 * @param channels The list of sensor channels
	 */
	@JsonCreator
	public SensorImpl(@JsonProperty("id") final long id, @JsonProperty("name") final String name, @JsonProperty("channels") final List<ChannelImpl> channels) {
		this.id = id;
		this.name = name;
		this.channels = Collections.unmodifiableList(Optional.ofNullable(channels).orElse(Collections.emptyList()));
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
	public final List<Channel> getChannels() {
		return this.channels;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof SensorImpl)) {
			return false;
		}
		final SensorImpl sensor = (SensorImpl) o;
		return getId() == sensor.getId() &&
				Objects.equals(getName(), sensor.getName()) &&
				Objects.equals(getChannels(), sensor.getChannels());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int hashCode() {
		return Objects.hash(getId(), getName(), getChannels());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String toString() {
		final StringBuilder sb = new StringBuilder("SensorImpl{");
		sb.append("id=").append(id);
		sb.append(", name='").append(name).append('\'');
		sb.append(", channels=").append(channels);
		sb.append('}');
		return sb.toString();
	}

}
