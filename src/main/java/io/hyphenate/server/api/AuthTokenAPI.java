package io.hyphenate.server.api;

public interface AuthTokenAPI {	

	/**
	 * Request an Authentication Token
	 * POST
	 *
	 * @param clientId
	 *            can be found in the application details page of the Hyphenate console
	 * @param clientSecret
	 *            can be found in the application details page of the Hyphenate console
	 * @return
	 */
	Object getAuthToken(String clientId, String clientSecret);
}
