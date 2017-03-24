package io.hyphenate.server.invoker;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.hyphenate.server.comm.ClientContext;
import io.hyphenate.server.comm.invoker.HttpClientRestAPIInvoker;
import io.hyphenate.server.comm.utils.ResponseUtils;
import io.hyphenate.server.comm.wrapper.HeaderWrapper;
import io.hyphenate.server.comm.wrapper.ResponseWrapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


/**
 * HttpClientRestAPIInvoker Tester.
 *
 * @author <Hyphenate>
 * @version 1.0
 * @since <pre>2016.11</pre>
 */
public class HttpClientRestAPIInvokerTest {

    private HttpClientRestAPIInvoker httpClient;
    private String configOrg;
    private String configAppName;
    private String apiUrl;
    private String resourcesPath = "src/test/java/io/hyphenate/server/invoker/mockdata/";
    private String configToken = "YWMt3elu7g9vEeeXtrPz3krXpQAAAVwt-IENLIL9ZN3wxEgh8ZHPzWBPZAzKRZc";

    @BeforeClass
    public static void beforeClass() {
        ClientContext.getInstance().init(ClientContext.INIT_FROM_PROPERTIES);
    }

    @Before
    public void before() throws Exception {
        httpClient = new HttpClientRestAPIInvoker();
        configOrg = ClientContext.getInstance().getOrg();
        configAppName = ClientContext.getInstance().getApp();
        apiUrl = "https://api.hyphenate.io/" + configOrg + "/" + configAppName;
    }

    @After
    public void after() throws Exception {
    }

