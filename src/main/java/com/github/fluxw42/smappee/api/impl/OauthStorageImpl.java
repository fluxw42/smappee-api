package com.github.fluxw42.smappee.api.impl;

import com.github.fluxw42.smappee.api.OAuthStorage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Date: 6/15/17 - 10:25 PM
 *
 * @author Jeroen Meulemeester
 */
public class OauthStorageImpl implements OAuthStorage {

	/**
	 * The logger for class OauthStorageImpl
	 */
	private static final Logger logger = Logger.getLogger(OauthStorageImpl.class.getName());

	/**
	 * The number of seconds subtracted from the access token expiration time to prevent expiration during renewal.
	 * We assume that an access token is already expired when the time left is <= this value
	 */
	private static final long EXPIRATION_GRAY_ZONE = 30;

	/**
	 * The storage label used to load and store the refresh token
	 */
	private static final String PROP_NAME_REFRESH_TOKEN = "refresh-token";

	/**
	 * The storage label used to load the client id from storage
	 */
	private static final String PROP_CLIENT_ID = "client-id";

	/**
	 * The storage label used to load the client secret from storage
	 */
	private static final String PROP_CLIENT_SECRET = "client-secret";

	/**
	 * The properties file, used to store the API credentials and refresh token
	 */
	private final File storageFile;

	/**
	 * The expiration time (using {@link System#nanoTime()} as a reference, independent of the system time
	 */
	private volatile long accessTokenExpirationTick;

	/**
	 * The current access token, 'null' when not available
	 */
	private volatile String accessToken;

	/**
	 * Create a new storage instance using the given file as underlying storage on disk
	 *
	 * @param storageFile The properties file, used as storage
	 */
	public OauthStorageImpl(final File storageFile) {
		this.storageFile = storageFile;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getAccessToken() {
		if (this.accessToken == null || accessTokenExpired()) {
			return null;
		}
		return this.accessToken;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getRefreshToken() {
		final Properties properties = load();
		return properties.getProperty(PROP_NAME_REFRESH_TOKEN);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setRefreshToken(final String refreshToken) {
		final Properties properties = load();
		if (refreshToken != null) {
			properties.setProperty(PROP_NAME_REFRESH_TOKEN, refreshToken);
		} else {
			properties.remove(PROP_NAME_REFRESH_TOKEN);
		}
		save(properties);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setAccessToken(final String accessToken, final long validity, final TimeUnit timeUnit) {
		this.accessToken = Objects.requireNonNull(accessToken);
		this.accessTokenExpirationTick = System.nanoTime() + Objects.requireNonNull(timeUnit.toNanos(validity));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getClientId() {
		final Properties properties = load();
		return properties.getProperty(PROP_CLIENT_ID);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getClientSecret() {
		final Properties properties = load();
		return properties.getProperty(PROP_CLIENT_SECRET);
	}

	/**
	 * Indicates if the access token is expired or not, taking the {@link #EXPIRATION_GRAY_ZONE} in account
	 *
	 * @return <tt>true</tt> when expired, <tt>false</tt> if not
	 */
	private boolean accessTokenExpired() {
		return System.nanoTime() + TimeUnit.SECONDS.toNanos(EXPIRATION_GRAY_ZONE) > this.accessTokenExpirationTick;
	}

	/**
	 * Load the OAuth2 properties from storage
	 *
	 * @return The properties
	 */
	private Properties load() {
		final Properties properties = new Properties();
		if (this.storageFile.isFile()) {
			try (final InputStream in = new FileInputStream(this.storageFile)) {
				properties.load(in);
			} catch (IOException e) {
				if (logger.isLoggable(Level.WARNING)) {
					logger.log(Level.WARNING, "Failed to load storage data from [" + this.storageFile + "] : " + e.getMessage(), e);
				}
			}
		}
		return properties;
	}

	/**
	 * Save the storage to disk
	 *
	 * @param properties The properties file
	 */
	private void save(final Properties properties) {
		try (final OutputStream out = new FileOutputStream(this.storageFile)) {
			properties.store(out, getClass().getSimpleName());
		} catch (IOException e) {
			if (logger.isLoggable(Level.WARNING)) {
				logger.log(Level.WARNING, "Failed to save storage data to [" + this.storageFile + "] : " + e.getMessage(), e);
			}
		}
	}

}
