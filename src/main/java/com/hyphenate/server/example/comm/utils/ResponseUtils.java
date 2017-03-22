package io.hyphenate.server.comm.utils;

import io.hyphenate.server.comm.wrapper.ResponseWrapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ResponseUtils {
    public static JsonNode ResponseBodyToJsonNode(ResponseWrapper response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(response.getResponseBody());
        JsonNode jsonNode = mapper.readTree(json);
        return jsonNode;
    }
}
