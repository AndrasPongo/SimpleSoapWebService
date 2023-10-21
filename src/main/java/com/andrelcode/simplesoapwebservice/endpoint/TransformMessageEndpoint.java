package com.andrelcode.simplesoapwebservice.endpoint;

import com.andrelcode.simplesoapwebservice.TransformMessageRequest;
import com.andrelcode.simplesoapwebservice.TransformMessageResponse;
import com.andrelcode.simplesoapwebservice.TransformMessageType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Slf4j
@Endpoint
public class TransformMessageEndpoint {

    private static final String NAMESPACE_URI = "http://andrelcode.com/simplesoapwebservice";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "TransformMessageRequest")
    @ResponsePayload
    public TransformMessageResponse processTransformMessageRequest(@RequestPayload TransformMessageRequest request) {
        log.info("Received request to transform message.");
        // Access the nested 'TransformMessage' structure from the request.
        TransformMessageType requestContent = request.getTransformMessage();

        // Create a response object.
        TransformMessageResponse response = new TransformMessageResponse();

        // Here, you'd implement your actual message transformation logic or call the service that does so.
        if (requestContent != null) { // check if the content is not null to avoid NullPointerException
            log.info("Request content: {}", requestContent);
            String header = requestContent.getHeader();
            String message = requestContent.getMessage();

            // Set up the response content.
            TransformMessageType responseContent = new TransformMessageType();
            responseContent.setHeader("Response Header");
            responseContent.setMessage("Response to: " + message);

            // Include the response content in the response.
            response.setTransformMessage(responseContent);
        } else {
            log.warn("Request content is null.");
            // Handle the case where the request content is null.
            // This could be due to an incorrect request structure, and you might want to return a fault/exception.
        }

        return response;
    }
}
