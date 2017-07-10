package com.github.fluxw42.smappee.api.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.fluxw42.smappee.api.Event;

import java.util.Date;
import java.util.Objects;

/**
 * Date: 7/10/17 - 9:19 PM
 *
 * @author Jeroen Meulemeester
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventImpl implements Event {

	/**
	 * The appliance identifier
	 */
	private final long applianceId;

	/**
	 * The event timestamp
	 */
	private final Date timeStamp;

	/**
	 * The active power value
	 */
	private final long activePower;

	/**
	 * Create a new event instance with the given values
	 *
	 * @param applianceId The identifier of the appliance this event is related to
	 * @param timeStamp   The timestamp of the event
	 * @param activePower The active power value of the event
	 */
	@JsonCreator
	public EventImpl(@JsonProperty("applianceId") final long applianceId,
					 @JsonProperty("timestamp") final Date timeStamp,
					 @JsonProperty("activePower") final long activePower) {
		this.applianceId = applianceId;
		this.timeStamp = Objects.requireNonNull(timeStamp);
		this.activePower = activePower;
	}

	/**
	 * Get the unique identifier of the appliance this event is related to
	 *
	 * @return The unique appliance id of the event
	 */
	@Override
	public final long getApplianceId() {
		return this.applianceId;
	}

	/**
	 * Get the timestamp of the event
	 *
	 * @return The event timestamp
	 */
	@Override
	public final Date getTimeStamp() {
		return this.timeStamp;
	}

	/**
	 * Get the active power value
	 *
	 * @return The active power value
	 */
	@Override
	public final long getActivePower() {
		return this.activePower;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof EventImpl)) {
			return false;
		}
		final EventImpl event = (EventImpl) o;
		return getApplianceId() == event.getApplianceId() &&
				getActivePower() == event.getActivePower() &&
				Objects.equals(getTimeStamp(), event.getTimeStamp());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int hashCode() {
		return Objects.hash(getApplianceId(), getTimeStamp(), getActivePower());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String toString() {
		final StringBuilder sb = new StringBuilder("EventImpl{");
		sb.append("applianceId=").append(applianceId);
		sb.append(", timeStamp=").append(timeStamp);
		sb.append(", activePower=").append(activePower);
		sb.append('}');
		return sb.toString();
	}
}
