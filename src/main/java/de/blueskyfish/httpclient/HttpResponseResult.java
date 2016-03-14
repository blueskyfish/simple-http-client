/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 BlueSkyFish
 */
package de.blueskyfish.httpclient;

/**
 * A response of a http request
 */
public class HttpResponseResult implements HttpResponse {

    private final int statusCode;

    private final String content;

    private final long duration;

    HttpResponseResult(int statusCode, String content, long duration) {
        this.statusCode = statusCode;
        this.content = content;
        this.duration = duration;
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public long getDuration() {
        return duration;
    }

    @Override
    public boolean hasError() {
        return statusCode > Definition.STATUS_CODE_OKAY;
    }

    @Override
    public Throwable getError() {
        return null;
    }
}
