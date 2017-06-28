package com.github.fluxw42.smappee.api.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.fluxw42.smappee.api.Consumption;

import java.util.Date;
import java.util.Objects;

/**
 * Date: 6/28/17 - 9:32 PM
 *
 * @author Jeroen Meulemeester
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsumptionImpl implements Consumption {

	/**
	 * The timestamp of this consumption interval
	 */
	private final Date timeStamp;

	/**
	 * The consumption value
	 */
	private final double consumption;

	/**
	 * The solar value
	 */
	private final double solar;

	/**
	 * The 'always-on' value
	 */
	private final double alwaysOn;

	/**
	 * Create a new {@link Consumption} instance using the given values
	 *
	 * @param timeStamp   The timestamp of this consumption interval
	 * @param consumption The consumption value
	 * @param solar       The solar value
	 * @param alwaysOn    The 'always-on' value
	 */
	@JsonCreator
	public ConsumptionImpl(@JsonProperty("timestamp") final Date timeStamp,
						   @JsonProperty("consumption") final double consumption,
						   @JsonProperty("solar") final double solar,
						   @JsonProperty("alwaysOn") final double alwaysOn) {

		this.timeStamp = timeStamp;
		this.consumption = consumption;
		this.solar = solar;
		this.alwaysOn = alwaysOn;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final Date getTimeStamp() {
		return this.timeStamp;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final double getConsumption() {
		return this.consumption;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final double getSolar() {
		return this.solar;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final double getAlwaysOn() {
		return this.alwaysOn;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof ConsumptionImpl)) {
			return false;
		}
		final ConsumptionImpl that = (ConsumptionImpl) o;
		return Double.compare(that.getConsumption(), getConsumption()) == 0 &&
				Double.compare(that.getSolar(), getSolar()) == 0 &&
				Double.compare(that.getAlwaysOn(), getAlwaysOn()) == 0 &&
				Objects.equals(getTimeStamp(), that.getTimeStamp());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int hashCode() {
		return Objects.hash(getTimeStamp(), getConsumption(), getSolar(), getAlwaysOn());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String toString() {
		final StringBuilder sb = new StringBuilder("ConsumptionImpl{");
		sb.append("timeStamp=").append(timeStamp);
		sb.append(", consumption=").append(consumption);
		sb.append(", solar=").append(solar);
		sb.append(", alwaysOn=").append(alwaysOn);
		sb.append('}');
		return sb.toString();
	}
}
