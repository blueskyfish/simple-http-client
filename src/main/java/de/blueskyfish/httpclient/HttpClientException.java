/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 BlueSkyFish
 */
package de.blueskyfish.httpclient;

public class HttpClientException extends Exception {

    private final Object[] args;

    public HttpClientException(String message, Object... args) {
        super(message);
        this.args = args;
    }

    public HttpClientException(Throwable cause, String message, Object... args) {
        super(message, cause);
        this.args = args;
    }

    @Override
    public String getMessage() {
        String message = super.getMessage();
        if (args != null && args.length > 0) {
            message = String.format(message, args);
        }
        return message;
    }

}
