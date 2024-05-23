package com.lexalytics.semantria.client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

import com.fasterxml.jackson.databind.ObjectMapper;

import feign.Response;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

public class SemantriaDecoder extends JacksonDecoder {

    public SemantriaDecoder(ObjectMapper mapper) {
        super(mapper);
    }

    @Override
    public Object decode(Response response, Type type) throws IOException {
        if (response.body() == null) {
            return null;
        }
        if (InputStream.class.getName().equals(type.getTypeName())) {
            return new ByteArrayInputStream(readAllBytes(response.body().asInputStream()));
        }
        return super.decode(response, type);
    }

    private byte[] readAllBytes(InputStream inputStream) throws IOException {
        try (final ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[4 * 1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                output.write(buffer, 0, length);
            }
            return output.toByteArray();
        }
    }
}
