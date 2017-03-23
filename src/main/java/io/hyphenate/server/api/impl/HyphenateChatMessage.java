package io.hyphenate.server.api.impl;

import io.hyphenate.server.api.ChatMessageAPI;
import io.hyphenate.server.api.HyphenateRestAPI;
import io.hyphenate.server.comm.constant.HTTPMethod;
import io.hyphenate.server.comm.helper.HeaderHelper;
import io.hyphenate.server.comm.wrapper.HeaderWrapper;
import io.hyphenate.server.comm.wrapper.QueryWrapper;

public class HyphenateChatMessage extends HyphenateRestAPI implements ChatMessageAPI {

    private static final String ROOT_URI = "/chatmessages";

    public Object exportChatMessages(Long limit, String cursor, String query) {
        String url = getContext().getSeriveURL() + getResourceRootURI();
        HeaderWrapper header = HeaderHelper.getDefaultHeaderWithToken();
        QueryWrapper queryWrapper = QueryWrapper.newInstance().addLimit(limit).addCursor(cursor).addQueryLang(query);

        return getInvoker().sendRequest(HTTPMethod.METHOD_GET, url, header, null, queryWrapper);
    }

    @Override
    public String getResourceRootURI() {
        return ROOT_URI;
    }
}
