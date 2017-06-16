package com.github.fluxw42.smappee.api;

import java.util.concurrent.TimeUnit;

/**
 * Date: 6/15/17 - 9:47 PM
 *
 * @author Jeroen Meulemeester
 */
public interface OAuthStorage {

	/**
	 * Get the current access token when available
	 *
	 * @return The current access token, or 'null' when unavailable
	 */
	String getAccessToken();

	/**
	 * Set or update the access token
	 *
	 * @param accessToken  The new access token
	 * @param validity     The validity time of the access token
	 * @param validityUnit The unit of the validity time
	 */
	void setAccessToken(final String accessToken, final long validity, final TimeUnit validityUnit);

	/**
	 * Get the current refresh token when available
	 *
	 * @return The current refresh token, or 'null' when unavailable
	 */
	String getRefreshToken();

	/**
	 * Set or update the refresh token
	 *
	 * @param refreshToken The new refresh token
	 */
	void setRefreshToken(final String refreshToken);

	/**
	 * Get the unique API client identifier
	 *
	 * @return The API client id
	 */
	String getClientId();

	/**
	 * Get the matching secret for the API client id
	 *
	 * @return The API client's secret
	 */
	String getClientSecret();

}
