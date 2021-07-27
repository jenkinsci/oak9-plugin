package io.jenkins.plugins.oak9.utils;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import okhttp3.FormBody;
import org.junit.jupiter.api.Test;

public class RequestBodyBuilderTest {
    @Test
    public void testConstructor() throws UnsupportedEncodingException {
        RequestBodyBuilder actualRequestBodyBuilder = new RequestBodyBuilder(
                new ByteArrayInputStream("AAAAAAAAAAAAAAAAAAAAAAAA".getBytes("UTF-8")), null);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("AAAAAAAAAAAAAAAAAAAAAAAA".getBytes("UTF-8"));
        actualRequestBodyBuilder.setInputStream(byteArrayInputStream);
        actualRequestBodyBuilder.setMediaType(null);
        ArrayList<String> encodedNames = new ArrayList<String>();
        FormBody formBody = new FormBody(encodedNames, new ArrayList<String>());

        actualRequestBodyBuilder.setRequestBody(formBody);
        assertSame(byteArrayInputStream, actualRequestBodyBuilder.getInputStream());
        assertNull(actualRequestBodyBuilder.getMediaType());
        assertSame(formBody, actualRequestBodyBuilder.getRequestBody());
    }

    @Test
    public void testCreate() throws UnsupportedEncodingException {
        RequestBodyBuilder requestBodyBuilder = new RequestBodyBuilder(
                new ByteArrayInputStream("AAAAAAAAAAAAAAAAAAAAAAAA".getBytes("UTF-8")), null);
        assertSame(requestBodyBuilder, requestBodyBuilder.create());
    }
}

