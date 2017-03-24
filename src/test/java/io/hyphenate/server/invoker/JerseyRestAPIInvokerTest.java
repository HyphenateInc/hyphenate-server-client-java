package io.hyphenate.server.invoker;
import io.hyphenate.server.comm.invoker.JerseyRestAPIInvoker;

import com.fasterxml.jackson.databind.JsonNode;
import io.hyphenate.server.comm.ClientContext;
import io.hyphenate.server.comm.utils.ResponseUtils;
import io.hyphenate.server.comm.wrapper.HeaderWrapper;
import io.hyphenate.server.comm.wrapper.ResponseWrapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * JerseyRestAPIInvoker Tester.
 *
 * @author <Hyphenate>
 * @version 1.0
 * @since <pre>2016.11</pre>
 */
public class JerseyRestAPIInvokerTest {

    private JerseyRestAPIInvoker jerseyClient;
    private String configOrg;
    private String configAppName;
    private String apiUrl;
    private String resourcesPath = "src/test/java/io/hyphenate/server/invoker/mockdata/";

    @BeforeClass
    public static void beforeClass(){
        ClientContext.getInstance().init(ClientContext.INIT_FROM_PROPERTIES);
    }

    @Before
    public void before() throws Exception {
        jerseyClient = new JerseyRestAPIInvoker();
        configOrg = ClientContext.getInstance().getOrg();
        configAppName = ClientContext.getInstance().getApp();
        apiUrl = "https://api.hyphenate.io/" + configOrg + "/" + configAppName;
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testSendRequest_1() throws Exception {
        IMocksControl mocksControl = EasyMock.createControl();
        HttpClient clientMock = mocksControl.createMock(HttpClient.class);
        HttpResponse responseMock = mocksControl.createMock(HttpResponse.class);
        HttpUriRequest requestMock = mocksControl.createMock(HttpUriRequest.class);
        HttpEntity entityMock = mocksControl.createMock(HttpEntity.class);
        StatusLine statusLineMock = mocksControl.createMock(StatusLine.class);
        EasyMock.expect(responseMock.getStatusLine()).andReturn(statusLineMock);
        EasyMock.expect(statusLineMock.getStatusCode()).andReturn(200);
        FileInputStream fileInputStream = new FileInputStream(resourcesPath + "getUser001");
        fileInputStream.close();
        EasyMock.expect(entityMock.getContent()).andReturn(fileInputStream);
        EasyMock.expect(clientMock.execute(requestMock)).andReturn(responseMock);
        mocksControl.replay();

        String method = "GET";
        String url = apiUrl + "/users/user001";
        String token = "YWMt3elu7g9vEeeXtrPz3krXpQAAAVwt-IENLIL9ZN3wxEgh8ZHPzWBPZAzKRZc";
        HeaderWrapper header = new HeaderWrapper();
        header.addAuthorization(token);
        ResponseWrapper responseWrapper = jerseyClient.sendRequest(method, url, header, null, null);
        assertEquals("200", responseWrapper.getResponseStatus().toString());
        JsonNode jsonNode = ResponseUtils.ResponseBodyToJsonNode(responseWrapper);
        assertEquals("1", jsonNode.get("count").toString());
    }
    @Test
    public void testSendRequest_2() throws Exception {
        String method = "";
        String url = "";
        ResponseWrapper responseWrapper = jerseyClient.sendRequest(method, url, null, null, null);
        List<String> messages = new ArrayList<>();
        messages.add("[ERROR]: " + method + " is an unknown type of HTTP methods.");
        messages.add("[ERROR]: Parameter url should not be null or empty.");
        messages.add("[ERROR]: Parameter url doesn't match the required format.");
        Iterator<String> iterator = responseWrapper.getMessages().iterator();
        for (String s : messages) {
            assertEquals(s, iterator.next());
        }
    }
} 
