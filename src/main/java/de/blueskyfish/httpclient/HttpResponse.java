/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 BlueSkyFish
 */
package de.blueskyfish.httpclient;

public interface HttpResponse {
    int getStatusCode();

    String getContent();

    long getDuration();

    boolean hasError();

    Throwable getError();
}
