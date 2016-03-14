/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 BlueSkyFish
 */
package de.blueskyfish.httpclient;

/**
 * Stores the token
 */
public interface HttpClientStore {

    void putToken(String token);

    String getToken();
}
