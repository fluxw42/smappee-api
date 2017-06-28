package com.github.fluxw42.smappee.api;

/**
 * Date: 6/20/17 - 4:59 PM
 *
 * @author Jeroen Meulemeester
 */
public enum AggregationPeriod {

	FIVE_MINUTES(1),
	HOURLY(2),
	DAILY(3),
	MONTHLY(4),
	QUARTERLY(5);

	/**
	 * The unique id
	 */
	private final int id;

	/**
	 * Create a new aggregation period
	 *
	 * @param id The unique id
	 */
	AggregationPeriod(final int id) {
		this.id = id;
	}

	/**
	 * Get the unique id of this aggregation period
	 *
	 * @return The unique aggregation id
	 */
	public final int getId() {
		return this.id;
	}

	/**
	 * Find a {@link AggregationPeriod} by it's numerical value
	 *
	 * @param value The numerical value you're looking for
	 * @return The matching enum value or 'null' when not found.
	 */
	public static AggregationPeriod fromId(final int value) {
		for (final AggregationPeriod aggregationPeriod : values()) {
			if (aggregationPeriod.getId() == value) {
				return aggregationPeriod;
			}
		}
		return null;
	}

}
