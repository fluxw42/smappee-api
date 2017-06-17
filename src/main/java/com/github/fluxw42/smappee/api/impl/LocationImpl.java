package com.github.fluxw42.smappee.api.impl;

import com.github.fluxw42.smappee.api.Location;

import java.util.Objects;

/**
 * Date: 6/17/17 - 8:28 PM
 *
 * @author Jeroen Meulemeester
 */
public class LocationImpl implements Location {

	/**
	 * The latitude of the location (North/South)
	 */
	private final double latitude;

	/**
	 * The longitude of the location (East/West)
	 */
	private final double longitude;

	/**
	 * Create a new location instance from the given values
	 *
	 * @param latitude  The latitude of the location (North/South)
	 * @param longitude The longitude of the location (East/West)
	 */
	public LocationImpl(final double latitude, final double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final double getLatitude() {
		return this.latitude;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final double getLongitude() {
		return this.longitude;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof LocationImpl)) {
			return false;
		}
		final LocationImpl location = (LocationImpl) o;
		return Double.compare(location.getLatitude(), getLatitude()) == 0 &&
				Double.compare(location.getLongitude(), getLongitude()) == 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int hashCode() {
		return Objects.hash(getLatitude(), getLongitude());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String toString() {
		final StringBuilder sb = new StringBuilder("LocationImpl{");
		sb.append("latitude=").append(latitude);
		sb.append(", longitude=").append(longitude);
		sb.append('}');
		return sb.toString();
	}

}
