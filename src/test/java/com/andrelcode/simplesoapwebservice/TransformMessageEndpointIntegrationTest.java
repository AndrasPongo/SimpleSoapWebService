package com.andrelcode.simplesoapwebservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.ws.test.server.MockWebServiceClient;

import static org.springframework.ws.test.server.RequestCreators.withPayload;
import static org.springframework.ws.test.server.ResponseMatchers.payload;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;

@SpringBootTest
@SpringJUnitConfig(classes = SimpleSoapWebServiceApplication.class)
public class TransformMessageEndpointIntegrationTest {

    @Autowired
    private ApplicationContext applicationContext;

    private MockWebServiceClient mockClient;

    @BeforeEach
    public void createClient() {
        mockClient = MockWebServiceClient.createClient(applicationContext);
    }

    @Test
    public void testTransformMessage() {
        String requestPayload = "<ns2:TransformMessageRequest xmlns:ns2=\"http://andrelcode.com/simplesoapwebservice\">" +
                "<ns2:TransformMessage>" +
                "<ns2:header>Test Header</ns2:header>" +
                "<ns2:message>Test Message</ns2:message>" +
                "</ns2:TransformMessage>" +
                "</ns2:TransformMessageRequest>";

        // Prepare an expected response payload
        String expectedResponsePayload = "<ns2:TransformMessageResponse xmlns:ns2=\"http://andrelcode.com/simplesoapwebservice\">" +
                "<ns2:TransformMessage>" +
                "<ns2:header>Response Header</ns2:header>" +
                "<ns2:message>Response to: Test Message</ns2:message>" +
                "</ns2:TransformMessage>" +
                "</ns2:TransformMessageResponse>";

        Source request = new StreamSource(new StringReader(requestPayload));
        Source expectedResponse = new StreamSource(new StringReader(expectedResponsePayload));

        mockClient
                .sendRequest(withPayload(request))
                .andExpect(payload(expectedResponse));
    }
}
