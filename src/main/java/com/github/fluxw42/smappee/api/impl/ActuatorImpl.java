package com.github.fluxw42.smappee.api.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.fluxw42.smappee.api.Actuator;

import java.util.Objects;

/**
 * Date: 6/18/17 - 11:25 PM
 *
 * @author Jeroen Meulemeester
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActuatorImpl implements Actuator {

	/**
	 * The actuator identifier
	 */
	private final long id;

	/**
	 * The name of the actuator
	 */
	private final String name;

	/**
	 * Create a new actuator instance
	 *
	 * @param id   The unique actuator id
	 * @param name The name of the actuator
	 */
	@JsonCreator
	public ActuatorImpl(@JsonProperty("id") final long id, @JsonProperty("name") final String name) {
		this.id = id;
		this.name = name;
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
	public final boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof ActuatorImpl)) {
			return false;
		}
		final ActuatorImpl actuator = (ActuatorImpl) o;
		return getId() == actuator.getId() &&
				Objects.equals(getName(), actuator.getName());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int hashCode() {
		return Objects.hash(getId(), getName());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String toString() {
		final StringBuilder sb = new StringBuilder("ActuatorImpl{");
		sb.append("id=").append(id);
		sb.append(", name='").append(name).append('\'');
		sb.append('}');
		return sb.toString();
	}

}
