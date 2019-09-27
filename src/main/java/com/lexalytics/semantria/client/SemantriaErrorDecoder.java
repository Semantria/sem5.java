package com.lexalytics.semantria.client;

import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;
import java.io.Reader;

public class SemantriaErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        Response.Body body = response.body();
        return new SemantriaClientError(methodKey + " failed", response.status(), bodyAsString(response.body()));
    }

    private String bodyAsString(Response.Body body) {
        if (body == null) {
            return "";
        }
        try {
            Reader reader = body.asReader();
            char[] arr = new char[8 * 1024];
            StringBuilder buffer = new StringBuilder();
            int numCharsRead;
            while ((numCharsRead = reader.read(arr, 0, arr.length)) != -1) {
                buffer.append(arr, 0, numCharsRead);
            }
            return buffer.toString();
        }
        catch (IOException e) {
            return "";
        }
    }
}
