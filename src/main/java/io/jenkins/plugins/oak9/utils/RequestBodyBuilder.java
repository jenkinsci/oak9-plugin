package io.jenkins.plugins.oak9.utils;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.internal.Util;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

import java.io.IOException;
import java.io.InputStream;

public class RequestBodyBuilder {

    private InputStream inputStream;
    private MediaType mediaType;
    private RequestBody requestBody;

    public RequestBodyBuilder(InputStream inputStream, MediaType mediaType) {
        this.inputStream = inputStream;
        this.mediaType = mediaType;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(RequestBody requestBody) {
        this.requestBody = requestBody;
    }

    public RequestBodyBuilder create() {

         setRequestBody(new RequestBody() {
            @Override
            public MediaType contentType() {
                return getMediaType();
            }

            @Override
            public long contentLength() {
                try {
                    return getInputStream().available();
                } catch (IOException e) {
                    return 0;
                }
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                Source source = null;
                try {
                    source = Okio.source(inputStream);
                    sink.writeAll(source);
                } finally {
                    if (source != null) {
                        Util.closeQuietly(source);
                    }
                }
            }
        });
        return this;
    }


}