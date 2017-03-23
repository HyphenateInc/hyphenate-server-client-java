package io.hyphenate.server.api;

import io.hyphenate.server.comm.wrapper.QueryWrapper;
import io.hyphenate.server.comm.wrapper.ResponseWrapper;
import io.hyphenate.server.comm.wrapper.BodyWrapper;
import io.hyphenate.server.comm.wrapper.HeaderWrapper;

import java.io.File;

public interface RestAPIInvoker {
	ResponseWrapper sendRequest(String method, String url, HeaderWrapper header, BodyWrapper body, QueryWrapper query);
	ResponseWrapper uploadFile(String url, HeaderWrapper header, File file);
    ResponseWrapper downloadFile(String url, HeaderWrapper header);
}