    @Test
    // test send file successfully with 200
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
        String token = configToken;
        HeaderWrapper header = new HeaderWrapper();
        header.addAuthorization(token);
        ResponseWrapper responseWrapper = httpClient.sendRequest(method, url, header, null, null);
        assertEquals("200", responseWrapper.getResponseStatus().toString());
        JsonNode jsonNode = ResponseUtils.ResponseBodyToJsonNode(responseWrapper);
        assertEquals("1", jsonNode.get("count").toString());
    }

    @Test
    // test missing user
    public void testSendRequest_2() throws Exception {
        IMocksControl mocksControl = EasyMock.createControl();
        HttpClient clientMock = mocksControl.createMock(HttpClient.class);
        HttpResponse responseMock = mocksControl.createMock(HttpResponse.class);
        HttpUriRequest requestMock = mocksControl.createMock(HttpUriRequest.class);
        HttpEntity entityMock = mocksControl.createMock(HttpEntity.class);
        StatusLine statusLineMock = mocksControl.createMock(StatusLine.class);
        EasyMock.expect(responseMock.getStatusLine()).andReturn(statusLineMock);
        EasyMock.expect(statusLineMock.getStatusCode()).andReturn(404);
        FileInputStream fileInputStream = new FileInputStream(resourcesPath + "getUser002");
        fileInputStream.close();
        EasyMock.expect(entityMock.getContent()).andReturn(fileInputStream);
        EasyMock.expect(clientMock.execute(requestMock)).andReturn(responseMock);
        mocksControl.replay();

        String method = "GET";
        String url = apiUrl + "/users/user111";
        String token = configToken;
        HeaderWrapper header = new HeaderWrapper();
        header.addAuthorization(token);
        ResponseWrapper responseWrapper = httpClient.sendRequest(method, url, header, null, null);
        assertEquals("404", responseWrapper.getResponseStatus().toString());
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(responseWrapper.getResponseBody());
        JsonNode jsonNode = mapper.readTree(json);
        assertEquals("\"service_resource_not_found\"", jsonNode.get("error").toString());
    }

    @Test
    // test false token
    public void testSendRequest_falseToken() throws Exception {
        IMocksControl mocksControl = EasyMock.createControl();
        HttpClient clientMock = mocksControl.createMock(HttpClient.class);
        HttpResponse responseMock = mocksControl.createMock(HttpResponse.class);
        HttpUriRequest requestMock = mocksControl.createMock(HttpUriRequest.class);
        HttpEntity entityMock = mocksControl.createMock(HttpEntity.class);
        StatusLine statusLineMock = mocksControl.createMock(StatusLine.class);
        EasyMock.expect(responseMock.getStatusLine()).andReturn(statusLineMock);
        EasyMock.expect(statusLineMock.getStatusCode()).andReturn(404);
        FileInputStream fileInputStream = new FileInputStream(resourcesPath + "getUser002");
        fileInputStream.close();
        EasyMock.expect(entityMock.getContent()).andReturn(fileInputStream);
        EasyMock.expect(clientMock.execute(requestMock)).andReturn(responseMock);
        mocksControl.replay();

        String method = "GET";
        String url = apiUrl + "/users/user002";
        String token = "thisisafalsetokenplaceholder";
        HeaderWrapper header = new HeaderWrapper();
        header.addAuthorization(token);
        ResponseWrapper responseWrapper = httpClient.sendRequest(method, url, header, null, null);
        assertEquals("401", responseWrapper.getResponseStatus().toString());
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(responseWrapper.getResponseBody());
        JsonNode jsonNode = mapper.readTree(json);
        assertEquals("\"unauthorized\"", jsonNode.get("error").toString());
    }

    @Test
    // test bad token. Valid token, but with some characters in the middle removed
    public void testSendRequest_falseToken_2() throws Exception {
        IMocksControl mocksControl = EasyMock.createControl();
        HttpClient clientMock = mocksControl.createMock(HttpClient.class);
        HttpResponse responseMock = mocksControl.createMock(HttpResponse.class);
        HttpUriRequest requestMock = mocksControl.createMock(HttpUriRequest.class);
        HttpEntity entityMock = mocksControl.createMock(HttpEntity.class);
        StatusLine statusLineMock = mocksControl.createMock(StatusLine.class);
        EasyMock.expect(responseMock.getStatusLine()).andReturn(statusLineMock);
        EasyMock.expect(statusLineMock.getStatusCode()).andReturn(404);
        FileInputStream fileInputStream = new FileInputStream(resourcesPath + "getUser002");
        fileInputStream.close();
        EasyMock.expect(entityMock.getContent()).andReturn(fileInputStream);
        EasyMock.expect(clientMock.execute(requestMock)).andReturn(responseMock);
        mocksControl.replay();

        String method = "GET";
        String url = apiUrl + "/users/user002";
        String token = "YWMt3elu7g9vEeeXtrPz3k8ZHPzWBPZAzKRZc";
        HeaderWrapper header = new HeaderWrapper();
        header.addAuthorization(token);
        ResponseWrapper responseWrapper = httpClient.sendRequest(method, url, header, null, null);
        assertEquals("401", responseWrapper.getResponseStatus().toString());
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(responseWrapper.getResponseBody());
        JsonNode jsonNode = mapper.readTree(json);
        assertEquals("\"auth_bad_access_token\"", jsonNode.get("error").toString());
    }

    @Test
    public void testSendRequest_3() throws Exception {
        String method = "";
        String url = "";
        ResponseWrapper responseWrapper = httpClient.sendRequest(method, url, null, null, null);
        List<String> messages = new ArrayList<>();
        messages.add("[ERROR]: " + method + " is an unknown type of HTTP methods.");
        messages.add("[ERROR]: Parameter url should not be null or empty.");
        messages.add("[ERROR]: Parameter url doesn't match the required format.");
        Iterator<String> iterator = responseWrapper.getMessages().iterator();
        for (String s : messages) {
            assertEquals(s, iterator.next());
        }
    }

    @Test
    public void testSendRequest_4() throws Exception {
        String method = "GET";
        String url = apiUrl + "/users";
        String token = "error";
        HeaderWrapper header = new HeaderWrapper();
        header.addAuthorization(token);
        ResponseWrapper responseWrapper = httpClient.sendRequest(method, url, header, null, null);
        assertEquals("401", responseWrapper.getResponseStatus().toString());
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(responseWrapper.getResponseBody());
        JsonNode jsonNode = mapper.readTree(json);
        assertEquals("\"unauthorized\"", jsonNode.get("error").toString());
    }

    @Test
    public void testUploadFile_1() throws Exception {
        IMocksControl mocksControl = EasyMock.createControl();
        CloseableHttpClient clientMock = mocksControl.createMock(CloseableHttpClient.class);
        CloseableHttpResponse responseMock = mocksControl.createMock(CloseableHttpResponse.class);
        HttpPost httpPostMock = mocksControl.createMock(HttpPost.class);
        HttpEntity entityMock = mocksControl.createMock(HttpEntity.class);
        StatusLine statusLineMock = mocksControl.createMock(StatusLine.class);
        EasyMock.expect(responseMock.getStatusLine()).andReturn(statusLineMock);
        EasyMock.expect(statusLineMock.getStatusCode()).andReturn(404);
        FileInputStream fileInputStream = new FileInputStream(resourcesPath + "responseUploadedFile");
        fileInputStream.close();
        EasyMock.expect(entityMock.getContent()).andReturn(fileInputStream);
        EasyMock.expect(clientMock.execute(httpPostMock)).andReturn(responseMock);
        mocksControl.replay();

        String url = apiUrl + "/chatfiles";
        HeaderWrapper headerWrapper = new HeaderWrapper();
        headerWrapper.addAuthorization(configToken);
        File file = new File("src/test/resources/audio/audio.mp3");
        ResponseWrapper responseWrapper = httpClient.uploadFile(url, headerWrapper, file);
        assertEquals("200", responseWrapper.getResponseStatus().toString());
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(responseWrapper.getResponseBody());
        JsonNode jsonNode = mapper.readTree(json);
        assertNull(jsonNode.get("entities").get(0).get("share-secret"));
    }

    @Test
    public void testUploadFile_2() throws Exception {
        String url = "";
        HeaderWrapper headerWrapper = new HeaderWrapper();
        File file = new File("");
        ResponseWrapper responseWrapper = httpClient.uploadFile(url, headerWrapper, file);
        List<String> messages = new ArrayList<>();
        messages.add("[ERROR]: Parameter url should not be null or empty.");
        messages.add("[ERROR]: Parameter url doesn't match the required format.");
        messages.add("[ERROR]: Request body is invalid.");
        Iterator<String> iterator = responseWrapper.getMessages().iterator();
        for (String s : messages) {
            assertEquals(s, iterator.next());
        }
    }

    @Test
    // File with access-restriction. Require "share-secret" to obtain the file
    public void testDownloadFile_1() throws Exception {
        IMocksControl mocksControl = EasyMock.createControl();
        CloseableHttpClient clientMock = mocksControl.createMock(CloseableHttpClient.class);
        CloseableHttpResponse responseMock = mocksControl.createMock(CloseableHttpResponse.class);
        HttpGet httpGetMock = mocksControl.createMock(HttpGet.class);
        HttpEntity entityMock = mocksControl.createMock(HttpEntity.class);
        StatusLine statusLineMock = mocksControl.createMock(StatusLine.class);
        EasyMock.expect(responseMock.getStatusLine()).andReturn(statusLineMock);
        EasyMock.expect(statusLineMock.getStatusCode()).andReturn(200);
        EasyMock.expect(entityMock.getContent()).andReturn(new ByteArrayInputStream(new byte[]{}));
        EasyMock.expect(clientMock.execute(httpGetMock)).andReturn(responseMock);
        mocksControl.replay();

        String uuid = "842cfdc0-103d-11e7-b588-a5c1825ca2ba";
        String url = apiUrl + "/chatfiles/" + uuid;
        HeaderWrapper header = new HeaderWrapper();
        header.addAuthorization(configToken)
                .addHeader("accept", "application/octet-stream")
                .addHeader("share-secret", "hCz9yhA9Eeef7pNOvDqJpo-_UvoCwtqTvRfxAp6iMzbRM_Eb")
                .addHeader("thumbnail", "true");
        ResponseWrapper responseWrapper = httpClient.downloadFile(url, header);
        assertEquals("200", responseWrapper.getResponseStatus().toString());
    }

    @Test
    // File without access-restriction
    public void testDownloadFile_2() throws Exception {
        IMocksControl mocksControl = EasyMock.createControl();
        CloseableHttpClient clientMock = mocksControl.createMock(CloseableHttpClient.class);
        CloseableHttpResponse responseMock = mocksControl.createMock(CloseableHttpResponse.class);
        HttpGet httpGetMock = mocksControl.createMock(HttpGet.class);
        HttpEntity entityMock = mocksControl.createMock(HttpEntity.class);
        StatusLine statusLineMock = mocksControl.createMock(StatusLine.class);
        EasyMock.expect(responseMock.getStatusLine()).andReturn(statusLineMock);
        EasyMock.expect(statusLineMock.getStatusCode()).andReturn(200);
        EasyMock.expect(entityMock.getContent()).andReturn(new ByteArrayInputStream(new byte[]{}));
        EasyMock.expect(clientMock.execute(httpGetMock)).andReturn(responseMock);
        mocksControl.replay();
        String uuid = "f6f987c0-103c-11e7-9523-e90f047d5852";
        String url = apiUrl + "/chatfiles/" + uuid;
        ResponseWrapper responseWrapper = httpClient.downloadFile(url, null);
        assertEquals("200", responseWrapper.getResponseStatus().toString());
    }

} 
