package com.github.fluxw42.smappee.api;

/**
 * Date: 6/14/17 - 10:51 PM
 *
 * @author Jeroen Meulemeester
 */
public interface Credentials {

	/**
	 * Get the username
	 *
	 * @return The username
	 */
	String getUserName();

	/**
	 * Get the password of the user
	 *
	 * @return The user password
	 */
	String getPassword();

	/**
	 * Create a new {@link Credentials} instance with the given values
	 *
	 * @param userName The username
	 * @param password The password
	 * @return The credentials
	 */
	static Credentials fromValues(final String userName, final String password) {
		return new Credentials() {
			@Override
			public String getUserName() {
				return userName;
			}

			@Override
			public String getPassword() {
				return password;
			}
		};
	}

}
