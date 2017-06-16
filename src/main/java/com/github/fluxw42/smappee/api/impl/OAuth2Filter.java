package com.github.fluxw42.smappee.api.impl;

import com.github.fluxw42.smappee.api.AuthorizationCallback;
import com.github.fluxw42.smappee.api.Credentials;
import com.github.fluxw42.smappee.api.OAuthStorage;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Date: 6/13/17 - 10:16 PM
 *
 * @author Jeroen Meulemeester
 */
@Provider
public class OAuth2Filter implements ClientRequestFilter {

	/**
	 * The logger for class OAuth2Filter
	 */
	private static final Logger logger = Logger.getLogger(OAuth2Filter.class.getName());

	/**
	 * The API endpoint, used to request and refresh tokens
	 */
	private final String tokenUrl;

	/**
	 * The authorization callback, used to get API tokens
	 */
	private final AuthorizationCallback authCallback;

	/**
	 * The OAuth storage, used to load and store the API tokens for later use
	 */
	private final OAuthStorage oAuthStorage;

	/**
	 * Create a new filter which handles the OAuth2 authentication in a transparent way
	 *
	 * @param baseUrl      The base URL of the API
	 * @param authCallback The authentication callback, used when we need to request new tokens using the user's credentials
	 * @param oAuthStorage The OAuth2 storage, which provides the API client credentials and the token(s)
	 */
	public OAuth2Filter(final String baseUrl, final AuthorizationCallback authCallback, final OAuthStorage oAuthStorage) {
		this.tokenUrl = Objects.requireNonNull(baseUrl) + "/oauth2/token";
		this.authCallback = Objects.requireNonNull(authCallback);
		this.oAuthStorage = Objects.requireNonNull(oAuthStorage);
	}

	/**
	 * Make sure we're authenticated and inject the access token in the header of the original request
	 *
	 * @param requestContext The REST http request
	 * @throws IOException When the request could not be fulfilled (communication or authentication error)
	 */
	@Override
	public synchronized void filter(final ClientRequestContext requestContext) throws IOException {
		if (this.oAuthStorage.getAccessToken() == null) {
			authenticate();
		}
		requestContext.getHeaders().add("Authorization", "Bearer " + this.oAuthStorage.getAccessToken());
	}

	/**
	 * Request new tokens using the user credentials
	 *
	 * @return <tt>true</tt> when successful, <tt>false</tt> if not
	 */
	private boolean requestTokens() {
		try {
			final OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
			final Credentials userCredentials = this.authCallback.getUserCredentials();
			final OAuthClientRequest request = OAuthClientRequest.tokenLocation(tokenUrl)
					.setGrantType(GrantType.PASSWORD)
					.setClientId(this.oAuthStorage.getClientId())
					.setClientSecret(this.oAuthStorage.getClientSecret())
					.setUsername(userCredentials.getUserName())
					.setPassword(userCredentials.getPassword())
					.buildBodyMessage();

			final OAuthJSONAccessTokenResponse post = oAuthClient.accessToken(request, "POST");
			this.oAuthStorage.setAccessToken(post.getAccessToken(), post.getExpiresIn(), TimeUnit.SECONDS);
			this.oAuthStorage.setRefreshToken(post.getRefreshToken());
			return true;
		} catch (OAuthSystemException | OAuthProblemException e) {
			if (logger.isLoggable(Level.WARNING)) {
				logger.log(Level.WARNING, "Failed to request tokens : " + e.getMessage(), e);
			}
			return false;
		}
	}

	/**
	 * Request new tokens using the refresh-token
	 *
	 * @return <tt>true</tt> when successful, <tt>false</tt> if not
	 */
	private boolean refreshTokens() {
		try {
			final OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
			final OAuthClientRequest request = OAuthClientRequest.tokenLocation(tokenUrl)
					.setGrantType(GrantType.REFRESH_TOKEN)
					.setRefreshToken(Objects.requireNonNull(this.oAuthStorage.getRefreshToken()))
					.setClientId(this.oAuthStorage.getClientId())
					.setClientSecret(this.oAuthStorage.getClientSecret())
					.buildBodyMessage();

			final OAuthJSONAccessTokenResponse post = oAuthClient.accessToken(request, "POST");
			this.oAuthStorage.setAccessToken(post.getAccessToken(), post.getExpiresIn(), TimeUnit.SECONDS);
			this.oAuthStorage.setRefreshToken(post.getRefreshToken());
			return true;
		} catch (OAuthSystemException | OAuthProblemException e) {
			if (logger.isLoggable(Level.WARNING)) {
				logger.log(Level.WARNING, "Failed to refresh tokens : " + e.getMessage());
			}
			return false;
		}
	}

	/**
	 * Try to get a valid access token by either refreshing the current tokens, or by requesting new tokens
	 *
	 * @throws IOException When the authentication failed or another communication error occurred
	 */
	private void authenticate() throws IOException {
		if (this.oAuthStorage.getRefreshToken() != null) {
			final boolean refreshSuccess = refreshTokens();
			if (refreshSuccess) {
				return;
			}
		}

		final boolean requestSuccess = requestTokens();
		if (!requestSuccess) {
			throw new IOException("Failed to request new access and request tokens.");
		}
	}

}
