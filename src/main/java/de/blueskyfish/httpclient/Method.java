/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 BlueSkyFish
 */
package de.blueskyfish.httpclient;

/**
 * An enumeration of the HTTP methods
 */
public enum Method {

    GET,
    PUT,
    POST,
    DELETE;

    public boolean allowSend() {
        return this == PUT || this == POST;
    }
}
