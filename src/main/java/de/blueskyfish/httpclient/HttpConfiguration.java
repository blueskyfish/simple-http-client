/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 BlueSkyFish
 */
package de.blueskyfish.httpclient;

/**
 * The configuration for the {@link HttpClient}.
 */
public class HttpConfiguration {

    /**
     * The buffer size for reading and writing from / to the http connection stream
     */
    private int bufferSize;

    /**
     * The encoding from the input stream
     */
    private String inputEncoding;

    /**
     * The encoding from the output stream
     */
    private String outputEncoding;

    /**
     * The user agent
     */
    private String userAgent;

    /**
     * The content type
     */
    private String contentType;

    /**
     * The base url for all requests.
     */
    private String baseUrl;

    /**
     * The name of the header for the token. If the header name is empty or null, then the token is not stored.
     */
    private String headerTokenName;

    public int getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public String getInputEncoding() {
        return inputEncoding;
    }

    public void setInputEncoding(String inputEncoding) {
        this.inputEncoding = inputEncoding;
    }

    public String getOutputEncoding() {
        return outputEncoding;
    }

    public void setOutputEncoding(String outputEncoding) {
        this.outputEncoding = outputEncoding;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getHeaderTokenName() {
        return headerTokenName;
    }

    public void setHeaderTokenName(String headerTokenName) {
        this.headerTokenName = headerTokenName;
    }
}
