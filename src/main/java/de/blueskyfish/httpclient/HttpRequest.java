/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 BlueSkyFish
 */
package de.blueskyfish.httpclient;

/**
 * An entity for a http request.
 */
public final class HttpRequest {

    private final String url;

    private final Method method;

    private final String sendData;

    /**
     * Factory method to create a GET http request.
     *
     * @param url the url
     * @return the http request
     */
    public static HttpRequest buildGET(String url) {
        return new HttpRequest(url, Method.GET, null);
    }

    /**
     * Factory method to create a POST http request.
     *
     * @param url      the url
     * @param sendData the sending data
     * @return the http request
     */
    public static HttpRequest buildPOST(String url, String sendData) {
        return new HttpRequest(url, Method.POST, sendData);
    }

    /**
     * Factory method to create a PUT http request
     *
     * @param url      the url
     * @param sendData the sending data
     * @return the http request
     */
    public static HttpRequest buildPUT(String url, String sendData) {
        return new HttpRequest(url, Method.PUT, sendData);
    }

    /**
     * Factory method to create a DELETE http request.
     *
     * @param url the url
     * @return the http request
     */
    public static HttpRequest buildDELETE(String url) {
        return new HttpRequest(url, Method.DELETE, null);
    }

    /**
     * Construct creates the http request.
     *
     * @param url      the url
     * @param method   the http method. The parameter can not be null.
     * @param sendData send data.
     */
    public HttpRequest(String url, Method method, String sendData) {
        if (method == null) {
            throw new IllegalArgumentException("HTCLT-101: parameter 'method' is null");
        }
        this.url = url;
        this.method = method;
        this.sendData = sendData;
        if (!method.allowSend() && sendData != null && !sendData.isEmpty()) {
            throw new IllegalArgumentException("HTCLT-102: send data is not allow in method '" + method + "'");
        }
    }

    public String getUrl() {
        return url;
    }

    public Method getMethod() {
        return method;
    }

    public String getSendData() {
        return sendData;
    }

    public boolean hasSendData() {
        return method.allowSend() && sendData != null && !sendData.isEmpty();
    }

    @Override
    public String toString() {
        return "HttpRequest ( " + method + ": " + url + " )";
    }
}
