package com.github.fluxw42.smappee.api.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.fluxw42.smappee.api.Appliance;

import java.util.Objects;

/**
 * Date: 6/17/17 - 7:43 PM
 *
 * @author Jeroen Meulemeester
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplianceImpl implements Appliance {

	/**
	 * The appliance identifier
	 */
	private final long id;

	/**
	 * The name of the appliance
	 */
	private final String name;

	/**
	 * The appliance type
	 */
	private final String type;

	/**
	 * Create a new appliance instance
	 *
	 * @param id   The unique appliance id
	 * @param name The name of the appliance
	 * @param type The type of appliance
	 */
	@JsonCreator
	public ApplianceImpl(@JsonProperty("id") final long id, @JsonProperty("name") final String name, @JsonProperty("type") final String type) {
		this.id = id;
		this.name = name;
		this.type = type;
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
		if (!(o instanceof ApplianceImpl)) {
			return false;
		}
		final ApplianceImpl appliance = (ApplianceImpl) o;
		return getId() == appliance.getId() &&
				Objects.equals(getType(), appliance.getType()) &&
				Objects.equals(getName(), appliance.getName());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int hashCode() {
		return Objects.hash(getId(), getType(), getName());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String toString() {
		final StringBuilder sb = new StringBuilder("ApplianceImpl{");
		sb.append("id=").append(id);
		sb.append(", type='").append(type).append('\'');
		sb.append(", name='").append(name).append('\'');
		sb.append('}');
		return sb.toString();
	}

}
