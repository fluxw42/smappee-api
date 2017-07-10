package com.github.fluxw42.smappee.api;

import java.time.Duration;

/**
 * Date: 6/29/17 - 10:21 PM
 *
 * @author Jeroen Meulemeester
 */
public enum StateDuration {

	INFINITE(-1),
	FIVE_MINUTES(300),
	FIFTEEN_MINUTES(900),
	HALF_HOUR(1800),
	HOUR(3600);

	/**
	 * The duration
	 */
	private final Duration duration;

	/**
	 * Create a new state duration
	 *
	 * @param duration The duration in seconds, or a negative value if not applicable
	 */
	StateDuration(final int duration) {
		this.duration = Duration.ofSeconds(duration);
	}

	/**
	 * Get the duration of an appliance state. A negative value indicates an infinite duration
	 *
	 * @return The duration
	 */
	public final Duration getDuration() {
		return this.duration;
	}

}
