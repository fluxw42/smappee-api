package com.github.fluxw42.smappee.api;

/**
 * Date: 6/15/17 - 9:38 PM
 *
 * @author Jeroen Meulemeester
 */
@FunctionalInterface
public interface AuthorizationCallback {

	/**
	 * Request the credentials of the user, used to get authorization for the app
	 *
	 * @return The users credentials (username and password)
	 */
	Credentials getUserCredentials();

}
