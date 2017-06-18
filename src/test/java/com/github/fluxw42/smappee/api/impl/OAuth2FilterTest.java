package com.github.fluxw42.smappee.api.impl;

import com.github.fluxw42.smappee.api.AuthorizationCallback;
import com.github.fluxw42.smappee.api.Credentials;
import com.github.fluxw42.smappee.api.OAuthStorage;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.containing;
import static com.github.tomakehurst.wiremock.client.WireMock.forbidden;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Date: 6/18/17 - 8:25 PM
 *
 * @author Jeroen Meulemeester
 */
public class OAuth2FilterTest extends TestWithResource {

	@Rule
	public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().dynamicPort().dynamicHttpsPort());

	@Test
	public void testGetTokens() throws Exception {

		final String clientId = "clientId";
		final String clientSecret = "clientSecret";
		final String refreshToken = "326ce36a-c101-356e-9686-b695bf924f0d";
		final String accessToken = "5fd0cf9a-fd95-38d3-a391-887b0dfa8e86";

		stubFor(post(urlEqualTo("/oauth2/token"))
				.withRequestBody(containing("grant_type=password"))
				.willReturn(aResponse()
						.withStatus(200)
						.withHeader("Content-Type", "application/json")
						.withBody(getResource("RequestTokens.json"))));

		final Credentials credentials = mock(Credentials.class);
		when(credentials.getUserName()).thenReturn("userName");
		when(credentials.getPassword()).thenReturn("userPassword");

		final AuthorizationCallback authorizationCallback = mock(AuthorizationCallback.class);
		when(authorizationCallback.getUserCredentials()).thenReturn(credentials);

		final OAuthStorage oAuthStorage = mock(OAuthStorage.class);
		when(oAuthStorage.getAccessToken()).thenReturn(null).thenReturn(accessToken);
		when(oAuthStorage.getRefreshToken()).thenReturn(null).thenReturn(refreshToken);
		when(oAuthStorage.getClientId()).thenReturn(clientId);
		when(oAuthStorage.getClientSecret()).thenReturn(clientSecret);

		final String baseUrl = "http://localhost:" + this.wireMockRule.port();
		final MultivaluedMap<String, Object> headers = mock(MultivaluedMap.class);

		final ClientRequestContext requestContext = mock(ClientRequestContext.class);
		when(requestContext.getHeaders()).thenReturn(headers);

		final OAuth2Filter filter = new OAuth2Filter(baseUrl, authorizationCallback, oAuthStorage);
		filter.filter(requestContext);

		verify(oAuthStorage, times(1)).setRefreshToken(eq(refreshToken));
		verify(oAuthStorage, times(1)).setAccessToken(eq(accessToken), eq(36000L), eq(TimeUnit.SECONDS));
		verify(oAuthStorage, times(1)).getRefreshToken();
		verify(oAuthStorage, times(2)).getAccessToken();
		verify(headers, times(1)).add(eq("Authorization"), eq("Bearer " + accessToken));
	}

	@Test
	public void testRefreshTokens() throws Exception {

		final String clientId = "clientId";
		final String clientSecret = "clientSecret";
		final String refreshToken = "326ce36a-c101-356e-9686-b695bf924f0d";
		final String updatedRefreshToken = "6d01da8f-d0bd-3e55-bba9-1ab7a31d6fe5";
		final String accessToken = "f73fd163-68da-3d90-95da-a7cf20b7f179";

		stubFor(post(urlEqualTo("/oauth2/token"))
				.withRequestBody(containing("grant_type=refresh_token"))
				.willReturn(aResponse()
						.withStatus(200)
						.withHeader("Content-Type", "application/json")
						.withBody(getResource("RefreshTokens.json"))));

		final AuthorizationCallback authorizationCallback = mock(AuthorizationCallback.class);
		final OAuthStorage oAuthStorage = mock(OAuthStorage.class);
		when(oAuthStorage.getAccessToken()).thenReturn(null).thenReturn(accessToken);
		when(oAuthStorage.getRefreshToken()).thenReturn(refreshToken).thenReturn(updatedRefreshToken);
		when(oAuthStorage.getClientId()).thenReturn(clientId);
		when(oAuthStorage.getClientSecret()).thenReturn(clientSecret);

		final String baseUrl = "http://localhost:" + this.wireMockRule.port();
		final MultivaluedMap<String, Object> headers = mock(MultivaluedMap.class);

		final ClientRequestContext requestContext = mock(ClientRequestContext.class);
		when(requestContext.getHeaders()).thenReturn(headers);

		final OAuth2Filter filter = new OAuth2Filter(baseUrl, authorizationCallback, oAuthStorage);
		filter.filter(requestContext);

		verify(oAuthStorage, times(1)).setRefreshToken(eq(updatedRefreshToken));
		verify(oAuthStorage, times(1)).setAccessToken(eq(accessToken), eq(36000L), eq(TimeUnit.SECONDS));
		verify(oAuthStorage, times(2)).getRefreshToken();
		verify(oAuthStorage, times(2)).getAccessToken();
		verify(headers, times(1)).add(eq("Authorization"), eq("Bearer " + accessToken));
	}

	@Test(expected = IOException.class)
	public void testAuthFailed() throws Exception {

		final String clientId = "clientId";
		final String clientSecret = "clientSecret";
		final String refreshToken = "326ce36a-c101-356e-9686-b695bf924f0d";
		final String updatedRefreshToken = "6d01da8f-d0bd-3e55-bba9-1ab7a31d6fe5";
		final String accessToken = "f73fd163-68da-3d90-95da-a7cf20b7f179";

		stubFor(post(urlEqualTo("/oauth2/token"))
				.withRequestBody(containing("grant_type=refresh_token"))
				.willReturn(forbidden()));

		stubFor(post(urlEqualTo("/oauth2/token"))
				.withRequestBody(containing("grant_type=password"))
				.willReturn(forbidden()));

		final Credentials credentials = mock(Credentials.class);
		when(credentials.getUserName()).thenReturn("userName");
		when(credentials.getPassword()).thenReturn("userPassword");

		final AuthorizationCallback authorizationCallback = mock(AuthorizationCallback.class);
		when(authorizationCallback.getUserCredentials()).thenReturn(credentials);

		final OAuthStorage oAuthStorage = mock(OAuthStorage.class);
		when(oAuthStorage.getAccessToken()).thenReturn(null).thenReturn(accessToken);
		when(oAuthStorage.getRefreshToken()).thenReturn(refreshToken).thenReturn(updatedRefreshToken);
		when(oAuthStorage.getClientId()).thenReturn(clientId);
		when(oAuthStorage.getClientSecret()).thenReturn(clientSecret);

		final String baseUrl = "http://localhost:" + this.wireMockRule.port();
		new OAuth2Filter(baseUrl, authorizationCallback, oAuthStorage).filter(mock(ClientRequestContext.class));
	}


}