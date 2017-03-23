package io.hyphenate.server.api.impl;

import io.hyphenate.server.api.AuthTokenAPI;
import io.hyphenate.server.comm.body.AuthTokenBody;
import io.hyphenate.server.comm.constant.HTTPMethod;
import io.hyphenate.server.comm.helper.HeaderHelper;
import io.hyphenate.server.comm.wrapper.BodyWrapper;
import io.hyphenate.server.comm.wrapper.HeaderWrapper;
import io.hyphenate.server.api.HyphenateRestAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HyphenateAuthToken extends HyphenateRestAPI implements AuthTokenAPI {
	
	public static final String ROOT_URI = "/token";
	
	private static final Logger log = LoggerFactory.getLogger(HyphenateAuthToken.class);
	
	@Override
	public String getResourceRootURI() {
		return ROOT_URI;
	}

	public Object getAuthToken(String clientId, String clientSecret) {
		String url = getContext().getSeriveURL() + getResourceRootURI();
		BodyWrapper body = new AuthTokenBody(clientId, clientSecret);
		HeaderWrapper header = HeaderHelper.getDefaultHeader();
		
		return getInvoker().sendRequest(HTTPMethod.METHOD_POST, url, header, body, null);
	}
}
