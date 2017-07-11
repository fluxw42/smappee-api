package com.github.fluxw42.smappee.api.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.fluxw42.smappee.api.SensorRecord;

import java.util.Date;
import java.util.Objects;

/**
 * Date: 7/11/17 - 7:47 PM
 *
 * @author Jeroen Meulemeester
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SensorRecordImpl implements SensorRecord {

	/**
	 * The timestamp of the record
	 */
	private final Date timeStamp;

	/**
	 * The first recorded value
	 */
	private final double value1;

	/**
	 * The second recorded value
	 */
	private final double value2;

	/**
	 * The temperature of the sensor
	 */
	private final double temperature;

	/**
	 * The humidity of the sensor
	 */
	private final double humidity;

	/**
	 * The battery level
	 */
	private final double batteryLevel;

	/**
	 * Create a new record with the given values
	 *
	 * @param timeStamp    The timestamp of the record
	 * @param value1       The first recorded value
	 * @param value2       The second recorded value
	 * @param temperature  The temperature
	 * @param humidity     The humidity level
	 * @param batteryLevel The battery level
	 */
	@JsonCreator
	public SensorRecordImpl(@JsonProperty("timestamp") final Date timeStamp,
							@JsonProperty("value1") final double value1,
							@JsonProperty("value2") final double value2,
							@JsonProperty("temperature") final double temperature,
							@JsonProperty("humidity") final double humidity,
							@JsonProperty("battery") final double batteryLevel) {
		this.timeStamp = Objects.requireNonNull(timeStamp);
		this.value1 = value1;
		this.value2 = value2;
		this.temperature = temperature;
		this.humidity = humidity;
		this.batteryLevel = batteryLevel;
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
	public final double getValue1() {
		return this.value1;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final double getValue2() {
		return this.value2;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final double getTemperature() {
		return this.temperature;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final double getHumidity() {
		return this.humidity;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final double getBatteryLevel() {
		return this.batteryLevel;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof SensorRecordImpl)) {
			return false;
		}
		final SensorRecordImpl that = (SensorRecordImpl) o;
		return Double.compare(that.getValue1(), getValue1()) == 0 &&
				Double.compare(that.getValue2(), getValue2()) == 0 &&
				Double.compare(that.getTemperature(), getTemperature()) == 0 &&
				Double.compare(that.getHumidity(), getHumidity()) == 0 &&
				Double.compare(that.getBatteryLevel(), getBatteryLevel()) == 0 &&
				Objects.equals(getTimeStamp(), that.getTimeStamp());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int hashCode() {
		return Objects.hash(getTimeStamp(), getValue1(), getValue2(), getTemperature(), getHumidity(), getBatteryLevel());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String toString() {
		final StringBuilder sb = new StringBuilder("SensorRecordImpl{");
		sb.append("timeStamp=").append(timeStamp);
		sb.append(", value1=").append(value1);
		sb.append(", value2=").append(value2);
		sb.append(", temperature=").append(temperature);
		sb.append(", humidity=").append(humidity);
		sb.append(", batteryLevel=").append(batteryLevel);
		sb.append('}');
		return sb.toString();
	}
}
