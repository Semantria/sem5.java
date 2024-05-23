package com.lexalytics.semantria.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

import com.fasterxml.jackson.databind.ObjectMapper;

import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.jackson.JacksonEncoder;

public class SemantriaEncoder extends JacksonEncoder {

    public SemantriaEncoder(ObjectMapper mapper) {
        super(mapper);
    }

    @Override
    public void encode(Object object, Type bodyType, RequestTemplate template) {
        if (InputStream.class.getName().equals(bodyType.getTypeName()) && object instanceof InputStream) {
            try {
                template.body(readAllBytes((InputStream) object), null);
            } catch (IOException e) {
                throw new EncodeException(e.getMessage(), e);
            }
        } else {
            super.encode(object, bodyType, template);
        }
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
