/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 BlueSkyFish
 */
package de.blueskyfish.httpclient;

public class DefaultHttpClientStore implements HttpClientStore {

    private String token;

    @Override
    public void putToken(String token) {
        this.token = token;
    }

    @Override
    public String getToken() {
        return token;
    }
}
