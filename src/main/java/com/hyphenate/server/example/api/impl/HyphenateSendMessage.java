package io.hyphenate.server.api.impl;

import io.hyphenate.server.api.HyphenateRestAPI;
import io.hyphenate.server.api.SendMessageAPI;
import io.hyphenate.server.comm.wrapper.BodyWrapper;
import io.hyphenate.server.comm.constant.HTTPMethod;
import io.hyphenate.server.comm.helper.HeaderHelper;
import io.hyphenate.server.comm.wrapper.HeaderWrapper;

public class HyphenateSendMessage extends HyphenateRestAPI implements SendMessageAPI {
    private static final String ROOT_URI = "/messages";

    @Override
    public String getResourceRootURI() {
        return ROOT_URI;
    }

    public Object sendMessage(Object payload) {
        String  url = getContext().getSeriveURL() + getResourceRootURI();
        HeaderWrapper header = HeaderHelper.getDefaultHeaderWithToken();
        BodyWrapper body = (BodyWrapper) payload;

        return getInvoker().sendRequest(HTTPMethod.METHOD_POST, url, header, body, null);
    }
}
