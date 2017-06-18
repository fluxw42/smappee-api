package com.github.fluxw42.smappee.api.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.fluxw42.smappee.api.Channel;

import java.util.Objects;

/**
 * Date: 6/18/17 - 12:10 PM
 *
 * @author Jeroen Meulemeester
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChannelImpl implements Channel {

	/**
	 * The channel number
	 */
	private final long channelNr;

	/**
	 * The name of the channel
	 */
	private final String name;

	/**
	 * The number of pulses per unit
	 */
	private final double pulsesPerUnit;

	/**
	 * The unit of measurement
	 */
	private final String unitOfMeasurement;

	/**
	 * Indicates if this channel is enabled or not
	 */
	private final boolean enabled;

	/**
	 * The channel type
	 */
	private final String type;

	/**
	 * Create a new channel instance with the given values
	 *
	 * @param channelNr         The channel number
	 * @param name              The name of the channel
	 * @param pulsesPerUnit     The number of pulses per unit
	 * @param unitOfMeasurement The unit of measurement
	 * @param enabled           The status of the channel (enabled or not)
	 * @param type              The channel type
	 */
	@JsonCreator
	public ChannelImpl(@JsonProperty("channel") final long channelNr,
					   @JsonProperty("name") final String name,
					   @JsonProperty("ppu") final double pulsesPerUnit,
					   @JsonProperty("uom") final String unitOfMeasurement,
					   @JsonProperty("enabled") final boolean enabled,
					   @JsonProperty("type") final String type) {

		this.channelNr = channelNr;
		this.name = name;
		this.pulsesPerUnit = pulsesPerUnit;
		this.unitOfMeasurement = unitOfMeasurement;
		this.enabled = enabled;
		this.type = type;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final long getChannelNr() {
		return this.channelNr;
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
	public final double getPulsesPerUnit() {
		return this.pulsesPerUnit;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String getUnitOfMeasurement() {
		return this.unitOfMeasurement;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean isEnabled() {
		return this.enabled;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String getType() {
		return this.type;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof ChannelImpl)) {
			return false;
		}
		final ChannelImpl channel = (ChannelImpl) o;
		return getChannelNr() == channel.getChannelNr() &&
				Double.compare(channel.getPulsesPerUnit(), getPulsesPerUnit()) == 0 &&
				isEnabled() == channel.isEnabled() &&
				Objects.equals(getName(), channel.getName()) &&
				Objects.equals(getUnitOfMeasurement(), channel.getUnitOfMeasurement()) &&
				Objects.equals(getType(), channel.getType());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int hashCode() {
		return Objects.hash(getChannelNr(), getName(), getPulsesPerUnit(), getUnitOfMeasurement(), isEnabled(), getType());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String toString() {
		final StringBuilder sb = new StringBuilder("ChannelImpl{");
		sb.append("channelNr=").append(channelNr);
		sb.append(", name='").append(name).append('\'');
		sb.append(", type='").append(type).append('\'');
		sb.append(", pulsesPerUnit=").append(pulsesPerUnit);
		sb.append(", unitOfMeasurement='").append(unitOfMeasurement).append('\'');
		sb.append(", enabled=").append(enabled);
		sb.append('}');
		return sb.toString();
	}
}
