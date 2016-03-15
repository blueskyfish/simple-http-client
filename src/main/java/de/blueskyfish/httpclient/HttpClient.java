/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 BlueSkyFish
 */
package de.blueskyfish.httpclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The http client manages the request and responses to the external web service / rest service.
 * <p>
 * There are some restriction:
 * <p>
 * <ul>
 * <li>The client has no session management.</li>
 * <li>if a {@link HttpClientStore} is present, then the client use a header token for the communication with the service</li>
 * </ul>
 */
public class HttpClient {

    private final HttpConfiguration configuration;

    private final HttpClientStore clientStore;

    /**
     * Contains the headers for the request.
     */
    private final Map<String, String> headers = new LinkedHashMap<String, String>();

    public HttpClient(HttpConfiguration configuration, HttpClientStore clientStore) {
        if (configuration == null) {
            throw new IllegalArgumentException("HTCLT-001: http client needs a configuration. Parameter is null");
        }
        this.configuration = configuration;
        this.clientStore = clientStore;
    }

    /**
     * Starts a request to the server with the given url and returns the response.
     *
     * @param httpRequest contains the url, request method and optional the send data.
     * @return the result if an exception is thrown, then it returns a {@link HttpResponseError} instance that has the
     * exception. If the
     * @throws IllegalArgumentException when the parameter is null.
     */
    public HttpResponse execute(HttpRequest httpRequest) {
        if (httpRequest == null) {
            throw new IllegalArgumentException("HTCLT-002: http client is expected a http request parameter, not null");
        }
        long dtStart = System.currentTimeMillis();
        HttpURLConnection conn = null;
        InputStream input = null;
        try {
            conn = createRequest(httpRequest);
            updateHeaderFromConnection(conn);
            int statusCode = conn.getResponseCode();
            String content = null;
            if (statusCode == Definition.STATUS_CODE_OKAY) {
                input = conn.getInputStream();
            } else if (statusCode >= Definition.STATUS_CODE_INTERNAL_SERVER) {
                content = ErrorBuilder.withMessage("Unexpected error from the server");
            } else {
                input = conn.getErrorStream();
            }
            if (input != null) {
                content = IOUtils.readFrom(input, getInputEncoding(), getBufferSize());
            }
            return new HttpResponseResult(statusCode, content, calcDuration(dtStart));
        } catch (ConnectException e) {
            return new HttpResponseError(e, Definition.STATUS_CODE_INTERNAL_SERVER, calcDuration(dtStart));
        } catch (IOException e) {
            return new HttpResponseError(e, Definition.STATUS_CODE_INTERNAL_SERVER, calcDuration(dtStart));
        } catch (HttpClientException e) {
            return new HttpResponseError(e, Definition.STATUS_CODE_INTERNAL_SERVER, calcDuration(dtStart));
        } finally {
            IOUtils.close(input);
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    public boolean hasClientStore() {
        return clientStore != null;
    }

    /**
     * Returns the header token.
     *
     * @return null or the header token
     */
    public String getHeaderToken() {
        return hasClientStore() ? clientStore.getToken() : null;
    }

    /**
     * Store the header token from an execute request (it is from the response header!)
     */
    synchronized void storeHeaderToken(String token) {
        if (hasClientStore()) {
            clientStore.putToken(token);
        }
    }


    String buildUrl(String url) throws HttpClientException {
        String baseUrl = configuration.getBaseUrl();
        if (StringUtils.isEmpty(baseUrl)) {
            throw new HttpClientException("HTCLT-003: missing the base url");
        }
        return baseUrl + url;
    }

    HttpURLConnection createRequest(HttpRequest httpRequest) throws HttpClientException {
        HttpURLConnection conn;
        try {
            conn = (HttpURLConnection) new URL(buildUrl(httpRequest.getUrl())).openConnection();
        } catch (IOException e) {
            throw new HttpClientException(e, "HTCLT-004: malformed request \"%s\"", httpRequest.getUrl());
        }
        try {
            conn.setRequestMethod(httpRequest.getMethod().toString());
        } catch (ProtocolException e) {
            throw new HttpClientException(e, "HTCLT-005: method \"%s\" is not allow ", httpRequest.getMethod());
        }
        setHeadersToConnection(conn);
        setOutputToConnection(httpRequest, conn);
        return conn;
    }

    /**
     * Update the header from the response (only the simple http token is important).
     *
     * @param conn the connection with response.
     */
    void updateHeaderFromConnection(HttpURLConnection conn) {
        String headerTokenName = configuration.getHeaderTokenName();
        if (!hasClientStore() || (headerTokenName == null || headerTokenName.isEmpty())) {
            // the header token is not store
            // two reasons:
            // -- client store is null
            // -- header name is not exist
            return;
        }

        int index = (conn.getHeaderFieldKey(0) == null) ? 1 : 0;
        while (true) {
            String key = conn.getHeaderFieldKey(index);
            if (key == null) {
                break;
            }
            if (headerTokenName.equalsIgnoreCase(key)) {
                String value = conn.getHeaderField(index);
                storeHeaderToken(value);
                break;
            }
            ++index;
        }
    }

    void setHeadersToConnection(HttpURLConnection conn) {

        if (!headers.containsKey(Definition.HEADER_CONTENT_TYPE)) {
            headers.put(Definition.HEADER_CONTENT_TYPE, getContentType());
        }
        if (!headers.containsKey(Definition.HEADER_ACCEPT)) {
            headers.put(Definition.HEADER_ACCEPT, getContentType());
        }
        if (!headers.containsKey(Definition.HEADER_USER_AGENT)) {
            headers.put(Definition.HEADER_USER_AGENT, getUserAgent());
        }

        // set always the header token
        String headerTokenName = configuration.getHeaderTokenName();
        if (hasClientStore() && headerTokenName != null && !headerTokenName.isEmpty()) {
            String token = getHeaderToken();
            headers.put(headerTokenName, token);
        }

        // put into the http connection
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            conn.setRequestProperty(entry.getKey(), entry.getValue());
        }
    }

    void setOutputToConnection(HttpRequest httpRequest, HttpURLConnection conn) throws HttpClientException {
        if (httpRequest.hasSendData()) {
            conn.setDoOutput(true);
            OutputStream output = null;
            try {
                output = conn.getOutputStream();
                Writer writer = new OutputStreamWriter(output, getOutputEncoding());
                writer.append(httpRequest.getSendData()).flush();
            } catch (IOException e) {
                throw new HttpClientException(e, "HTCLT-007: output can not written <%s: %s>",
                    httpRequest.getMethod(), httpRequest.getUrl()
                );
            } finally {
                IOUtils.close(output);
            }
        }
    }

    String getContentType() {
        return StringUtils.prepare(configuration.getContentType(), Definition.DEFAULT_CONTENT_TYPE);
    }

    String getUserAgent() {
        return StringUtils.prepare(configuration.getUserAgent(), Definition.DEFAULT_USER_AGENT);
    }

    String getOutputEncoding() {
        return StringUtils.prepare(configuration.getOutputEncoding(), Definition.DEFAULT_OUTPUT_ENCODING);
    }

    String getInputEncoding() {
        return StringUtils.prepare(configuration.getInputEncoding(), Definition.DEFAULT_INPUT_ENCODING);
    }

    int getBufferSize() {
        int bufferSize = prepare(configuration.getBufferSize(), Definition.DEFAULT_BUFFER_SIZE);

        if (bufferSize < Definition.MIN_BUFFER_SIZE) {
            bufferSize = Definition.MIN_BUFFER_SIZE;
        }
        if (bufferSize > Definition.MAX_BUFFER_SIZE) {
            bufferSize = Definition.MAX_BUFFER_SIZE;
        }
        return bufferSize;
    }


    static int prepare(int value, int def) {
        if (value <= 0) {
            return def;
        }
        return value;
    }

    static long calcDuration(long startTime) {
        return System.currentTimeMillis() - startTime;
    }
}
